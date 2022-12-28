package com.example.wagba.Model;

public class CartItemModel {
    private String dish_name;
    private String dish_description;
    private String dish_price;
    private String img_id;
    private String number;

    public CartItemModel() {
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDish_description() {
        return dish_description;
    }

    public void setDish_description(String dish_description) {
        this.dish_description = dish_description;
    }

    public String getDish_price() {
        return dish_price;
    }

    public void setDish_price(String dish_price) {
        this.dish_price = dish_price;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CartItemModel(String dish_name, String dish_description, String dish_price, String img_id, String number) {
        this.dish_name = dish_name;
        this.dish_description= dish_description;
        this.dish_price= dish_price;
        this.img_id = img_id;
        this.number = number;
    }
}
