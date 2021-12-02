package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Locale;

public class CheckoutPage extends AppCompatActivity {

    TextView priceLabel;
    public double flatRatePrice;
    public double totalPrice;

    public EditText creditNumberInput;
    public EditText cvvInput;
    public EditText addressInput;

    public Switch pickUpOtion;

    public StoreDatabase storeDatabase;

    private User user;

    private int rewardPointsEarned;

    public SharedPreferences myPrefs;

    public HashMap<String, Integer> updatedInventoryItems;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);

        setTitle("Checkout");

        storeDatabase = new StoreDatabase(this);

        priceLabel = findViewById(R.id.checkoutPriceLabel);
        pickUpOtion = findViewById(R.id.switch1);


        creditNumberInput = findViewById(R.id.creditNumberTextField);
        cvvInput = findViewById(R.id.cvvTextField);
        addressInput = findViewById(R.id.addressTextField);

        Intent intent = getIntent();

        if (intent.hasExtra("updatedInventoryItems")) {
            updatedInventoryItems = (HashMap) intent.getSerializableExtra("updatedInventoryItems");

        }

        if (intent.hasExtra("userInfo")) {
            user = (User) intent.getSerializableExtra("userInfo");
        }

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


    public void submitOrderClicked(View v) {

        //Add user order
        String userName = user.getUserName();
        //totalPrice;
        storeDatabase.addOrder(totalPrice, userName);


        //Add customer purchase details
        storeDatabase.addCustomer(userName, creditNumberInput.getText().toString(), Integer.parseInt(cvvInput.getText().toString()), addressInput.getText().toString());


        //Update Inventory
        storeDatabase.updateInventory(updatedInventoryItems);


        //Update reward points
        myPrefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        int rewardPointsRemaining = myPrefs.getInt("remainingRewardPoints", 0);
        rewardPointsEarned = (int) totalPrice;
        int newRewardPointsValue = rewardPointsEarned + rewardPointsRemaining;
        storeDatabase.updateRewardPoints(userName, newRewardPointsValue);


        Intent i = new Intent(this, OrderCompletePage.class);
        startActivity(i);


    }

    @Override
    protected void onDestroy() {
        storeDatabase.shutDown();
        super.onDestroy();
    }











}