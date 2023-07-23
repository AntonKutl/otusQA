package steps;

import com.google.inject.Inject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import pages.MainPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MainPageSteps {
    @Inject
    public MainPage mainPage;

    @Тогда("^Открываю страницу с курсом")
    public void openCoursePage(DataTable vars) {
        Random r = new Random();
        mainPage.clickItemMenu(vars.asList().get(r.nextInt(vars.asList().size())));
    }

    @Тогда("^найти курс стартующий c (\\d{2}\\.\\d{2}\\.\\d{4})")
    public void searchCourse(String date) throws InterruptedException {
        Thread.sleep(1000);
        LocalDate parse = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        mainPage.clickItemMenuDate(
                mainPage.getDateList()
                        .stream()
                        .filter(x -> !x.isBefore(parse))
                        .collect(Collectors.toSet()));
    }

    @Пусть("Открыта главная страница в браузере")
    public void openPage() {
        mainPage.open();
    }

}
