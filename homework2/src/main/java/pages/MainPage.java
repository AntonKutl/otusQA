package pages;

import annotations.UrlPrefix;
import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import support.GuiceScoped;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UrlPrefix("/")
public class MainPage extends AnyPageAbs<MainPage> {

    public WebDriver drive;

    @Inject
    public MainPage(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    private String menuItemLocator = "//a[.//div[normalize-space()='%s']]";
    private String menuItemDateLocator = "//a[.//div[contains(., '%s')]]";
    private String menuItemTitle = "div.lessons__new-item-title";
    @FindBy(css = ".container-padding-bottom .lessons__new-item-time")
    List<WebElement> dateItem;
    @FindBy(css = ".lessons__new-item-start")
    List<WebElement> dateItemStart;
    @FindBy(css = " a.lessons__new-item  ")
    List<WebElement> item;


    public MainPage clickItemMenu(String nameItem) {
        WebElement menuItemElement = driver.findElement(By.xpath(String.format(menuItemLocator, nameItem)));
        new Actions(driver)
                .click(menuItemElement)
                .perform();
        return this;
    }

    public MainPage clickItemMenuDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM", new Locale("ru"));

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate parse = LocalDate.parse(date, formatterDate);

        List<String> strDate = getDateList().stream()
                .filter(dateCourse -> dateCourse.isAfter(parse.minusDays(1)))
                .map(dateCourseEdit -> dateCourseEdit.format(formatter))
                .distinct()
                .collect(Collectors.toList());

        for (String dateCourse : strDate) {
            driver.findElements(By.xpath(String.format(menuItemDateLocator, dateCourse)))
                    .stream()
                    .distinct()
                    .map(webElement -> webElement.findElement(By.cssSelector(menuItemTitle)).getText())
                    .forEach(print->System.out.println("Курс: "+print+" Начинаеться:"+dateCourse));
        }

        return this;
    }

    public List<LocalDate> getDateList() {
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

            if (Pattern.compile("\\d+ [а-я]+ \\d{4}").matcher(dateInString).find()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
                dateList.add(LocalDate.parse(dateInString, formatter));
            }
        }
        return dateList;
    }

}
