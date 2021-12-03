package com.example.donuts;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ManagerPortal extends AppCompatActivity {


    public User u;

    public Button viewReviewsButton;
    public Button getReportButton;
    StoreDatabase storeDatabase;

    public SharedPreferences myPrefs;

    public HashMap<String, Double> mp = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_portal);

        storeDatabase = new StoreDatabase(this);

        viewReviewsButton = findViewById(R.id.viewReviewsButton);
        getReportButton = findViewById(R.id.viewReportsButton);

        //Just keeping this in for now so code doesn't break
        //Once you replace intents with shared preferences for all instances of it,
        //make sure to delete the user intents
        Intent intent = getIntent();
        if (intent.hasExtra("userInfo")) {
            u = (User) intent.getSerializableExtra("userInfo");

        }

        else if (intent.hasExtra("registeredUser")) {
            u = (User) intent.getSerializableExtra("registeredUser");

        }

        else {

        }




        if (u.getRole().equals("Manager")) {

            setTitle("Manager Page");
        }
        else {

            viewReviewsButton.setVisibility(View.INVISIBLE);
            viewReviewsButton.setEnabled(false);
            getReportButton.setVisibility(View.INVISIBLE);
            getReportButton.setEnabled(false);

            setTitle("Employee Page");
        }

        //Initialize shared preferences object myPref
        myPrefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);




    }

    public void logOrderClicked(View v) {
        Intent i = new Intent(this, StartOrderForUserPage.class);
        i.putExtra("userInfo", u);
        startActivity(i);
    }

    public void editInventoryClicked(View v) {
        Intent i = new Intent(this, InventoryUpdatePage.class);
        i.putExtra("userInfo", u);
        startActivity(i);
    }


    public void viewReviewsClicked(View v) {
        Intent i = new Intent(this, ReviewsPage.class);
        i.putExtra("userInfo", u);
        startActivity(i);
    }

    public void manageUsersClicked(View v) {
        Intent i = new Intent(this, ManageUsers.class);
        i.putExtra("userInfo", u);
        startActivity(i);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getReportClicked(View v) {
        mp.putAll(storeDatabase.getOrders());

        String name = "";

        HashMap<String, Integer> tempMap = new HashMap<>();

        double total = 0;

        for (String k: mp.keySet()) {
            total = total + mp.get(k);
        }

        Intent i = new Intent(this, ReportPage.class);
        i.putExtra("total", total);
        i.putExtra("userInfo", u);
        startActivity(i);

    }

    public void logOutClicked(View v) {

        showLogoutWarning();

    }


    private void logoutOfSystem() {
        SharedPreferences.Editor myPrefEditor = myPrefs.edit();
        myPrefEditor.clear();
        myPrefEditor.commit();

        Intent i = new Intent(this, LoginPage.class);
        startActivity(i);
    }


    public void showLogoutWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to log out of the system?");
        builder.setPositiveButton("YES", (dialogInterface, i) -> logoutOfSystem());
        builder.setNegativeButton("NO", (dialogInterface, i) -> { });
        AlertDialog dialog = builder.create();
        dialog.show();

    }



    @Override
    protected void onDestroy() {
        storeDatabase.shutDown();
        super.onDestroy();
    }


}