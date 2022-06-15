package order;

import client.OrderApiClient;
import model.Order;
import model.Ingredient;
import model.Ingredients;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Step;

import static org.hamcrest.Matchers.equalTo;

public class StepsOrder {

    private OrderApiClient apiClient = new OrderApiClient();
    private List<Ingredient> ingredientsList;
    private List<String> idValidIngredientsList = new ArrayList<>();
    private List<String> idInvalidIngredientList = new ArrayList<>();


    //получить список всех ингредиентов
    private List<Ingredient> getListIngredient(){
        return apiClient.getListIngredients()
                .then()
                .assertThat().statusCode(200)
                .body("success", equalTo(true))
                .extract().as(Ingredients.class)
                .getData();
    }

    //заполнить список id всех валидных ингредиентов и сгенерировать список id невалидных ингредиентов для тестов
    private void fillIdIngredientForTest(){
        ingredientsList = getListIngredient();

        for (Ingredient ingredient : ingredientsList) {
            idValidIngredientsList.add(ingredient.getId());
        }

        for (int i = 0; i<ingredientsList.size(); i++){
            idInvalidIngredientList.add(RandomStringUtils.randomAlphanumeric(5));
        }
    }

    @Step("Создать заказ с учетом заданного условия")
    public ValidatableResponse createOrder(String auth, int indicator) {
        int countIngrs;
        Order order = new Order();

        fillIdIngredientForTest();
        countIngrs = ingredientsList.size()-1;

        if (indicator == 1) {
            order.setIngredients(List.of(idValidIngredientsList.get(RandomUtils.nextInt(0,countIngrs)), idValidIngredientsList.get(RandomUtils.nextInt(0,countIngrs)), idValidIngredientsList.get(RandomUtils.nextInt(0,countIngrs))));
        }
        else if (indicator == 2) {
            order.setIngredients(List.of(idInvalidIngredientList.get(RandomUtils.nextInt(0,countIngrs)), idInvalidIngredientList.get(RandomUtils.nextInt(0,countIngrs)), idInvalidIngredientList.get(RandomUtils.nextInt(0,countIngrs))));
        }
        else if (indicator == 3) {
            order.setIngredients(List.of(idValidIngredientsList.get(RandomUtils.nextInt(0,countIngrs)), idValidIngredientsList.get(RandomUtils.nextInt(0,countIngrs)), idInvalidIngredientList.get(RandomUtils.nextInt(0,countIngrs))));
        }
        else if (indicator == 4) {
            order.setIngredients(null);
        }

        return apiClient.createOrder(auth,order)
                .then()
                .assertThat();
    }

}


