package stubs;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static stubs.util.ConfigurationService.getProperty;
import static stubs.util.GetFile.readFile;


public class Stubs {
  public WireMockServer wireMockServer;

  public Stubs setUp() {
    wireMockServer = new WireMockServer(Integer.parseInt(getProperty("port.url")));
    wireMockServer.start();
    return this;
  }

  public Stubs stop() {
    wireMockServer.stop();
    return this;
  }

  public Stubs stubForGetScore(String responseFileName) {
    wireMockServer.stubFor(get(urlPathTemplate("/user/get/{id}"))
        .withPathParam("id", equalTo("1"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBodyFile(responseFileName))
    );
    return this;
  }

  public Stubs stubForGetCourse(String responseFileName) {
    wireMockServer.stubFor(get("/cource/get/all")
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBodyFile(responseFileName)));
    return this;
  }

  public Stubs stubForGetUser(String responseFileName) {
    wireMockServer.stubFor(get("/user/get/all")
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBodyFile(responseFileName)));
    return this;
  }


  public Stubs stubForGetUserXML(String responseFileName) {
    wireMockServer.stubFor(post("/score/scheme.xml")
        .withRequestBody(equalToXml(readFile("user_request.xml")))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "text/xml")
            .withBodyFile(responseFileName)));

    return this;
  }

  public void status() {
    System.out.println("Stubs Started!");
  }
}
