package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

public class CheckoutPage extends AppCompatActivity {

    TextView priceLabel;
    public double flatRatePrice;
    public double totalPrice;

    public Switch pickUpOtion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);

        setTitle("Checkout");

        priceLabel = findViewById(R.id.checkoutPriceLabel);
        pickUpOtion = findViewById(R.id.switch1);



        Intent intent = getIntent();

        if (intent.hasExtra("totalPrice")) {

            totalPrice = intent.getDoubleExtra("totalPrice", 0);
            flatRatePrice = totalPrice;
            priceLabel.setText("Total: " + "$"+String.format(Locale.US, "%.2f",totalPrice));
        }

        else {

            //Error of some kind

        }











    }



    public void switchClicked(View v) {

        if (((Switch) v).isChecked()) {
            if (totalPrice > 0) {
                totalPrice = totalPrice + totalPrice * 0.2;
            }
            else {
                totalPrice = 1.79;
            }

        }
        else {

            totalPrice = flatRatePrice;

        }

        priceLabel.setText("Total: " + "$"+String.format(Locale.US, "%.2f",totalPrice));

    }




    public  void editOrderClicked(View v) {
        Intent i = new Intent(this, ShoppingCartPage.class);
        startActivity(i);
    }











}