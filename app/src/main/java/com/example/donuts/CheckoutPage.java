package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CheckoutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);

        setTitle("Checkout");
    }
}