package org.example.steps;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.dto.UserDto;

public class UserSteps {
  private static final String BASE_URL = "https://petstore.swagger.io/v2";
  private static final String ADD_PATH = "/user";
  private RequestSpecification spec;

  public UserSteps() {
    spec = given()
        .baseUri(BASE_URL)
        .basePath(ADD_PATH)
        .contentType(ContentType.JSON);
  }

  @Step("Добавление нового пользователя")
  public ValidatableResponse createUser(UserDto userDto) {
    return given(spec)
        .body(userDto)
        .log().all()
        .when()
        .post()
        .then()
        .log().all();
  }

  @Step("Получение пользователей {name}")
  public ValidatableResponse getUsers(String name) {
    return given(spec)
        .pathParam("user", name)
        .accept("application/json")
        .log().all()
        .when()
        .get("{user}")
        .then()
        .log().all();
  }


}
