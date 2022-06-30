package client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseHttpClient {

    private final String JSON = "application/json";

    //базовая спецификация запроса
    private RequestSpecification requestSpec = given()
            .baseUri("https://stellarburgers.nomoreparties.site")
            .header("Content-Type", JSON);

    //отправить post-запрос
    protected Response doPostRequest (String uri, Object object) {
        return  requestSpec
                .and()
                .body(object)
                .when()
                .post(uri);
    }

    //отправить post-запрос с авторизацией
    protected Response doPostRequest (String uri, Object object, String auth) {
        return  requestSpec
                .header("Authorization", auth)
                .and()
                .body(object)
                .when()
                .post(uri);
    }

    //отправить delete-запрос с авторизацией
    protected Response doDelRequest (String uri, String auth) {
        return requestSpec
                .header("Authorization", auth)
                .delete(uri);
    }

    //отправить patch-запрос с авторизацией
    protected Response doPatchRequest (String uri, String auth, Object object) {
        return requestSpec
                .header("Authorization", auth)
                .body(object)
                .when()
                .patch(uri);
    }

    //отправить get-запрос
    protected Response doGetRequest(String uri) {
        return  requestSpec
                .get(uri);
    }

    //отправить get-запрос с авторизацией
    protected Response doGetRequest(String uri, String auth) {
        return  requestSpec
                .header("Authorization", auth)
                .get(uri);
    }

}
