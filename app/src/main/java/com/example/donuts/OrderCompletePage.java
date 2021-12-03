package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class OrderCompletePage extends AppCompatActivity {


    private SharedPreferences myPrefs;
    User user;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete_page);
        setTitle("Order Complete");

        button = findViewById(R.id.button);

        Intent intent = getIntent();


        if (intent.hasExtra("userInfo")) {
            user = (User) intent.getSerializableExtra("userInfo");
            //saveUserToSharedPreferences();
        }

        if (user.getRole().equals("Regular")) {
            button.setText("Logout");
        }

        myPrefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);

    }




    @Override
    public void onBackPressed() {

    }




    public void onClick(View v) {
        if (user.getRole().equals("Regular")){

            showLogoutWarning();
        }
        else {
            Intent i = new Intent(this, ManagerPortal.class);
            i.putExtra("userInfo", user);
            startActivity(i);
        }

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




}