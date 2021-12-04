package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class CreateAccount extends AppCompatActivity {

    EditText firstNameInput;
    EditText lastNameInput;
    EditText newUserNameInput;
    EditText newPassWordInput;

    StoreDatabase storeDatabase;

    SharedPreferences myPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        setTitle("Sign Up");

        storeDatabase = new StoreDatabase(this);







        firstNameInput = findViewById(R.id.firstNameTextField);
        lastNameInput = findViewById(R.id.lastNameTextField);
        newUserNameInput = findViewById(R.id.userNameEditText2);
        newPassWordInput = findViewById(R.id.editTextTextPassword);





    }



    public void createAccountClicked(View v) {


        if (!newUserNameInput.getText().toString().isEmpty() && !newPassWordInput.getText().toString().isEmpty()
        && !firstNameInput.getText().toString().isEmpty() && !lastNameInput.getText().toString().isEmpty()) {

            User newUser = new User(newUserNameInput.getText().toString(), newPassWordInput.getText().toString(),
                    firstNameInput.getText().toString(), lastNameInput.getText().toString(), "No", 0, "Regular");

            try {
                User temp = storeDatabase.loadUserForManager(newUserNameInput.getText().toString());
                Toast.makeText(this, "Error: User is already registered in the system.", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e) {
                configureUserSharedPreferences(newUser);

                storeDatabase.addUser(newUser);

                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("newUser", newUser);
                startActivity(i);
            }


//            configureUserSharedPreferences(newUser);
//
//            storeDatabase.addUser(newUser);
//
//            Intent i = new Intent(this, MainActivity.class);
//            i.putExtra("newUser", newUser);
//            startActivity(i);

        }
        else {

            Toast.makeText(this, "Please enter all required fields.", Toast.LENGTH_SHORT).show();

        }


    }


    private void configureUserSharedPreferences(User user) {
        myPrefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        //Initialize editor for myPref
        SharedPreferences.Editor myPrefEditor = myPrefs.edit();
        //Initialize gson object so it's possible to save objects in sharedPreferences object
        myPrefEditor.clear();

        myPrefEditor.putInt("remainingRewardPoints", user.getRewardPoints());
        myPrefEditor.putString("currentUserName", user.getUserName());
        myPrefEditor.putString("currentPassword", user.getPassword());
        myPrefEditor.putString("userRole", user.getRole());
        myPrefEditor.commit();
    }

    @Override
    protected void onDestroy() {
        storeDatabase.shutDown();
        super.onDestroy();
    }





}