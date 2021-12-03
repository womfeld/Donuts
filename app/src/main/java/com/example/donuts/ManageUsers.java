package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ManageUsers extends AppCompatActivity {

    public EditText firstInput;
    public EditText newUserNameInput;
    public EditText lastInput;
    public EditText passwordInput;


    public TextView customerLabel;
    public Switch s;
    public TextView employeeLabel;

    public TextView removeUserLabel;
    public EditText removeUserInput;
    public Button removeUserButton;


    public String createdUserRole;




    public StoreDatabase storeDatabase;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        storeDatabase = new StoreDatabase(this);

        Intent intent = getIntent();
        if (intent.hasExtra("userInfo")) {
            user = (User) intent.getSerializableExtra("userInfo");
        }

        firstInput = findViewById(R.id.firstNInput);
        newUserNameInput = findViewById(R.id.newUInput);
        lastInput = findViewById(R.id.laInput);
        passwordInput = findViewById(R.id.pwdInput);


        customerLabel = findViewById(R.id.customerLabel);
        customerLabel.setVisibility(View.INVISIBLE);
        s = findViewById(R.id.switch7);
        s.setEnabled(false);
        s.setVisibility(View.INVISIBLE);
        employeeLabel = findViewById(R.id.employeeLabel);
        employeeLabel.setVisibility(View.INVISIBLE);

        removeUserLabel = findViewById(R.id.removeUserLabel);
        removeUserLabel.setVisibility(View.INVISIBLE);
        removeUserInput = findViewById(R.id.existingUserNInput);
        removeUserInput.setVisibility(View.INVISIBLE);
        removeUserButton = findViewById(R.id.removeUserButton);
        removeUserButton.setEnabled(false);
        removeUserButton.setVisibility(View.INVISIBLE);

        //Default value is customer
        createdUserRole = "Regular";


        //Only manager has access to choose user role and remove user
        if (user.getRole().equals("Manager")) {

            customerLabel.setVisibility(View.VISIBLE);
            s.setEnabled(true);
            s.setVisibility(View.VISIBLE);
            employeeLabel.setVisibility(View.VISIBLE);

            removeUserLabel.setVisibility(View.VISIBLE);
            removeUserInput.setVisibility(View.VISIBLE);
            removeUserButton.setEnabled(true);
            removeUserButton.setVisibility(View.VISIBLE);


        }

    }


    public void roleSwitchClicked(View v) {

        if (((Switch) v).isChecked()) {

            createdUserRole = "Employee";

        }
        else {

            createdUserRole = "Regular";


        }

    }

    public void createNewSystemUserClicked(View v) {

        String firstName = firstInput.getText().toString();
        String newUserName = newUserNameInput.getText().toString();
        String lastName = lastInput.getText().toString();
        String password = passwordInput.getText().toString();

        storeDatabase.addUser(new User(newUserName, password, firstName, lastName, "No", 0, createdUserRole));

        firstInput.setText("");
        newUserNameInput.setText("");
        lastInput.setText("");
        passwordInput.setText("");

        Toast.makeText(this, "Added User to System!", Toast.LENGTH_SHORT).show();

    }


    public void removeUserClicked(View v) {

        String userToRemove = removeUserInput.getText().toString();
        storeDatabase.deleteUser(userToRemove);
        removeUserInput.setText("");

        Toast.makeText(this, "Removed User from System!", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ManagerPortal.class);
        i.putExtra("userInfo", user);
        startActivity(i);
    }



    @Override
    protected void onDestroy() {
        storeDatabase.shutDown();
        super.onDestroy();
    }
}