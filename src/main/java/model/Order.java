package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {

    @SerializedName("_id")
    private String id;
    private String status;
    private String number;
    private List<String> ingredients;

    public Order() {
    }

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Order(String id, String status, String number, List<String> ingredients) {
        this.id = id;
        this.status = status;
        this.number = number;
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
