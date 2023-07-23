package steps;

import com.google.inject.Inject;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.Пусть;
import pages.MainPage;

public class WebSteps {

  @Inject
  public MainPage mainPage;

  @Пусть("^Я открываю браузер ([^\"]*)")
  public void openPage(String nameBrowser) {
    System.setProperty("browser", nameBrowser);
  }

  @Затем("^Вернуться на приведущую вкладку")
  public void navigationBack() {
    mainPage.navigationBack();
  }
}