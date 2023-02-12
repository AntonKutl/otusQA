package steps;

import com.google.inject.Inject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import pages.MainPage;

import java.util.Random;

public class MainPageSteps {
    @Inject
    public MainPage mainPage;

    @Тогда("^Открываю страницу с курсом")
    public void openCoursePage(DataTable vars) {
        Random r = new Random();
        mainPage.clickItemMenu(vars.asList().get(r.nextInt(vars.asList().size())));
    }

    @Тогда("^найти курс стартующий ([^\"]*)")
    public void searchCourse(String date) {
        mainPage.clickItemMenuDate(date);
    }

    @Пусть("Открыта главная страница в браузере")
    public void openPage() {
        mainPage.open();
    }

}
