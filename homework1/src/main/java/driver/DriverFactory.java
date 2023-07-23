package driver;

import driver.impl.ChromeWebDriver;
import driver.impl.FirefoxWebDriver;
import driver.impl.OperaWebDriver;
import exceptions.DriverTypeNotSupported;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.Locale;

public class DriverFactory {

  public static EventFiringWebDriver newDriver() {

    switch (System.getProperty("browser", "chrome").toLowerCase(Locale.ROOT)) {
      case "chrome": {
        return new EventFiringWebDriver(new ChromeWebDriver().newDriver());
      }
      case "firefox": {
        return new EventFiringWebDriver(new FirefoxWebDriver().newDriver());
      }
      case "opera": {
        return new EventFiringWebDriver(new OperaWebDriver().newDriver());
      }
      default:
        try {
          throw new DriverTypeNotSupported(System.getProperty("browser", "chrome").toLowerCase(Locale.ROOT));
        } catch (DriverTypeNotSupported ex) {
          ex.printStackTrace();
          return null;
        }
    }
  }
}
