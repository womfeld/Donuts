package com.example.donuts;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MenuViewHolder extends RecyclerView.ViewHolder {

    TextView optionTitle;
    ImageView rowImage;

    public MenuViewHolder(View itemView) {
        super(itemView);

        optionTitle = itemView.findViewById(R.id.optionTitle);
        rowImage = itemView.findViewById(R.id.optionImage);

    }
}
