package com.example.smartpantrymanager;

public class Ingredient {
    private String name;
    private String quantity;
    private String unit;
    private String expiryDate;

    public Ingredient(String name, String quantity, String unit, String expiryDate){
        this.name=name;
        this.quantity=quantity;
        this.unit=unit;
        this.expiryDate=expiryDate;
    }
    public String getName(){
        return name;
    }
    public String getQuantity(){
        return quantity;
    }
    public String getUnit(){
        return unit;
    }
    public String getExpiryDate(){
        return expiryDate;
    }
}
