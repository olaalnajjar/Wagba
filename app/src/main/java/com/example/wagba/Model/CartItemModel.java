package com.example.wagba.Model;

public class CartItemModel {
    private String dish_name;
    private String dish_description;
    private String dish_price;
    private int img_id;
    private String number;

    public String getDishName() {
        return dish_name;
    }

    public void SetDishName(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDishDescription() {
        return dish_description;
    }

    public void SetDishDescription(String dish_description) {
        this.dish_description = dish_description;
    }

    public String getDishPrice() {
        return dish_price;
    }

    public void setDishPrice(String dish_price) {
        this.dish_price = dish_price;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CartItemModel(String dish_name, String dish_description, String dish_price, int img_id,String number) {
        this.dish_name = dish_name;
        this.dish_description= dish_description;
        this.dish_price= dish_price;
        this.img_id = img_id;
        this.number = number;
    }
}
