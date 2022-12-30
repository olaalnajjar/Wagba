package com.example.wagba.Model;

public class HistoryModel {

    private String order_name;
    private String order_date;
    private String order_price;
    private String order_status="";
    //private int restaurant_img_id;
    //private int food_type_img_id;
    private String order_item_1;
    private String order_item_2;
    private String order_item_3;
    private String order_item_1_price;
    private String order_item_2_price;
    private String order_item_3_price;
    private String total_price_details;
    private String delivery_area;

    public HistoryModel() {
    }

    private boolean expanded;

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    /*public int getRestaurant_img_id() {
        return restaurant_img_id;
    }

    public void setRestaurant_img_id(int restaurant_img_id) {
        this.restaurant_img_id = restaurant_img_id;
    }

    public int getFood_type_img_id() {
        return food_type_img_id;
    }

    public void setFood_type_img_id(int food_type_img_id) {
        this.food_type_img_id = food_type_img_id;
    }*/

    public String getOrder_item_1() {
        return order_item_1;
    }

    public void setOrder_item_1(String order_item_1) {
        this.order_item_1 = order_item_1;
    }

    public String getOrder_item_2() {
        return order_item_2;
    }

    public void setOrder_item_2(String order_item_2) {
        this.order_item_2 = order_item_2;
    }

    public String getOrder_item_3() {
        return order_item_3;
    }

    public void setOrder_item_3(String order_item_3) {
        this.order_item_3 = order_item_3;
    }

    public String getOrder_item_1_price() {
        return order_item_1_price;
    }

    public void setOrder_item_1_price(String order_item_1_price) {
        this.order_item_1_price = order_item_1_price;
    }

    public String getOrder_item_2_price() {
        return order_item_2_price;
    }

    public void setOrder_item_2_price(String order_item_2_price) {
        this.order_item_2_price = order_item_2_price;
    }

    public String getOrder_item_3_price() {
        return order_item_3_price;
    }

    public void setOrder_item_3_price(String order_item_3_price) {
        this.order_item_3_price = order_item_3_price;
    }

    public String getTotal_price_details() {
        return total_price_details;
    }

    public void setTotal_price_details(String total_price_details) {
        this.total_price_details = total_price_details;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getDelivery_area() {
        return delivery_area;
    }

    public void setDelivery_area(String delivery_area) {
        this.delivery_area = delivery_area;
    }

    public HistoryModel(String order_name, String order_date, String order_price, String order_status,/* int restaurant_img_id, int food_type_img_id,*/ String order_item_1, String order_item_2, String order_item_3, String order_item_1_price, String order_item_2_price, String order_item_3_price, String total_price_details, String delivery_area) {
        this.order_name = order_name;
        this.order_date= order_date;
        this.order_price= order_price;
        this.order_status = order_status;
        this.order_item_1= order_item_1;
        this.order_item_2= order_item_2;
        this.order_item_3 = order_item_3;
        this.order_item_1_price= order_item_1_price;
        this.order_item_2_price= order_item_2_price;
        this.order_item_3_price= order_item_3_price;
        this.total_price_details= total_price_details;
       /* this.restaurant_img_id = restaurant_img_id;
        this.food_type_img_id = food_type_img_id;*/
        this.expanded=false;
        this.delivery_area=delivery_area;


    }


}

