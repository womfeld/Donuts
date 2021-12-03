package com.example.donuts;

import androidx.annotation.NonNull;

import android.util.JsonWriter;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Review implements Serializable {

    private String name;
    private String description;
    private int stars;




    public Review(String n, String d, int t){

        this.name = n;
        this.description = d;
        this.stars =  t;


    }



    public String getDescription() {

        return description;
    }

    public String getName() {

        return name;
    }

    public int getStars() {

        return stars;
    }








}
