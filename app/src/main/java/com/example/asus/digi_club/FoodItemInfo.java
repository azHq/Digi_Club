package com.example.asus.digi_club;

public class FoodItemInfo {

    public String imagepath;
    public int id;
    public String name;
    public double price;

    public FoodItemInfo(int id, String name, double price,String imagepath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imagepath = imagepath;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
