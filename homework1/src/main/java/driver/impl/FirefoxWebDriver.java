package driver.impl;

import exceptions.DriverTypeNotSupported;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class FirefoxWebDriver implements IDriver {

  @Override
  public WebDriver newDriver() {
    FirefoxOptions options = new FirefoxOptions();

    if (getRemoteUrl() == null) {
      try {
        downloadLocalWebDriver(DriverManagerType.FIREFOX);
      } catch (DriverTypeNotSupported ex) {
        ex.printStackTrace();
      }

      return new FirefoxDriver(options);
    } else
      return new RemoteWebDriver(getRemoteUrl(), options);
  }
}
