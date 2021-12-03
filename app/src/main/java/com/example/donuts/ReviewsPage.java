package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class ReviewsPage extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter rAdapter;
    RecyclerView.LayoutManager layoutManager;

    StoreDatabase storeDatabase;

    public User user;

    public ArrayList<Review> reviewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_page);


        storeDatabase = new StoreDatabase(this);

        Intent intent = getIntent();
        if (intent.hasExtra("userInfo")) {
            user = (User) intent.getSerializableExtra("userInfo");
        }

        recyclerView = findViewById(R.id.inventoryRecycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        reviewList = storeDatabase.loadReviews();

        rAdapter = new ReviewAdapter(reviewList, this);
        recyclerView.setAdapter(rAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ManagerPortal.class);
        i.putExtra("userInfo", user);
        startActivity(i);
    }



    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    protected void onDestroy() {
        storeDatabase.shutDown();
        super.onDestroy();
    }
}