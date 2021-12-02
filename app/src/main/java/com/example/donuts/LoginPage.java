package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class LoginPage extends AppCompatActivity {

    public EditText userNameInput;
    public EditText passwordInput;
    public TextView loginUnsuccessfulMessage;
    public Button loginButton;
    public Button signUpButton;

    StoreDatabase storeDatabase;

    public SharedPreferences myPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        this.deleteDatabase("MontroseDonutsDeliDB");


        storeDatabase = new StoreDatabase(this);



        //Adds the manager
        try {
            storeDatabase.addUser(new User("Manager", "Will", "manager", "asdf3", "No", 0, "Manager"));
        }
        catch(Exception e) {

        }




        userNameInput = findViewById(R.id.userNameTextField);
        passwordInput = findViewById(R.id.passwordTextField);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        loginUnsuccessfulMessage = findViewById(R.id.loginFailedLabel);


        loginUnsuccessfulMessage.setVisibility(View.INVISIBLE);


    }


    public void loginButtonClicked(View v) {

        String userNameEntered = userNameInput.getText().toString();
        String passwordEntered = passwordInput.getText().toString();

        if (userNameEntered.isEmpty() || passwordEntered.isEmpty()) {
            Toast.makeText(this, "Please enter all required fields.", Toast.LENGTH_SHORT).show();
        }
        else {

            try {
                User user = storeDatabase.loadUser(userNameEntered, passwordEntered, this);

                myPrefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
                //Initialize editor for myPref
                SharedPreferences.Editor myPrefEditor = myPrefs.edit();
                //Initialize gson object so it's possible to save objects in sharedPreferences object
                Gson gson = new Gson();

                myPrefEditor.putInt("remainingRewardPoints", user.getRewardPoints());
                myPrefEditor.commit();

                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("registeredUser", user);
                startActivity(i);

            }
            catch (Exception e) {

                loginUnsuccessfulMessage.setVisibility(View.VISIBLE);

            }
        }
    }




    public void signUpButtonClicked(View v) {

        Intent i = new Intent(this, CreateAccount.class);
        //i.putExtra("database", (Parcelable) storeDatabase);
        startActivity(i);

    }

    @Override
    protected void onDestroy() {
        storeDatabase.shutDown();
        super.onDestroy();
    }




}