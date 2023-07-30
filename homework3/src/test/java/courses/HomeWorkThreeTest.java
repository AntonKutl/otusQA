package courses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.dto.UserDto;
import org.example.steps.UserSteps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HomeWorkThreeTest {
  UserSteps userSteps = new UserSteps();

  UserDto userDto = UserDto.builder()
      .id(200l)
      .username("Otus")
      .firstName("Otus")
      .lastName("Otus")
      .email("otus@otus.ot")
      .password("123")
      .phone("123456789")
      .userStatus(1l)
      .build();

  @Test
  @DisplayName("Добавление нового пользователя")
  public void addUser() {
    ValidatableResponse user = userSteps.createUser(userDto);
    user.statusCode(HttpStatus.SC_OK);
  }

  @Test
  @DisplayName("Добавление нового пользователя с неполными данными и сравнение json схемы ответа")
  public void addUserWithOneName() {
    UserDto userDto = UserDto.builder()
        .id(201l)
        .username("User1")
        .build();

    ValidatableResponse user = userSteps.createUser(userDto);
    user.statusCode(HttpStatus.SC_OK);
    user.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/user_add_answer.json"));
  }

  @Test
  @DisplayName("Получение пользователей и проверка на соответствия")
  public void getUser() {
    UserSteps userSteps = new UserSteps();
    ValidatableResponse validatableResponse = userSteps.getUsers("Otus");
    UserDto user = validatableResponse.extract().body().as(UserDto.class);
    assertThat(user.equals(userDto))
        .as("Пользователи разные")
        .isTrue();
  }

  @Test
  @DisplayName("Попытка получить не существующего пользователя")
  public void getNoUser() {
    UserSteps userSteps = new UserSteps();
    ValidatableResponse validatableResponse = userSteps.getUsers("sdfsdgr");
    validatableResponse
        .body("message", equalTo("User not found"))
        .statusCode(HttpStatus.SC_NOT_FOUND);
  }

}
