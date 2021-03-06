package com.example.donuts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ImageButton donutsImage;
    ImageButton beveragesImage;
    ImageButton bagelsImage;
    ImageButton sandwichesImage;

    //Array that will save the user's orders
    ArrayList<Order>userOrders = new ArrayList<>();

    private static final String TAG = "MainActivity";

    //Shared preferences object
    private SharedPreferences myPrefs;

    String role;


    private User user;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Montrose Donuts and Deli");


        //Initialize shared preferences object myPref




        Intent intent = getIntent();

        if (intent.hasExtra("registeredUser") || intent.hasExtra("newUser")) {

            if (intent.hasExtra("registeredUser")) {
                user = (User) intent.getSerializableExtra("registeredUser");
            }
            else {
                user = (User) intent.getSerializableExtra("newUser");
            }

            //Need to clear json file when user logins in
            clearCart();



        }




        if (intent.hasExtra("userInfo")) {
            user = (User) intent.getSerializableExtra("userInfo");
            //saveUserToSharedPreferences();
        }

        //Initialize user role obtained from database
        myPrefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        role = myPrefs.getString("userRole", "");




        //When logging in or creating an account we have to clear the JSON file





        //This section of code receives the intent with the menu (titles and images) and saves
        //the menu titles into menuList and the menu images into menuImages
        if (intent.hasExtra("order")) {


              //Fall back on
            Order orderItem = (Order) intent.getSerializableExtra("order");
            //userOrders.add(orderItem);
            saveOrderToCart(orderItem);
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
        if (role.equals("Regular")) {
            getMenuInflater().inflate(R.menu.regular_user_menu, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.manager_home_menu, menu);
        }
        return true;
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.goToShoppingCart && role.equals("Regular")) {

            Intent goToCart = new Intent(this, ShoppingCartPage.class);
            //Fall back on
            goToCart.putExtra("orders", userOrders);
            goToCart.putExtra("userInfo", user);
            startActivity(goToCart);
            return true;

        }
        else if (item.getItemId() == R.id.goToUserProfile && role.equals("Regular")) {
            Intent goToUserProfile = new Intent(this, UserProfilePage.class);
            goToUserProfile.putExtra("userInfo", user);
            startActivity(goToUserProfile);
            return true;
        }
        else if (item.getItemId() == R.id.logout && role.equals("Regular")) {
            showLogoutWarning();
            return true;
        }

        else if (item.getItemId() == R.id.goToCartAsManager) {


            Intent goToCart = new Intent(this, ShoppingCartPage.class);
            //Fall back on
            goToCart.putExtra("orders", userOrders);
            goToCart.putExtra("userInfo", user);
            startActivity(goToCart);
        }

        else if (item.getItemId() == R.id.goToManagerPortal) {


            Intent goToPortal = new Intent(this, ManagerPortal.class);
            goToPortal.putExtra("userInfo", user);
            startActivity(goToPortal);

        }
        else if (item.getItemId() == R.id.logoutAsManager) {
            showLogoutWarning();
            return true;

        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }






    private void logoutOfSystem() {
        SharedPreferences.Editor myPrefEditor = myPrefs.edit();
        myPrefEditor.clear();
        myPrefEditor.commit();

        Intent i = new Intent(this, LoginPage.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        showLogoutWarning();
        //super.onBackPressed();
    }


    public void showLogoutWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to log out of the system?");
        builder.setPositiveButton("YES", (dialogInterface, i) -> logoutOfSystem());
        builder.setNegativeButton("NO", (dialogInterface, i) -> { });
        AlertDialog dialog = builder.create();
        dialog.show();

    }







    public void imageClicked(View v) {

        Intent i = new Intent(this, MenuOptionsPage.class);

        if (v == donutsImage) {
            //Donut menu item titles
            String[] donutsList = {"Chocolate Donut", "Powdered Donut", "Glazed Donut", "Jelly Donut", "Apple Fritter"};
            //Images of the donut menu items
            int[] donutImagesList = {R.drawable.donuts_chocolate, R.drawable.donuts_powdered, R.drawable.donuts_glazed,
                    R.drawable.donuts_jelly, R.drawable.donuts_apple_fritters};
            i.putExtra("menu", donutsList);
            i.putExtra("images", donutImagesList);
            i.putExtra("type", "Donut");
        }

        if (v==beveragesImage) {
            String[] beveragesList = {"Coffee", "Iced Coffee", "Latte", "Macchiato"};
            int[] beverageImagesList = {R.drawable.beverages_coffee, R.drawable.beverages_iced_coffee,
                    R.drawable.beverages_latte, R.drawable.beverages_macchiato};
            i.putExtra("menu", beveragesList);
            i.putExtra("images", beverageImagesList);
            i.putExtra("type", "Beverage");
        }

        if (v == bagelsImage) {
            String[] bagelsList = {"Plain Bagel", "Sesame Bagel", "Cinnamon Bagel"};
            int[] bagelImagesList = {R.drawable.bagels_plain, R.drawable.bagels_sesame, R.drawable.bagels_cinnamon};
            i.putExtra("menu", bagelsList);
            i.putExtra("images", bagelImagesList);
            i.putExtra("type", "Bagel");

        }

        if (v == sandwichesImage) {
            String[] sandwichesList = {};
            int[] sandwichImagesList = {};
            i.putExtra("menu", sandwichesList);
            i.putExtra("images", sandwichImagesList);

        }

        i.putExtra("userInfo", user);
        startActivity(i);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveOrderToCart(Order order) {


        //This creates the JSON file to put the name and description contents in
        //If deciding to move forward with this app, will be more efficient to use Broadcast and Receive
        userOrders.addAll(loadFile());
        userOrders.add(order);


        try {
            FileOutputStream fos = getApplicationContext().
                    openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            PrintWriter printWriter = new PrintWriter(fos);
            printWriter.print(userOrders);
            printWriter.close();
            fos.close();

        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void clearCart() {


        //This creates the JSON file to put the name and description contents in
        //If deciding to move forward with this app, will be more efficient to use Broadcast and Receive
        userOrders.clear();


        try {
            FileOutputStream fos = getApplicationContext().
                    openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            PrintWriter printWriter = new PrintWriter(fos);
            printWriter.print(userOrders);
            printWriter.close();
            fos.close();

        } catch (Exception e) {
            e.getStackTrace();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private ArrayList<Order> loadFile() {

        ArrayList<Order> orders = new ArrayList<>();
        try {
            InputStream is = getApplicationContext().openFileInput("Note.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }


            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i=0; i < jsonArray.length(); i++) {

                JSONObject jObject = jsonArray.getJSONObject(i);
                String itemName = jObject.getString("itemName");
                int quantity = jObject.getInt("quantity");
                double itemPrice = jObject.getDouble("itemPrice");
                int img = jObject.getInt("image");
                String type = jObject.getString("type");

                JSONArray customItemsJSON = jObject.getJSONArray("customItems");
                ArrayList<String> items = new ArrayList<>();
                for (int j = 0; j < customItemsJSON.length(); j++) {
                    items.add(customItemsJSON.getString(j));
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    orders.add(new Order(itemName, items, quantity, itemPrice, img, type));
                }
            }

        } catch (FileNotFoundException e) {
            //Toast.makeText(this, "No JSON Note File Present", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return orders;
    }








}
