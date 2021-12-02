package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    EditText firstNameInput;
    EditText lastNameInput;
    EditText newUserNameInput;
    EditText newPassWordInput;

    StoreDatabase storeDatabase;


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


        if (!firstNameInput.getText().toString().isEmpty() && !lastNameInput.getText().toString().isEmpty()
        && !newUserNameInput.getText().toString().isEmpty() && !newPassWordInput.getText().toString().isEmpty()) {

            User newUser = new User(firstNameInput.getText().toString(), lastNameInput.getText().toString(),
                    newUserNameInput.getText().toString(), newPassWordInput.getText().toString(), "No", 0, "Regular");

            storeDatabase.addUser(newUser);

            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("newUser", newUser);
            startActivity(i);

        }
        else {

            Toast.makeText(this, "Please enter all required fields.", Toast.LENGTH_SHORT).show();

        }



    }

    @Override
    protected void onDestroy() {
        storeDatabase.shutDown();
        super.onDestroy();
    }





}