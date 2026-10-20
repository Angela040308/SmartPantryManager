package com.example.smartpantrymanager;

public class Ingredient {
    private final String name;
    private final String quantity;
    private final String unit;
    private final String expiryDate;
    private int id;


    public Ingredient(String name, String quantity, String unit, String expiryDate){
        this.name=name;
        this.quantity=quantity;
        this.unit=unit;
        this.expiryDate=expiryDate;
    }
    public Ingredient(int id,String name, String quantity, String unit, String expiryDate){
        this.id=id;
        this.name=name;
        this.quantity=quantity;
        this.unit=unit;
        this.expiryDate=expiryDate;
    }

    public int getId(){
        return id;
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
