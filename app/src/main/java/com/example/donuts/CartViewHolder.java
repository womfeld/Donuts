package com.example.donuts;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

public class CartViewHolder extends RecyclerView.ViewHolder {

    TextView cartSlotTitle;
    ElegantNumberButton numberButton;
    TextView description;
    TextView quantityOfItem;
    TextView priceOfItem;
    Button editOrderButton;

    public CartViewHolder(View itemView) {
        super(itemView);

        cartSlotTitle = itemView.findViewById(R.id.shoppingItem);
        quantityOfItem = itemView.findViewById(R.id.quantityInCart);
        description = itemView.findViewById(R.id.orderDescription);
        priceOfItem = itemView.findViewById(R.id.itemPriceInCart);
        editOrderButton = itemView.findViewById(R.id.editOrderButton);

    }
}

