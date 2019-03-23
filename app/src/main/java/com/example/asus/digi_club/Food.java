package com.example.asus.digi_club;

public class Food {
    String foodId;
    String foodName;
    String price;
    String quatiy;
    String picturePath;


    public Food(String foodId, String foodName, String price, String quatiy,String picturePath) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
        this.quatiy = quatiy;
        this.picturePath = picturePath;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuatiy() {
        return quatiy;
    }

    public void setQuatiy(String quatiy) {
        this.quatiy = quatiy;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
