package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserPortal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_portal);

        setTitle("Manager Page");
    }

    public void goToInventoryUpdatePage(View v) {
        Intent i = new Intent(this, InventoryUpdatePage.class);
        startActivity(i);
    }


}