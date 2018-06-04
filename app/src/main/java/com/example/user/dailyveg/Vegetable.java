package com.example.user.dailyveg;

/**
 * Created by user on 16-03-2018.
 */

public class Vegetable{

    public int imageId;
    String name = null;
    boolean selected = false;
    public String price;
    String quantity;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getPrice(){
        return price;
    }

    public Vegetable(int imageId , String name, String price, String quantity, boolean selected) {
        super();
        this.imageId = imageId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.selected = selected;
    }

    public String getQuantity(){
        return quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}