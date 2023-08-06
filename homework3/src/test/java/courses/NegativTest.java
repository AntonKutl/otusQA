package courses;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.dto.UserDto;
import org.example.steps.UserSteps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NegativTest {

  private final UserSteps userSteps=new UserSteps();

  @Test
  @DisplayName("Попытка получить не существующего пользователя")
  public void getNoUser() {
    UserSteps userSteps = new UserSteps();
    ValidatableResponse validatableResponse = userSteps.getUsers("sdfsdgr");
    validatableResponse
        .body("message", equalTo("User not found"))
        .statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  @DisplayName("Попытка отправить запрос с пустым username")
  public void getUser() {
    ValidatableResponse validatableResponse = userSteps.getUsers("");
    validatableResponse
        .body("type", equalTo("unknown"))
        .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
  }
}
