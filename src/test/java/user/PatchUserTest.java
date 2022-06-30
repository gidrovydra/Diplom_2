package user;

import client.UserApiClient;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import java.util.Map;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class PatchUserTest {

    public UserApiClient apiClient = new UserApiClient();
    public StepsUser steps = new StepsUser();
    public StepsUser stepsForSecondUser = new StepsUser();
    public String auth;

    @Before
    public void setUp() {
        steps.generatePatches();
        auth = steps.createUserAndReturnToken();
    }

    @After
    public void cleanUp() {
       steps.deleteUser(auth);
    }

    @Test
    @DisplayName("Обновить параметр Name пользователя с авторизацией")
    @Description("Тест проверяет обновление имени пользователя с указанием токена авторизации")
    public void updateNameAuthUser() {
       apiClient.updateUserDataAndReturnResponse(auth, steps.patch[0])
               .then()
               .assertThat()
               .statusCode(SC_OK)
               .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Обновить параметр Password пользователя с авторизацией")
    @Description("Тест проверяет обновление пароля пользователя с указанием токена авторизации")
    public void updatePasswordAuthUser() {
        apiClient.updateUserDataAndReturnResponse(auth, steps.patch[1])
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Обновить параметр Email пользователя с авторизацией")
    @Description("Тест проверяет обновление почты пользователя с указанием токена авторизации")
    public void updateEmailAuthUser() {
        apiClient.updateUserDataAndReturnResponse(auth, steps.patch[2])
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Обновить параметр Email (где Email уже зарегистрирован в системе) пользователя с авторизацией")
    @Description("Тест проверяет обновление почты пользователя, где почта уже использвана для другого пользователя, с указанием токена авторизации")
    public void updateDuplicatedEmailAuthUser() {
        stepsForSecondUser.createUserAndReturnToken();
        apiClient.updateUserDataAndReturnResponse(auth, Map.of("Email",stepsForSecondUser.randomEmailUser))
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Обновить параметры Name и Password пользователя с авторизацией")
    @Description("Тест проверяет обновление имени и пароля пользователя с указанием токена авторизации")
    public void updateNamePasswordAuthUser() {
        apiClient.updateUserDataAndReturnResponse(auth, steps.patch[3])
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Обновить параметры Name и Email пользователя с авторизацией")
    @Description("Тест проверяет обновление имени и почты пользователя с указанием токена авторизации")
    public void updateNameEmailAuthUser() {
        apiClient.updateUserDataAndReturnResponse(auth, steps.patch[4])
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Обновить параметры Password и Email пользователя с авторизацией")
    @Description("Тест проверяет обновление пароля и почты пользователя с указанием токена авторизации")
    public void updatePasswordEmailAuthUser() {
        apiClient.updateUserDataAndReturnResponse(auth, steps.patch[5])
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Обновить параметры Email, Password, Name пользователя с авторизацией")
    @Description("Тест проверяет обновление имени, пароля и почты пользователя с указанием токена авторизации")
    public void updateEmailPasswordNameAuthUser() {
        apiClient.updateUserDataAndReturnResponse(auth, steps.patch[6])
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Обновить параметр Name пользователя без авторизации")
    @Description("Тест проверяет обновление имени пользователя без указания токена авторизации")
    public void updateNameNoAuthUser() {
        apiClient.updateUserDataAndReturnResponse("", steps.patch[0])
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Обновить параметр Password пользователя без авторизации")
    @Description("Тест проверяет обновление пароля пользователя без указания токена авторизации")
    public void updatePasswordNoAuthUser() {
        apiClient.updateUserDataAndReturnResponse("", steps.patch[1])
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Обновить параметр Email пользователя без авторизации")
    @Description("Тест проверяет обновление почты пользователя без указания токена авторизации")
    public void updateEmailNoAuthUser() {
        apiClient.updateUserDataAndReturnResponse("", steps.patch[2])
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Обновить параметры Name и Password пользователя без авторизации")
    @Description("Тест проверяет обновление имени и пароля пользователя без указания токена авторизации")
    public void updateNamePasswordNoAuthUser() {
        apiClient.updateUserDataAndReturnResponse("", steps.patch[3])
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Обновить параметры Name и Email пользователя без авторизации")
    @Description("Тест проверяет обновление имени и почты пользователя без указания токена авторизации")
    public void updateNameEmailNoAuthUser() {
        apiClient.updateUserDataAndReturnResponse("", steps.patch[4])
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Обновить параметры Password и Email пользователя без авторизации")
    @Description("Тест проверяет обновление пароля и почты пользователя без указания токена авторизации")
    public void updatePasswordEmailNoAuthUser() {
        apiClient.updateUserDataAndReturnResponse("", steps.patch[5])
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Обновить параметры Email, Password, Name пользователя без авторизации")
    @Description("Тест проверяет обновление имени, пароля и почты пользователя без указания токена авторизации")
    public void updateEmailPasswordNameNoAuthUser() {
        apiClient.updateUserDataAndReturnResponse("", steps.patch[6])
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);
    }

}
