package com.example.donuts;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class InventoryViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView quantity;
//    EditText input;
    Button plusButton;
    Button minusButton;



    public InventoryViewHolder(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.inventoryTitle);
        plusButton = itemView.findViewById(R.id.plusButton);
        minusButton = itemView.findViewById(R.id.minusButton);
        quantity = itemView.findViewById(R.id.inventoryStockLabel);
//        input = itemView.findViewById(R.id.inventoryInput);

    }
}
