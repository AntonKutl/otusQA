package pageobject;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import support.GuiceScoped;
import waiters.BaseWaiters;

public abstract class AbsPageObject<T> {

  private final BaseWaiters baseWaiters;
  protected WebDriver driver;
  protected GuiceScoped guiceScoped;

  @Inject
  public AbsPageObject(GuiceScoped guiceScoped) {
    this.guiceScoped = guiceScoped;
    this.driver = guiceScoped.driver;
    this.baseWaiters = new BaseWaiters(driver);

    PageFactory.initElements(guiceScoped.driver, this);
  }

}
