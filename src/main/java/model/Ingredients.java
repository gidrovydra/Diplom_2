package model;

import java.util.List;

public class Ingredients {

    private List<Ingredient> data;
    private boolean success;

    public Ingredients(List<Ingredient> data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public List<Ingredient> getData() {
        return data;
    }

    public void setData(List<Ingredient> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
