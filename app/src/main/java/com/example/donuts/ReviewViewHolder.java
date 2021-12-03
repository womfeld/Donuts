package com.example.donuts;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView description;
    ImageView star1;
    ImageView star2;
    ImageView star3;
    ImageView star4;
    ImageView star5;

    ArrayList<ImageView> stars = new ArrayList<>();


    public ReviewViewHolder(@NonNull View v) {
        super(v);
        name = v.findViewById(R.id.reviewTitle);
        description = v.findViewById(R.id.reviewLabel);

        star1 = v.findViewById(R.id.imageView);
        stars.add(star1);
        star2 = v.findViewById(R.id.imageView2);
        stars.add(star2);
        star3 = v.findViewById(R.id.imageView3);
        stars.add(star3);
        star4 = v.findViewById(R.id.imageView4);
        stars.add(star4);
        star5 = v.findViewById(R.id.imageView5);
        stars.add(star5);

    }






}
