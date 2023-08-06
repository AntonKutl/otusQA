package org.example.steps;

import static io.restassured.RestAssured.given;
import static org.example.utils.ConfigurationService.getProperty;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.dto.UserDto;

public class UserSteps {
  private final RequestSpecification spec;

  public UserSteps() {
    spec = given()
        .baseUri(getProperty("base.url"))
        .basePath(getProperty("add.path"))
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

  @Step("Получение пользователей {name}")
  public ValidatableResponse deletedUser(String name) {
    return given(spec)
        .pathParam("user", name)
        .accept("application/json")
        .log().all()
        .when()
        .delete("{user}")
        .then()
        .log().all();
  }


}
