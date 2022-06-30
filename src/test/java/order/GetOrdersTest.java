package order;

import client.OrderApiClient;
import user.StepsUser;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersTest {

    public OrderApiClient apiClient = new OrderApiClient();
    public static StepsUser stepsUser = new StepsUser();
    public static StepsOrder stepsOrder = new StepsOrder();
    public static String auth;

    @BeforeClass
    public static void setUp() {
        auth = stepsUser.createUserAndReturnToken();
        stepsOrder.createOrder(auth, 1);
    }

    @AfterClass
    public static void cleanUp() {
        stepsUser.deleteUser(auth);
    }

    @Test
    @DisplayName("Получить список закзаов пользователя с авторизацией")
    @Description("Тест проверяет получение списка заказов пользователя с токеном авторизации")
    public void getOrdersAuthUser(){
        apiClient.getUserOrders(auth)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("orders", notNullValue());
    }

    @Test
    @DisplayName("Получить список закзаов пользователя без авторизациеи")
    @Description("Тест проверяет получение списка заказов пользователя без токена авторизации")
    public void getOrdersNoAuthUser(){
        apiClient.getUserOrders("")
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

}
