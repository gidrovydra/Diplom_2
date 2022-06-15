package client;

import model.User;

import io.restassured.response.Response;
import java.util.Map;

public class UserApiClient extends BaseHttpClient {

    public String handleCrateUser = "/api/auth/register";
    public String handleDeleteUser = "/api/auth/user";
    public String handleLoginUser = "/api/auth/login";
    public String handleUpdateUser = "/api/auth/user";

    //post-запрос создание пользователя
    public Response createUserAndReturnResponse(String email, String password, String name) {
        User user = new User(email, password, name);
        return doPostRequest(handleCrateUser, user);
    }

    //post-запрос логин пользователя
    public Response loginUserAndReturnResponse(String email, String password) {
        User user = new User(email, password);
        return doPostRequest(handleLoginUser, user);
    }

    //post-запрос логин пользователя и возврат токена авторизации
    public String loginUserAndReturnAccessToken(String email, String password) {
        return loginUserAndReturnResponse(email, password)
                .then()
                .extract()
                .body()
                .path("accessToken");
    }

    //delete-запрос удаление пользователя
    public Response deleteUserAndReturnResponse(String email, String password) {
        return doDelRequest(handleDeleteUser, loginUserAndReturnAccessToken(email,password));
    }

    //delete-запрос удаление пользователя по токену
    public Response deleteUserAndReturnResponse(String auth) {
        return doDelRequest(handleDeleteUser, auth);
    }

    //patch-запрос обновление данных пользователя
    public Response updateUserDataAndReturnResponse(String auth, Map<String,String> patch) {
        User user = new User();

        for (Map.Entry<String, String> pair : patch.entrySet()) {
            if (pair.getKey().equals("Email"))
                user.setEmail(pair.getValue());
            else if (pair.getKey().equals("Password"))
                user.setPassword(pair.getValue());
            else if (pair.getKey().equals("Name"))
                user.setName(pair.getValue());
        }

        return doPatchRequest(handleUpdateUser, auth, user);
    }

}
