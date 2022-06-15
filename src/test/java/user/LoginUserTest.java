package user;

import client.UserApiClient;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.apache.commons.lang3.RandomStringUtils;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest {

    private UserApiClient apiClient = new UserApiClient();
    private static StepsUser steps = new StepsUser();
    private String randomPart = RandomStringUtils.randomAlphabetic(6);

    @BeforeClass
    public static void setUp() {
        steps.createUser();
    }

    @AfterClass
    public static void cleanUp() {
        steps.deleteUser();
    }

    @Test
    @DisplayName("Логин существующего пользователя")
    @Description("Тест проверяет логин существующего пользователя с указанием всех параметров")
    public void loginUserPositive() {
        apiClient.loginUserAndReturnResponse(steps.randomEmailUser, steps.randomPasswordUser)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Логин пользователя без параметра Email")
    @Description("Тест проверяет логин пользователя без указания почты")
    public void loginUserWithoutEmail() {
        apiClient.loginUserAndReturnResponse("", steps.randomPasswordUser)
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин пользователя без параметра Password")
    @Description("Тест проверяет логин пользователя без указания пароля")
    public void loginUserWithoutPassword() {
        apiClient.loginUserAndReturnResponse(steps.randomEmailUser, "")
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин пользователя с некорректным Email")
    @Description("Тест проверяет логин пользователя с указанием некорректной почты")
    public void loginUserWithIncorrectEmail() {
        apiClient.loginUserAndReturnResponse(steps.randomEmailUser + randomPart , steps.randomPasswordUser)
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин пользователя с некорректным Password")
    @Description("Тест проверяет логин пользователя с указанием некорректного пароля")
    public void loginUserWithIncorrectPassword() {
        apiClient.loginUserAndReturnResponse(steps.randomEmailUser, steps.randomPasswordUser + randomPart)
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
    }

}
