package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class CheckoutPage extends AppCompatActivity {

    TextView priceLabel;
    public double totalPrice;

    TextView rewardPointsLabel;
    public int rewardPoints;

    private int rewardPointsEarned;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);

        setTitle("Checkout");

        priceLabel = findViewById(R.id.checkoutPriceLabel);
        rewardPointsLabel = findViewById(R.id.checkoutRewardLabel);


        //Initialize rewardPoints
        //rewardPoints will be pulled from sharedPreferences
        //Remember, the sharedPreference object is to be initialized after
        //user logs in and is to be filled filled with info from database

        rewardPoints = 11; //TEMPORARY VALUE!!!!



        Intent intent = getIntent();

        if (intent.hasExtra("totalPrice")) {

            totalPrice = intent.getDoubleExtra("totalPrice", 0);
            priceLabel.setText("Total: " + "$"+String.format(Locale.US, "%.2f",totalPrice));
        }

        else {

        }




    }







//
//    //Maybe make separate functions that handle reward points for drinks, donuts, and bagels
//    //Occurs after clicking redeem
//    //Make sure to update database and sharedPreferences as well
//
//    //Function for donuts
//    //In the loop statement, add && numOfDonuts > 0
//        while (rewardPoints >= 8 && totalPrice > 0) {
//
//        //We are assuming 8 for donut or bagel or drink
//        //Later, 10 for drink and 6 for bagel
//
//        //Maybe count the number of donuts, bagels, and or drinks and send in intent extra
//        //from shopping cart page.  This way, we can use this code:
//
//        //if (numOfDrinks > 0) {
//        // rewardPoints = rewardPoints - 10
//
//        totalPrice = totalPrice - 1.5;
//        rewardPoints = rewardPoints - 8;
//
//    }











}