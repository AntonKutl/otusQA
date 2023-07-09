package driver.impl;

import exceptions.DriverTypeNotSupported;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class OperaWebDriver implements IDriver {

  @Override
  public WebDriver newDriver() {
    OperaOptions chromeOptions = new OperaOptions();

    if (getRemoteUrl() == null) {
      try {
        downloadLocalWebDriver(DriverManagerType.OPERA);
      } catch (DriverTypeNotSupported ex) {
        ex.printStackTrace();
      }

      return new OperaDriver(chromeOptions);
    } else
      return new RemoteWebDriver(getRemoteUrl(), chromeOptions);
  }
}
