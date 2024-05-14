import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.Ingredients;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderDataTests extends BaseUserTest {

    @Test
    @DisplayName("Получение заказов")
    @Description("Получение заказов авторизованного пользователя")
    public void checkUserOrders() {
        setupToken();
        Ingredients ingredients = new Ingredients(generateListIngredients());

        createOrder(token, ingredients);

        getUserOrders(token)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("orders", notNullValue());
    }

    @Test
    @DisplayName("Попытка получение заказов")
    @Description("Получение заказов не авторизованного пользователя")
    public void checkUserOrdersWithout () {
        setupToken();
        Ingredients ingredients = new Ingredients(generateListIngredients());

        createOrder(token, ingredients);

        getUserOrders("")
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false));
    }
}
