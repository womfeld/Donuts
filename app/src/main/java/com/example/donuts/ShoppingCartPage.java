package com.example.donuts;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;

public class ShoppingCartPage extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    //On back pressed, we return to the home screen

    //Also need to load orders from the database

    //GOING TO HAVE TO EDIT THIS SO WE CAN ADD MULTIPLE ORDERS TO THE CART,
    //AND THEN WHEN WE GO TO THE HOMESCREEN AND HIT MY CART, THE NEWLY ADDED
    //ORDERS ARE PRESENT

    private static final int E_REQUEST_CODE = 0;


    RecyclerView recyclerView;
    RecyclerView.Adapter cAdapter;
    RecyclerView.LayoutManager layoutManager;

    public ArrayList<Order> ordersList = new ArrayList<>();;

    private Order revisedOrder;
    private int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_page);

        setTitle("Shopping Cart");


        recyclerView = findViewById(R.id.cartRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        cAdapter = new CartAdapter(this, ordersList);
        recyclerView.setAdapter(cAdapter);

        Intent intent = getIntent();

        if (intent.hasExtra("orders")) {

            ArrayList<Order>orders = (ArrayList<Order>) intent.getSerializableExtra("orders");
            ordersList.addAll(orders);

        }

        else if (intent.hasExtra("editedOrder") && (intent.hasExtra("position"))) {

            revisedOrder = (Order) intent.getSerializableExtra("editedOrder");
            position = intent.getIntExtra("position", 0);
            //CHANGE LATER!!!
            ordersList.clear();
            ordersList.add(revisedOrder);


        }





        //counterButton.setNumber("1");

    }

    /*
    public void requestToEditItem(Order item, int pos) {
        Intent i = new Intent();
        i.putExtra("orderEdit", item);
        i.putExtra("position", pos);
        startActivity(i);
    }
    */


    @Override
    public void onClick(View v) {

        System.out.println("hi");

    }

    public void checkOutClicked (View v) {

        Intent goToCheckout = new Intent(this, CheckoutPage.class);
        startActivity(goToCheckout);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}