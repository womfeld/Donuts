package com.example.donuts;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShoppingCartPage extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    //On back pressed, we return to the home screen

    //Also need to load orders from the database

    //GOING TO HAVE TO EDIT THIS SO WE CAN ADD MULTIPLE ORDERS TO THE CART,
    //AND THEN WHEN WE GO TO THE HOMESCREEN AND HIT MY CART, THE NEWLY ADDED
    //ORDERS ARE PRESENT


    RecyclerView recyclerView;
    RecyclerView.Adapter cAdapter;
    RecyclerView.LayoutManager layoutManager;

    public ArrayList<Order> ordersList = new ArrayList<>();;

    private Order revisedOrder;
    private int position;


    //Shared preferences object
    private SharedPreferences myPrefs;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_page);

        setTitle("Shopping Cart");

        //Initialize shared preferences object myPref
        myPrefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        //Initialize editor for myPref
        SharedPreferences.Editor myPrefEditor = myPrefs.edit();
        //Initialize gson object so it's possible to save objects in sharedPreferences object
        Gson gson = new Gson();


        recyclerView = findViewById(R.id.cartRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        cAdapter = new CartAdapter(this, ordersList);
        recyclerView.setAdapter(cAdapter);

        Intent intent = getIntent();

        if (intent.hasExtra("orders")) {

            //Shared preferences code
            //Fall back on

            //Get arraylist of order objects stored in shared preferences
            //and then set adapter to hold this list of orders
//            Set<String> jsonFormattedStringSet = myPrefs.getStringSet("orderList", new HashSet<>());
//
//            ArrayList<Order> orders = new ArrayList<>();
//
//            try {
//                for (String s: jsonFormattedStringSet) {
//                    JSONObject jObject = new JSONObject(s);
//                    //JSONObject jObject = new JSONObject(jsonFormattedString);
//                    String itemName = jObject.getString("itemName");
//                    int quantity = jObject.getInt("quantity");
//                    double itemPrice = jObject.getDouble("itemPrice");
//                    int img = jObject.getInt("image");
//
//                    JSONArray customItemsJSON = jObject.getJSONArray("customItems");
//                    ArrayList<String> items = new ArrayList<>();
//                    for (int j = 0; j < customItemsJSON.length(); j++) {
//                        items.add(customItemsJSON.getString(j));
//                    }
//                    Order o = new Order(itemName, items, quantity, itemPrice, img);
//                    orders.add(o);
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


            ordersList.addAll(loadFile());
            cAdapter.notifyDataSetChanged();


            //Fall back on
//            ArrayList<Order>orders = (ArrayList<Order>) intent.getSerializableExtra("orders");
//            ordersList.addAll(orders);

            //cAdapter.notifyDataSetChanged();

        }

        else if (intent.hasExtra("editedOrder") && (intent.hasExtra("position"))) {

            revisedOrder = (Order) intent.getSerializableExtra("editedOrder");
            position = intent.getIntExtra("position", 0);

            //CHANGE LATER!!!/////////////////This will cause problems as it is
            ordersList.clear();
            ordersList.add(revisedOrder);


        }
        //counterButton.setNumber("1");

    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putSerializable("ordersList", ordersList);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        this.ordersList = (ArrayList<Order>) savedInstanceState.getSerializable("ordersList");
//    }
//
//


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

                JSONArray customItemsJSON = jObject.getJSONArray("customItems");
                ArrayList<String> items = new ArrayList<>();
                for (int j = 0; j < customItemsJSON.length(); j++) {
                    items.add(customItemsJSON.getString(j));
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    orders.add(new Order(itemName, items, quantity, itemPrice, img));
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



    //Saving the cart in this activity will be different than doing so in main activity
    private void saveCart() {

        try {
            FileOutputStream fos = getApplicationContext().
                    openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            PrintWriter printWriter = new PrintWriter(fos);
            printWriter.print(ordersList);
            printWriter.close();
            fos.close();




        } catch (Exception e) {
            e.getStackTrace();
        }

    }





    @Override
    public void onClick(View v) {

        //System.out.println("hi");

    }



    public void checkOutClicked (View v) {

        Intent goToCheckout = new Intent(this, CheckoutPage.class);
        startActivity(goToCheckout);
    }




    @Override
    public boolean onLongClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        Order o = ordersList.get(pos);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setTitle("Delete?");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            ordersList.remove(o);
            saveCart();
            cAdapter.notifyDataSetChanged();

        });
        builder.setNegativeButton("CANCEL", (dialogInterface, i) -> {

        });
        AlertDialog dialog = builder.create();
        dialog.show();


        return false;
    }

}