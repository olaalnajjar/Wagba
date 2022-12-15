package com.example.wagba.Model;

public class StoreModel {

    private String title;

    private String img_id;

    public StoreModel() {
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public StoreModel(String title, String img_id) {
        this.title = title;
        this.img_id = img_id;
    }

}
