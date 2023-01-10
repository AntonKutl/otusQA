package pages;

import actions.CommonActions;
import annotations.UrlPrefix;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class AnyPageAbs<T> extends CommonActions<T> {

    public AnyPageAbs(WebDriver driver) {
        super(driver);
    }

    private String getBaseUrl() {
        return StringUtils.stripEnd(System.getProperty("webdriver.base.url"), "/");
    }

    private String getUrlPrefix() {
        UrlPrefix urlAnnotation = getClass().getAnnotation(UrlPrefix.class);
        if (urlAnnotation != null) {
            return urlAnnotation.value();
        }

        return "";
    }

    public T open() {
        driver.get(getBaseUrl() + getUrlPrefix());

        return (T) page(getClass());
    }

    public <T> T page(Class<T> clazz) {
        try {
            Constructor constructor = clazz.getConstructor(WebDriver.class);

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
