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

public class PositiveTest {

  private final UserSteps userSteps=new UserSteps();

  private final UserDto userDto = UserDto.builder()
      .id(200l)
      .username("Otus")
      .firstName("Otus")
      .lastName("Otus")
      .email("otus@otus.ot")
      .password("123")
      .phone("123456789")
      .userStatus(1l)
      .build();

  private final UserDto userIncompleteDto = UserDto.builder()
      .id(201l)
      .username("User1")
      .build();

  @Test
  @DisplayName("Добавление нового пользователя")
  public void addUser() {
    ValidatableResponse user = userSteps.createUser(userDto);
    user.statusCode(HttpStatus.SC_OK);

    UserDto receivedUser = userSteps
        .getUsers(userDto.getUsername())
        .extract()
        .body()
        .as(UserDto.class);

    assertThat(receivedUser.equals(userDto))
        .as("Пользователи разные")
        .isTrue();

    userSteps.deletedUser(userDto.getUsername());
  }

  @Test
  @DisplayName("Добавление нового пользователя с неполными данными и сравнение json схемы ответа")
  public void addUserWithOneName() {
    ValidatableResponse user = userSteps.createUser(userIncompleteDto);
    user.statusCode(HttpStatus.SC_OK);
    user.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/user_add_answer.json"));
    userSteps.deletedUser(userIncompleteDto.getUsername());
  }
}
