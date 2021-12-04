package com.example.donuts;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {

    public EditText userNameInput;
    public EditText passwordInput;
    public TextView loginUnsuccessfulMessage;
    public Button loginButton;
    public Button signUpButton;

    StoreDatabase storeDatabase;

    public SharedPreferences myPrefs;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //NEEDclearCart();

        //this.deleteDatabase("DonutStoreDB");


        storeDatabase = new StoreDatabase(this);



        //Adds the manager
//         storeDatabase.addUser(new User("Manager", "Will", "manager", "asdf3", "No", 0, "Manager"));
//
//
//
//        String[] tempArray = {"Chocolate Donut", "Powdered Donut", "Glazed Donut", "Jelly Donut", "Apple Fritter",
//                "Coffee", "Iced Coffee", "Latte", "Macchiato", "Plain Bagel", "Sesame Bagel", "Cinnamon Bagel"};
//        int[] qtys = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
//
//        double[] prices = {1.5, 1.5, 1.5, 1.5, 2, 2, 2, 2, 1, 1, 1, 1};
//
//        for (int i=0; i < 12; i++) {
//            storeDatabase.addItemToInventory(tempArray[i], qtys[i], prices[i]);
//        }




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
                User user = storeDatabase.loadUser(userNameEntered, passwordEntered);

                configureUserSharedPreferences(user);

                if (user.getRole().equals("Regular")) {
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("registeredUser", user);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(this, ManagerPortal.class);
                    i.putExtra("registeredUser", user);
                    startActivity(i);
                }

            }
            catch (Exception e) {

                loginUnsuccessfulMessage.setVisibility(View.VISIBLE);

            }
        }
    }

    private void configureUserSharedPreferences(User user) {
        myPrefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        //Initialize editor for myPref
        SharedPreferences.Editor myPrefEditor = myPrefs.edit();
        myPrefEditor.clear();

        myPrefEditor.putInt("remainingRewardPoints", user.getRewardPoints());
        myPrefEditor.putString("currentUserName", user.getUserName());
        myPrefEditor.putString("currentPassword", user.getPassword());
        myPrefEditor.putString("userRole", user.getRole());
        myPrefEditor.commit();
    }


    public void signUpButtonClicked(View v) {

        Intent i = new Intent(this, CreateAccount.class);
        //i.putExtra("database", (Parcelable) storeDatabase);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        storeDatabase.shutDown();
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void clearCart() {


        ArrayList<Order> userOrders = new ArrayList<>();


        try {
            FileOutputStream fos = getApplicationContext().
                    openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            PrintWriter printWriter = new PrintWriter(fos);
            printWriter.print(userOrders);
            printWriter.close();
            fos.close();

        } catch (Exception e) {
            e.getStackTrace();
        }

    }




}