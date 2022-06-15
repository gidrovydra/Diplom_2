package user;

import client.UserApiClient;

import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import io.qameta.allure.Step;

import static org.apache.http.HttpStatus.*;

public class StepsUser {

    private final UserApiClient apiClient = new UserApiClient();

    public String randomEmailUser = RandomStringUtils.randomAlphabetic(10) + "@" +RandomStringUtils.randomAlphabetic(6) + "." + RandomStringUtils.randomAlphabetic(3);
    public String randomPasswordUser = RandomStringUtils.randomAlphabetic(10);
    public String randomNameUser = RandomStringUtils.randomAlphabetic(10);
    public String randomPart = RandomStringUtils.randomAlphabetic(3);
    public Map<String,String>[] patch = new Map[7];


    //создать наборы данных для патчей в тестах
    public void generatePatches(){
        patch[0] = Map.of("Name",randomNameUser + randomPart);
        patch[1] = Map.of("Password", randomPasswordUser + randomPart);
        patch[2] = Map.of("Email", randomEmailUser + randomPart);
        patch[3] = Map.of("Name",randomNameUser+randomPart,"Password", randomPasswordUser + randomPart);
        patch[4] = Map.of("Name",randomNameUser+randomPart,"Email", randomEmailUser + randomPart);
        patch[5] = Map.of("Password", randomPasswordUser + randomPart, "Email", randomEmailUser + randomPart);
        patch[6] = Map.of("Name",randomNameUser+randomPart,"Password", randomPasswordUser + randomPart,"Email", randomEmailUser + randomPart);
    }

   @Step("Создать пользователя с рандомными данными")
   public void createUser() {
       apiClient.createUserAndReturnResponse(randomEmailUser, randomPasswordUser, randomNameUser)
               .then()
               .assertThat()
               .statusCode(SC_OK);
   }

    @Step("Создать пользователя с рандомными данными и вернуть токен авторизации")
    public String createUserAndReturnToken() {
        return apiClient.createUserAndReturnResponse(randomEmailUser, randomPasswordUser, randomNameUser)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .body()
                .path("accessToken");
    }

    @Step("Проверить создавался ли пользователь с заданными параметрами")
    public boolean loginUserSuccess() {
        return apiClient.loginUserAndReturnResponse(randomEmailUser, randomPasswordUser)
                .then()
                .extract()
                .statusCode() == SC_OK;
    }

    @Step("Удалить созданного пользователя")
    public void deleteUser() {
        apiClient.deleteUserAndReturnResponse(randomEmailUser,randomPasswordUser)
                .then()
                .assertThat()
                .statusCode(SC_ACCEPTED);
    }

    @Step("Удалить пользователя по токену")
    public void deleteUser(String auth) {
        apiClient.deleteUserAndReturnResponse(auth)
                .then()
                .assertThat()
                .statusCode(SC_ACCEPTED);
    }

}
