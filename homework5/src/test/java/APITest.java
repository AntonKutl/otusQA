import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import helper.APIHelper;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stubs.Stubs;

public class APITest {
  private final static Stubs apiStubs = new Stubs();
  private final static APIHelper apiHelper = new APIHelper();

  @BeforeAll
  public static void configureSystemUnderTest() {
    apiStubs.setUp()
        .stubForGetScore("score.json")
        .stubForGetUser("score.json")
        .stubForGetCourse("course.json")
        .status();
  }

  @Test
  @DisplayName("Проверка json схемы score")
  public void checkScore() {
    ValidatableResponse users = apiHelper.getScore(1);
    users.statusCode(HttpStatus.SC_OK);
    users.body(matchesJsonSchemaInClasspath("schema/score.json"));
  }

  @Test
  @DisplayName("Проверка json схемы course")
  public void checkCourses() {
    ValidatableResponse users = apiHelper.getCourse();
    users.statusCode(HttpStatus.SC_OK);
    users.body(matchesJsonSchemaInClasspath("schema/course.json"));
  }

  @Test
  @DisplayName("Проверка json схемы users")
  public void checkUsers() {
    ValidatableResponse users = apiHelper.getUsers();
    users.statusCode(HttpStatus.SC_OK);
    users.body(matchesJsonSchemaInClasspath("schema/user.json"));
  }

  @AfterAll
  public static void stopTest() {
    apiStubs.stop();
  }
}
