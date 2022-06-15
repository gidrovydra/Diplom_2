package order;

import user.StepsUser;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class CreateOrderTest {

    public StepsOrder stepsOrder = new StepsOrder();
    public static StepsUser stepsUser = new StepsUser();
    public static String auth;

    @BeforeClass
    public static void setUp() {
        auth = stepsUser.createUserAndReturnToken();
    }

    @AfterClass
    public static void cleanUp() {
        stepsUser.deleteUser(auth);
    }

    @Test
    @DisplayName("Заказ с валидными ингредиентами и авторизацией")
    @Description("Тест проверяет создание заказа с набором валидных ингредиентов и с авторизацией")
    public void createOrderWithValidIngrdsAndAuth() {
        stepsOrder.createOrder(auth, 1)
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("order.ingredients", notNullValue())
                .body("order.number", notNullValue())
                .body("order.owner",notNullValue());
    }

    @Test
    @DisplayName("Заказ с валидными ингредиентами и без авторизации")
    @Description("Тест проверяет создание заказа с набором валидных ингредиентов и без авторизации")
    public void createOrderWithValidIngrdsAndNoAuth() {
        stepsOrder.createOrder("", 1)
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("order.ingredients", nullValue())
                .body("order.number", notNullValue())
                .body("order.owner",nullValue());
    }

    @Test
    @DisplayName("Заказ с невалидными ингредиентами")
    @Description("Тест проверяет создание заказа с набором невалидных ингредиентов")
    public void createOrderWithInvalidIngrds() {
        stepsOrder.createOrder(auth, 2)
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Заказ с валидными и невалидными ингредиентами")
    @Description("Тест проверяет создание заказа с набором валидных и невалидных ингредиентов")
    public void createOrderWithValidAndInvalidIngrds() {
        stepsOrder.createOrder(auth, 3)
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Заказ без ингредиентов")
    @Description("Тест проверяет создание заказа без ингредиентов")
    public void createOrderWithoutIngrds() {
        stepsOrder.createOrder(auth, 4)
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

}
