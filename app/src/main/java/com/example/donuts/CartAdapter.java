package com.example.donuts;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private ShoppingCartPage shoppingCartPage;
    private ArrayList<Order> orderslist;
    private MainActivity mainActivity;

    public CartAdapter(ShoppingCartPage shoppingCartPage, ArrayList<Order> orderArrayList){
        this.shoppingCartPage = shoppingCartPage;
        this.orderslist = orderArrayList;
    }

    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);

        itemView.setOnClickListener(shoppingCartPage);
        itemView.setOnLongClickListener(shoppingCartPage);

        return new CartViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Order o = orderslist.get(position);

        holder.cartSlotTitle.setText(o.getName());

        //CHANGE THIS LATER
        String formattedDescription = "Notes: " + o.getCustomItemsString().replace("[", "");
        formattedDescription = formattedDescription.replace("]", "");
        holder.description.setText(formattedDescription);
        holder.quantityOfItem.setText(o.getQuantity() + "");
        holder.priceOfItem.setText("$"+String.format(Locale.US, "%.2f",o.getItemPrice()));

        holder.editOrderButton.setOnClickListener(v -> {
            Intent i = new Intent(shoppingCartPage, OrderPage.class);

            i.putExtra("orderToEdit", o);
            i.putExtra("position", position);
            shoppingCartPage.startActivity(i);

        });




    }

    @Override
    public int getItemCount() {
        return orderslist.size();
    }


}
