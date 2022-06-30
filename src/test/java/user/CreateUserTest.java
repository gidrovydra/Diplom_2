package user;

import client.UserApiClient;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {

    private UserApiClient apiClient = new UserApiClient();
    private StepsUser steps = new StepsUser();

    @After
    public void cleanUp() {
        if (steps.loginUserSuccess()) {
            steps.deleteUser();
        }
    }

    @Test
    @DisplayName("Создать уникального пользователя со всеми параметрами")
    @Description("Тест проверяет создание уникального пользователя с указанием всех параметров")
    public void createUserPositive() {
        apiClient.createUserAndReturnResponse(steps.randomEmailUser, steps.randomPasswordUser,steps.randomPasswordUser)
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Создать пользователя без параметра email")
    @Description("Тест проверяет создание уникального пользователя без указания почты")
    public void createUserWithoutEmail() {
        apiClient.createUserAndReturnResponse("", steps.randomPasswordUser,steps.randomPasswordUser)
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создать пользователя без параметра password")
    @Description("Тест проверяет создание уникального пользователя без указания пароля")
    public void createUserWithoutPassword() {
        apiClient.createUserAndReturnResponse(steps.randomEmailUser, "",steps.randomPasswordUser)
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создать пользователя без параметра name")
    @Description("Тест проверяет создание уникального пользователя без указания имени")
    public void createUserWithoutName() {
        apiClient.createUserAndReturnResponse(steps.randomEmailUser, steps.randomPasswordUser, "")
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создать дублирующего пользователя")
    @Description("Тест проверяет создание неуникального пользователя")
    public void createDuplicateUser() {
        steps.createUser();
        apiClient.createUserAndReturnResponse(steps.randomEmailUser, steps.randomPasswordUser, steps.randomNameUser)
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("User already exists"));
    }

}
