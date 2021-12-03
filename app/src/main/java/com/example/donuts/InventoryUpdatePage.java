package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    StoreDatabase storeDatabase;

    public User user;



    public Set<Integer> updatedPositions = new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_update_page);

        storeDatabase = new StoreDatabase(this);

        Intent intent = getIntent();
        if (intent.hasExtra("userInfo")) {
            user = (User) intent.getSerializableExtra("userInfo");
        }


        HashMap<String, Integer> fullInventory = storeDatabase.loadInventory();
        menuItemNames = new ArrayList<>();
        menuItemQuantities = new ArrayList<>();

        for (String s : fullInventory.keySet()) {
            menuItemNames.add(s);
            menuItemQuantities.add(fullInventory.get(s));
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

        HashMap<String, Integer> map = new HashMap<>();

        System.out.println(updatedPositions);

        for (Integer i : updatedPositions) {


            int updatedAmount = menuItemQuantities.get(i);
            String itemName = menuItemNames.get(i);

            map.put(itemName, updatedAmount);


        }

        storeDatabase.updateInventory(map);
        Toast.makeText(this, "Inventory Updated!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ManagerPortal.class);
        i.putExtra("userInfo", user);
        startActivity(i);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        storeDatabase.shutDown();
        super.onDestroy();
    }


}