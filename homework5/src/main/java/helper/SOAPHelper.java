package helper;

import static io.restassured.RestAssured.given;
import static stubs.util.ConfigurationService.getProperty;
import static stubs.util.GetFile.readFile;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class SOAPHelper {
  private RequestSpecification spec;

  public SOAPHelper() {
    spec = given()
        .port(Integer.parseInt(getProperty("port.url")))
        .contentType(ContentType.JSON);
  }

  @Step("Получение оценки")
  public ValidatableResponse getScore(String path) {
    return given(spec)
        .header("Content-Type", "text/xml")
        .body(readFile(path))
        .log().all()
        .when()
        .post("/score/scheme.xml")
        .then()
        .log().all();
  }
}
