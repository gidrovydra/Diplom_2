package client;

import model.Order;

import io.restassured.response.Response;

public class OrderApiClient extends BaseHttpClient {

    public String handleGetIngredients = "/api/ingredients";
    public String handleCreateOrder = "/api/orders";
    public String handleGetUserOrders = "/api/orders";

    //get-запрос получения списка ингредиентов
    public Response getListIngredients() {
        return doGetRequest(handleGetIngredients);
    }

    //post-запрос создания заказа
    public Response createOrder(String auth, Order order){
        return doPostRequest(handleCreateOrder, order, auth);
    }

    //get-запрос полчения заказов пользователя
    public Response getUserOrders(String auth) {
        return doGetRequest(handleGetUserOrders, auth);
    }

}
