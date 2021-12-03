package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StartOrderForUserPage extends AppCompatActivity {

    public TextView title;
    public EditText userInput;

    StoreDatabase storeDatabase;
    User u;

    public SharedPreferences myPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_order_for_user_page);

        storeDatabase = new StoreDatabase(this);

        Intent intent = getIntent();
        if (intent.hasExtra("userInfo")) {
            u = (User) intent.getSerializableExtra("userInfo");

        }

        title = findViewById(R.id.reviewDialogTitle);
        userInput = findViewById(R.id.EditText1);

        myPrefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
    }

    public void startOrderForUserClicked(View v) {

        String userName = userInput.getText().toString();
        User userToOrderFor;

        try {
            userToOrderFor = storeDatabase.loadUserForManager(userName);

            SharedPreferences.Editor myPrefEditor = myPrefs.edit();
            myPrefEditor.putString("userToOrderFor", userToOrderFor.getUserName());
            myPrefEditor.putInt("initialRewardPoints", userToOrderFor.getRewardPoints());
            myPrefEditor.putInt("remainingRewardPoints", userToOrderFor.getRewardPoints());
            myPrefEditor.commit();

            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("userInfo", u);
            startActivity(i);





        }
        catch (Exception e) {
            Toast.makeText(this, "This user is not in the database.  Please enter a valid user.", Toast.LENGTH_LONG).show();

        }

    }


    @Override
    protected void onDestroy() {
        storeDatabase.shutDown();
        super.onDestroy();
    }

}