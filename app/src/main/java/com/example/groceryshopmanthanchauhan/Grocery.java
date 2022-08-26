package com.example.groceryshopmanthanchauhan;

public class Grocery
{
    String gid;
    String imageUrl;
    String gName;
    int price;
    int stock;
    String measure;

    public Grocery() {
    }

    public Grocery(String gid, String imageUrl, String gName, int price, int stock, String measure) {
        this.gid = gid;
        this.imageUrl = imageUrl;
        this.gName = gName;
        this.price = price;
        this.stock = stock;
        this.measure = measure;
    }


    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }



}
