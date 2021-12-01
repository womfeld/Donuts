package com.example.donuts;

import android.util.JsonWriter;

import androidx.annotation.NonNull;

import org.json.JSONArray;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;

public class Order implements Serializable {

    //If we want to add custom items that cost more, maybe add a parameter called extra items
    //Pass in another array, no need to create another object

    private String itemName;
    public ArrayList<String> customItems;
    private int quantity;
    private double itemPrice;
    private int image;
    private String type;

    public Order (String itemName, ArrayList<String> items, int quantity, double itemPrice, int img, String type) {
        this.itemName = itemName;
        //Initialize customItems
        customItems = new ArrayList<>();
        setCustomItems(items);
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.image = img;
        this.type = type;
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

    public String getType() { return type; }

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

    public void setType(String t) { this.type = t; }


    @NonNull
    public String toString() {

        try {


            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(sw);
            jsonWriter.setIndent("   ");
            jsonWriter.beginObject();
            jsonWriter.name("itemName").value(getName());
            jsonWriter.name("quantity").value(getQuantity());
            jsonWriter.name("itemPrice").value(getItemPrice());
            jsonWriter.name("image").value(getImage());
            jsonWriter.name("type").value(getType());

            //Begins array within object for custom items
            jsonWriter.name("customItems").beginArray();
            for (String s : getCustomItems()) {
                jsonWriter.value(s);
            }
            jsonWriter.endArray();

            jsonWriter.endObject();
            jsonWriter.close();
            return sw.toString();




        } catch (IOException e) {
            e.printStackTrace();

        }


        return "";

    }

}
