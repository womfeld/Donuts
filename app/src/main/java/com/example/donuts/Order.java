package com.example.donuts;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    //If we want to add custom items that cost more, maybe add a parameter called extra items
    //Pass in another array, no need to create another object

    private String itemName;
    public ArrayList<String> customItems;
    private int quantity;
    private double itemPrice;
    private int image;

    public Order (String itemName, ArrayList<String> items, int quantity, double itemPrice, int img) {
        this.itemName = itemName;
        //Initialize customItems
        customItems = new ArrayList<>();
        setCustomItems(items);
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.image = img;
    }

    public Order() {
    }

    public String getName() {
        return itemName;
    }

    public ArrayList<String> getCustomItems() {
        return this.customItems;
    }

    public String getCustomItemsString() {
        String s = customItems.toString();
        return s;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getImage() {
        return image;
    }

    public void setQuantity(int q) {
        this.quantity = q;
    }

    public void setItemPrice(double p) {
        this.itemPrice = p;
    }

    //Loads values of the inputted array items into the Order class array customItems
    public void setCustomItems(ArrayList<String> items) {
        this.customItems.clear();
        this.customItems.addAll(items);
    }

    public void setImage(int img) {
        this.image = img;
    }

}
