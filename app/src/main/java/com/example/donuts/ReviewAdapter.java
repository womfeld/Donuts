package com.example.donuts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {


    //Make Reviews about what an adapter does


    private ArrayList<Review> reviewList;
    private final ReviewsPage reviewsPage;


    public ReviewAdapter(ArrayList<Review> reviewList, ReviewsPage rp) {
        this.reviewList = reviewList;
        reviewsPage = rp;

    }

    //Inflates and creates cell
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);

        itemView.setOnClickListener(reviewsPage);
        itemView.setOnLongClickListener(reviewsPage);

        return new ReviewViewHolder(itemView);
    }


    //Binds variable values to cell

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        Review r = reviewList.get(position);
        holder.name.setText(r.getName());

        //Limits length of review displayed
        String desc = r.getDescription();
        if(desc.length() > 150){
            String shortenedDesc = desc.substring(0,150) + "...";
            holder.description.setText(shortenedDesc);
        }
        else {
            holder.description.setText(r.getDescription());
        }

        int numberOfStars = r.getStars();
        int counter = 0;
        for (ImageView star : holder.stars) {
            if (counter < numberOfStars) {
                star.setVisibility(View.VISIBLE);
            }
            counter++;

        }




    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
