package com.example.donuts;

import android.content.Context;
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

import com.google.gson.Gson;

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






    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Montrose Donuts and Deli");


        //Initialize shared preferences object myPref
        myPrefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        //Initialize editor for myPref
        SharedPreferences.Editor myPrefEditor = myPrefs.edit();
        //Initialize gson object so it's possible to save objects in sharedPreferences object
        Gson gson = new Gson();



        Intent intent = getIntent();

        //This section of code receives the intent with the menu (titles and images) and saves
        //the menu titles into menuList and the menu images into menuImages
        if (intent.hasExtra("order")) {




            //Preference code
            //Fall back on
            /////////////////////////
//            String jsonFormattedObject = myPrefs.getString("lastOrder", "");
//
//            Set<String> s = myPrefs.getStringSet("orderList", new HashSet<>());
//
//            s.add(jsonFormattedObject);
//
//            //Must dp this so set is saved
//            myPrefEditor.clear();
//
//            myPrefEditor.putStringSet("orderList", s);
//            myPrefEditor.commit();
//
//            Toast.makeText(this, "Item successfully added to cart!", Toast.LENGTH_SHORT).show();

            ///////////////////////////




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
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        // Now check for menu items
        if (item.getItemId() == R.id.goToHomePage) {
            Toast.makeText(this, "Home Page", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.goToManagerPage) {
            Intent i = new Intent(this, UserPortal.class);
            startActivity(i);
            return true;
        } else if (item.getItemId() == R.id.goToShoppingCart) {

            Intent goToCart = new Intent(this, ShoppingCartPage.class);
            //Fall back on
            goToCart.putExtra("orders", userOrders);
            startActivity(goToCart);

            //Fall back on for shared preferences
//            goToCart.putExtra("orders", "userOrders");
//            startActivity(goToCart);

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
