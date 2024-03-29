package pages;

import annotations.UrlPrefix;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UrlPrefix("/")
public class MainPage extends AnyPageAbs<MainPage> {

  public MainPage(WebDriver driver) {
    super(driver);
  }

  private String menuItemLocator = "//a[.//div[normalize-space()='%s']]";

  private String menuItemDateLocator = "//div[div[picture] and div[contains(., '%s')]]";

  @FindBy(xpath = "//a[contains(@href, 'https://otus.ru/lessons/')]//span[contains(., 'С')]")
  List<WebElement> dateItem;

  public MainPage clickItemMenu(String nameItem) {
    WebElement menuItemElement = driver.findElement(By.xpath(String.format(menuItemLocator, nameItem)));
    new Actions(driver).click(menuItemElement).perform();
    return this;
  }

  public MainPage clickItemMenuDate(Func func) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM", new Locale("ru"));

    List<WebElement> menuItemElement = driver.findElements(By.xpath(String.format(menuItemDateLocator, func.getFunc().format(formatter))));

    WebElement webElement = menuItemElement.stream().reduce((first, second) -> second).get();

    new Actions(driver).click(webElement).perform();

    return this;
  }

  public List<LocalDate> getDateList() {
    List<LocalDate> dateList = new ArrayList<>();

    List<List<String>> datesStringList =
        dateItem.stream().map(WebElement::getText).map(word -> word.split(" ")).map(array -> Arrays.stream(array).skip(1).limit(2).collect(Collectors.toList())).collect(Collectors.toList());

    for (List<String> dateStringList : datesStringList) {
      if (dateStringList.size() < 3) {
        dateStringList.add(2, String.valueOf(LocalDate.now().getYear()));
      }

      String dateInString = dateStringList.get(0) + " " + dateStringList.get(1) + " " + dateStringList.get(2);

      if (Pattern.compile("\\d+ [а-я]+ \\d{4}").matcher(dateInString).find()) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        dateList.add(LocalDate.parse(dateInString, formatter));
      }
    }
    return dateList;
  }

}
