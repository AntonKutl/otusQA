import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import helper.APIHelper;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import stubs.Stubs;

public class APITest {
  private static Stubs apiStubs = new Stubs();
  private static APIHelper apiHelper = new APIHelper();

  @BeforeClass
  public static void configureSystemUnderTest() {
    apiStubs.setUp()
        .stubForGetScore("score.json")
        .stubForGetUser("score.json")
        .stubForGetCourse("course.json")
        .status();
  }

  @Test(testName = "Проверка json схемы score")
  public void checkScore() {
    ValidatableResponse users = apiHelper.getScore(1);
    users.statusCode(HttpStatus.SC_OK);
    users.body(matchesJsonSchemaInClasspath("schema/score.json"));
  }

  @Test(testName = "Проверка json схемы course")
  public void checkCourses() {
    ValidatableResponse users = apiHelper.getCourse();
    users.statusCode(HttpStatus.SC_OK);
    users.body(matchesJsonSchemaInClasspath("schema/course.json"));
  }

  @Test(testName = "Проверка json схемы users")
  public void checkUsers() {
    ValidatableResponse users = apiHelper.getUsers();
    users.statusCode(HttpStatus.SC_OK);
    users.body(matchesJsonSchemaInClasspath("schema/user.json"));
  }

  @AfterClass
  public static void stopTest() {
    apiStubs.stop();
  }
}
