package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class ReportPage extends AppCompatActivity {

    public TextView userName;
    public TextView totalRevenue;
    Button button;

    private User user;
    private double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);

        totalRevenue = findViewById(R.id.textView12);

        Intent intent = getIntent();

        if (intent.hasExtra("userInfo") && intent.hasExtra("total")) {
            user = (User) intent.getSerializableExtra("userInfo");
            total = intent.getDoubleExtra("total", 0);
        }

        totalRevenue.setText("$"+String.format(Locale.US, "%.2f",total));

    }


    public void onClick(View v) {
        Intent i = new Intent(this, ManagerPortal.class);
        i.putExtra("userInfo", user);
        startActivity(i);
    }
}