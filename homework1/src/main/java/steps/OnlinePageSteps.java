package steps;

import com.google.inject.Inject;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import pages.OnlinePage;

import java.util.Comparator;

public class OnlinePageSteps {

    @Inject
    public OnlinePage onlinePage;


    @Пусть("Открыта страницу с подготовительными курсами")
    public void openPage() {
        onlinePage.open();
    }

    @Тогда("Найти подготовительный курс с минимальной ценой")
    public void minPrice() throws InterruptedException {
        Thread.sleep(1000);
        onlinePage.printMenuPrice(onlinePage
                .getPriseList()
                .stream()
                .min(Comparator.naturalOrder())
                .get());

    }

    @Тогда("Найти подготовительный курс с максимальной ценой")
    public void maxPrice() {
        onlinePage.printMenuPrice(onlinePage
                .getPriseList()
                .stream()
                .max(Comparator.naturalOrder())
                .get());

    }


}
