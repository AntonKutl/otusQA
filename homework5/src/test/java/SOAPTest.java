import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;

import helper.SOAPHelper;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import stubs.Stubs;

public class SOAPTest {
  private static Stubs soapStubs = new Stubs();
  private static SOAPHelper helper = new SOAPHelper();

  @BeforeClass
  public static void configureSystemUnderTest() {
    soapStubs.setUp()
        .stubForGetUserXML("user.xml")
        .status();
  }

  @Test(testName = "Проверка xml схемы score")
  public void checkScore() {
    ValidatableResponse users = helper.getScore("response/course.xml");
    users.statusCode(HttpStatus.SC_OK);
    users.body(matchesXsdInClasspath("schema/course.xsd"));
  }

  @AfterClass
  public static void stopTest() {
    soapStubs.stop();
  }
}
