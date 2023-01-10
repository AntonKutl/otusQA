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

    private String menuItemDateLocator = "//div[contains(text(), '%s')]";

    @FindBy(css = ".container-padding-bottom .lessons__new-item-time")
    List<WebElement> dateItem;

    @FindBy(css = ".lessons__new-item-start")
    List<WebElement> dateItemStart;

    public MainPage clickItemMenu(String nameItem) {
        WebElement menuItemElement = driver.findElement(By.xpath(String.format(menuItemLocator, nameItem)));
        new Actions(driver)
                .click(menuItemElement)
                .perform();
        return this;
    }

    public MainPage clickEarlierItemMenu() {
        LocalDate min = getDateList().stream()
                .min(Comparator.naturalOrder())
                .get();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM", Locale.ENGLISH);

        List<WebElement> menuItemElement = driver.findElements(By.xpath(String.format(menuItemDateLocator, min.format(formatter))));

        WebElement webElement = menuItemElement.stream()
                .reduce((first, second) -> second)
                .get();

        new Actions(driver)
                .click(webElement)
                .perform();

        return this;
    }

    public MainPage clickLaterItemMenu() {
        LocalDate min = getDateList().stream()
                .max(Comparator.naturalOrder())
                .get();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM", Locale.ENGLISH);

        List<WebElement> menuItemElement = driver.findElements(By.xpath(String.format(menuItemDateLocator, min.format(formatter))));

        WebElement webElement = menuItemElement.stream()
                .reduce((first, second) -> second)
                .get();

        new Actions(driver)
                .click(webElement)
                .perform();

        return this;
    }

    private List<LocalDate> getDateList() {
        List<LocalDate> dateList = new ArrayList<>();

        List<List<String>> datesStringList = dateItem.stream()
                .map(text -> text.getText())
                .map(word -> word.split(" "))
                .map(array -> Arrays.stream(array)
                        .limit(3)
                        .collect(Collectors.toList())
                )
                .collect(Collectors.toList());

        datesStringList.addAll(dateItemStart.stream()
                .map(text -> text.getText())
                .map(word -> word.split(" "))
                .map(array -> Arrays.stream(array)
                        .skip(1)
                        .limit(3)
                        .collect(Collectors.toList())
                )
                .collect(Collectors.toList()));

        for (List<String> dateStringList : datesStringList) {
            if (dateStringList.size() < 3 || Integer.parseInt(dateStringList.get(2)) < 2000) {
                dateStringList.add(2, String.valueOf(LocalDate.now().getYear()));
            }

            String dateInString = dateStringList.get(0) + " " + dateStringList.get(1) + " " + dateStringList.get(2);

            if (Pattern.compile("\\d+ \\w+ \\d{4}").matcher(dateInString).find()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
                dateList.add(LocalDate.parse(dateInString, formatter));
            }
        }
        return dateList;
    }

}
