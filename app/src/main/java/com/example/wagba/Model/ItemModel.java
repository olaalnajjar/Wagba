package com.example.wagba.Model;

public class ItemModel {
    private String dish_name;
    private String Description;
    private String Price;
    private String Image;
    private String Status;


    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public ItemModel() {
    }


    public ItemModel(String dish_name, String dish_description, String Price, String img_id, String Status) {
        this.dish_name = dish_name;
        this.Description= dish_description;
        this.Price = Price;
        this.Image = img_id;
        this.Status = Status;
    }
}
