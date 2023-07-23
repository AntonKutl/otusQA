package pages;

import annotations.UrlPrefix;
import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import support.GuiceScoped;

import java.util.List;
import java.util.stream.Collectors;

@UrlPrefix("/online/")
public class OnlinePage extends AnyPageAbs<OnlinePage> {

    public WebDriver drive;

    @Inject
    public OnlinePage(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    @FindBy(css = "a.lessons__new-item")
    List<WebElement> item;

    private String menuItemPriceLocator = "//a[.//div[contains(text(), '%s')]]";

    public List<Integer> getPriseList() {
        return item.stream()
                .map(x -> x.getText().replaceAll("\\D+", ""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public OnlinePage printMenuPrice(int price) {

        List<WebElement> menuItemElement = driver.findElements(By.xpath(String.format(menuItemPriceLocator, price)));

        WebElement webElement = menuItemElement.stream()
                .reduce((first, second) -> second)
                .get();

        System.out.printf(webElement.findElement(By.cssSelector("div.lessons__new-item-container")).getText());

        return this;
    }
}
