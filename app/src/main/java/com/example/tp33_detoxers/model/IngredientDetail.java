package com.example.tp33_detoxers.model;

public class IngredientDetail {
    private String iName;
    private String iQuantity;
    private String iLevel;

    public IngredientDetail(String iName, String iQuantity, String iLevel) {
        this.iName = iName;
        this.iQuantity = iQuantity;
        this.iLevel = iLevel;
    }

    public String getiName() {
        return iName;
    }

    public String getiQuantity() {
        return iQuantity;
    }

    public String getiLevel() {
        return iLevel;
    }
}
