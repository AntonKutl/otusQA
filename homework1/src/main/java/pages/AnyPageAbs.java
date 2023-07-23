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

  public T open() {
    driver.get(baseUrl + getUrlPrefix());
    return (T) page(getClass());
  }

  public <T> T page(Class<T> clazz) {
    try {
      Constructor<T> constructor = clazz.getConstructor(WebDriver.class);

      return convertInstanceOfObject(constructor.newInstance(driver), clazz);

    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      e.printStackTrace();
    }

    return null;
  }

  private static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
    try {
      return clazz.cast(o);
    } catch (ClassCastException e) {
      return null;
    }
  }

  public void navigationBack() {
    try {
      driver.navigate().back();
    } catch (Exception ex) {
      throw new RuntimeException("Невозможно вернуться назад");
    }
  }
}
