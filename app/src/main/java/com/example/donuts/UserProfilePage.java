package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Locale;

public class UserProfilePage extends AppCompatActivity {

    public TextView fullNameLabel;
    public TextView rewardPointsLabel;
    public Button submitReview;


    private  User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_page);

        Intent intent = getIntent();
        if (intent.hasExtra("userInfo")) {

            user = (User) intent.getSerializableExtra("userInfo");

        }

        fullNameLabel = findViewById(R.id.fullNameLabel);
        rewardPointsLabel = findViewById(R.id.pointsLabelUserProfile);
        submitReview = findViewById(R.id.submitReviewButton);

        String temp = user.getFirst() + " " + user.getLast();
        String fullNameParsed = temp.replaceAll("\\s", "");

        fullNameLabel.setText(user.getFirst() + " " + user.getLast());
        rewardPointsLabel.setText(String.valueOf(user.getRewardPoints()));


    }


    public void submitReviewClicked(View v) {

        Intent intent = new Intent(this, ReviewPage.class);
        intent.putExtra("userInfo", user);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("userInfo", user);
        startActivity(intent);
    }
}