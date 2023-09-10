package helper;

import static io.restassured.RestAssured.given;
import static stubs.util.ConfigurationService.getProperty;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;


public class APIHelper {
  private RequestSpecification spec;

  public APIHelper() {
    spec = given()
        .port(Integer.parseInt(getProperty("port.url")))
        .contentType(ContentType.JSON);
  }

  @Step("Получение оценки")
  public ValidatableResponse getScore(int id) {
    return given(spec)
        .pathParam("id", id)
        .log().all()
        .when()
        .get("/user/get/{id}")
        .then()
        .log().all();
  }

  @Step("Получение курсов")
  public ValidatableResponse getCourse() {
    return given(spec)
        .log().all()
        .when()
        .get("/cource/get/all")
        .then()
        .log().all();
  }

  @Step("Получение пользователя")
  public ValidatableResponse getUsers() {
    return given(spec)
        .log().all()
        .when()
        .get("/user/get/all")
        .then()
        .log().all();
  }

}
