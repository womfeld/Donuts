package com.example.donuts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ImageButton donutsImage;
    ImageButton beveragesImage;
    ImageButton bagelsImage;
    ImageButton sandwichesImage;

    //Array that will save the user's orders
    ArrayList<Order>userOrders = new ArrayList<>();

    private static final String TAG = "MainActivity";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Montrose Donuts and Deli");

        Intent intent = getIntent();

        //This section of code receives the intent with the menu (titles and images) and saves
        //the menu titles into menuList and the menu images into menuImages
        if (intent.hasExtra("order")) {

            Order orderItem = (Order) intent.getSerializableExtra("order");
            userOrders.add(orderItem);
            Toast.makeText(this, "Item successfully added to cart!", Toast.LENGTH_SHORT).show();

        }



        //Initializing all of the image buttons
        donutsImage = findViewById(R.id.donutsImage);
        beveragesImage = findViewById(R.id.beveragesImage);
        bagelsImage = findViewById(R.id.bagelsImage);
        sandwichesImage = findViewById(R.id.sandwichesImage);




        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        // Now check for menu items
        if (item.getItemId() == R.id.goToHomePage) {
            Toast.makeText(this, "Home Page", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.goToManagerPage) {
            Intent i = new Intent(this, ManagerPage.class);
            startActivity(i);
            return true;
        } else if (item.getItemId() == R.id.goToShoppingCart) {
            Intent goToCart = new Intent(this, ShoppingCartPage.class);
            goToCart.putExtra("orders", userOrders);
            startActivity(goToCart);
            return true;
        } else if (item.getItemId() == R.id.goToCreateAccount) {
            Intent goToCreateAcc = new Intent(this, CreateAccount.class);
            startActivity(goToCreateAcc);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }


    public void imageClicked(View v) {

        Intent i = new Intent(this, MenuOptionsPage.class);

        if (v == donutsImage) {
            //Donut menu item titles
            String[] donutsList = {"Chocolate Donuts", "Powdered Donuts", "Glazed Donuts", "Jelly Donuts", "Apple Fritters"};
            //Images of the donut menu items
            int[] donutImagesList = {R.drawable.chocolate_donuts, R.drawable.powdered_donuts, R.drawable.glazed_donuts,
                    R.drawable.jelly_donuts, R.drawable.apple_fritters};
            i.putExtra("menu", donutsList);
            i.putExtra("images", donutImagesList);
        }

        if (v==beveragesImage) {
            String[] beveragesList = {"Coffee"};
            int[] beverageImagesList = {};
            i.putExtra("menu", beveragesList);
            i.putExtra("images", beverageImagesList);

        }

        if (v == bagelsImage) {
            String[] bagelsList = {};
            int[] bagelImagesList = {};
            i.putExtra("menu", bagelsList);
            i.putExtra("images", bagelImagesList);

        }

        if (v == sandwichesImage) {
            String[] sandwichesList = {};
            int[] sandwichImagesList = {};
            i.putExtra("menu", sandwichesList);
            i.putExtra("images", sandwichImagesList);

        }

        startActivity(i);

    }


}
