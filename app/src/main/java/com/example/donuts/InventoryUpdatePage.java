package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class InventoryUpdatePage extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter nAdapter;
    RecyclerView.LayoutManager layoutManager;

    private ArrayList<String> menuItemNames;
    public ArrayList<Integer>menuItemQuantities;

    private HashMap<String, Integer> map;



    public Set<Integer> updatedPositions = new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_update_page);


        //Change this later, but this code initializes the menu items name list and the number present in the invetory

        menuItemNames = new ArrayList<>();

        String[] tempArray = {"Chocolate Donut", "Powdered Donut", "Glazed Donut", "Jelly Donut", "Apple Fritter",
                "Coffee", "Iced Coffee", "Latte", "Macchiato", "Plain Bagel", "Sesame Bagel", "Cinnamon Bagel"};
        for (String s : tempArray) {
            menuItemNames.add(s);

        }

        menuItemQuantities = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            menuItemQuantities.add(2);
        }


        recyclerView = findViewById(R.id.inventoryRecycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nAdapter = new InventoryAdapter(this, menuItemNames, menuItemQuantities);
        recyclerView.setAdapter(nAdapter);








    }


    public void updateQuantity(int position, int updatedValue) {
        menuItemQuantities.remove(position);
        menuItemQuantities.add(position, updatedValue);
        nAdapter.notifyDataSetChanged();
        updatedPositions.add(position);
    }


    public void updateInventoryClicked(View v) {


        System.out.println(updatedPositions);

        for (Integer i : updatedPositions) {


            int updatedAmount = menuItemQuantities.get(i);
            String itemName = menuItemNames.get(i);


            //Do an SQL query hereto update inventory in database


        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();


    }
}