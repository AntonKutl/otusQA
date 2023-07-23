package pages;

import annotations.UrlPrefix;
import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import pageobject.AbsPageObject;
import support.GuiceScoped;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class AnyPageAbs<T> extends AbsPageObject<T> {
  @Inject
  public AnyPageAbs(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  private String baseUrl = System.getProperty("base.url", "https://otus.ru");

  private String getUrlPrefix() {
    UrlPrefix urlAnnotation = getClass().getAnnotation(UrlPrefix.class);
    if (urlAnnotation != null) {
      return urlAnnotation.value();
    }

    return "";
  }

  public void open() {
    driver.get(baseUrl + getUrlPrefix());
  }

  public void navigationBack() {
    try {
      driver.navigate().back();
    } catch (Exception ex) {
      throw new RuntimeException("Невозможно вернуться назад");
    }
  }
}
