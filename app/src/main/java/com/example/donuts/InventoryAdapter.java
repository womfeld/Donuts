package com.example.donuts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.Locale;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryViewHolder> {

    private Context context;
    private ArrayList<String> names;
    private ArrayList<Integer> quantities;


    //Try this after code works
    private InventoryUpdatePage inventoryUpdatePage;
    public InventoryAdapter(InventoryUpdatePage inventoryUpdatePage, ArrayList<String> names, ArrayList<Integer> quantities){
        //public MenuAdapter(Context context, String[] menuOptionsList, int[] menuImages){

        //this.context = context;
        this.inventoryUpdatePage = inventoryUpdatePage;
        this.names = names;
        this.quantities = quantities;

    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.menu_option, parent, false);
//        MenuViewHolder viewHolder = new MenuViewHolder(view);
//        return viewHolder;

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inventory_item, parent, false);

        itemView.setOnClickListener((View.OnClickListener) this.inventoryUpdatePage);
        itemView.setOnLongClickListener((View.OnLongClickListener) this.inventoryUpdatePage);

        return new InventoryViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {

        quantities.get(0);
        holder.title.setText(names.get(position));
        holder.quantity.setText(String.valueOf(quantities.get(position)));

        holder.plusButton.setOnClickListener(v -> {

            int value = Integer.parseInt(holder.quantity.getText().toString());
            int nv = value + 1;
            inventoryUpdatePage.updateQuantity(position, nv);


//            Intent i = new Intent(this.inventoryUpdatePage, MainActivity.class);
//            inventoryUpdatePage.startActivity(i);

        });

        holder.minusButton.setOnClickListener(v -> {

            int value = Integer.parseInt(holder.quantity.getText().toString());
            if (value != 0) {
                int nv = value - 1;
                holder.quantity.setText(String.valueOf(nv));
                inventoryUpdatePage.updatedPositions.add(position);
                inventoryUpdatePage.updateQuantity(position, nv);
            }


        });

    }

    @Override
    public int getItemCount() {
        return names.size();
    }




}
