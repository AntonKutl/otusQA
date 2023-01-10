package courses;

import annotations.Driver;
import extensions.UIExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.MainPage;

@ExtendWith(UIExtension.class)
public class HomeWorkOneTest {

    @Driver
    public WebDriver driver;

    @Test
    public void selectCourseFromMenu() {
        MainPage mainPage = new MainPage(driver).open();

        mainPage.clickItemMenu("Специализация Java-разработчик");

        mainPage.navigationBack();

        mainPage.clickEarlierItemMenu();

        mainPage.navigationBack();

        mainPage.clickLaterItemMenu();
    }
}
