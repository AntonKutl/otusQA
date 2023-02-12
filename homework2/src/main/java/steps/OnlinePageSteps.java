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

    @Тогда("Найти подоготовительный курс с минимальной ценой")
    public void minPrice() {

        onlinePage.clickItemMenuPrice(()->onlinePage
                .getPriseList()
                .stream()
                .min(Comparator.naturalOrder())
                .get());

    }

    @Тогда("Найти подоготовительный курс с максимальной ценой")
    public void maxPrice() {

        onlinePage.clickItemMenuPrice(()->onlinePage
                .getPriseList()
                .stream()
                .max(Comparator.naturalOrder())
                .get());

    }

}
