import helper.SOAPHelper;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stubs.Stubs;

import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;

public class SOAPTest {
  private final static Stubs soapStubs = new Stubs();
  private final static SOAPHelper helper = new SOAPHelper();

  @BeforeAll
  public static void configureSystemUnderTest() {
    soapStubs.setUp()
        .stubForGetUserXML("user.xml")
        .status();
  }

  @Test
  @DisplayName("Проверка xml схемы score")
  public void checkScore() {
    ValidatableResponse users = helper.getScore("response/course.xml");
    users.statusCode(HttpStatus.SC_OK);
    users.body(matchesXsdInClasspath("schema/course.xsd"));
  }

  @AfterAll
  public static void stopTest() {
    soapStubs.stop();
  }
}
