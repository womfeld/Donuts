package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class ReviewPage extends AppCompatActivity {


    public RadioButton radio1;
    public RadioButton radio2;
    public RadioButton radio3;
    public RadioButton radio4;
    public RadioButton radio5;

    public RadioGroup radioGroup;

    EditText input;

    public StoreDatabase storeDatabase;

    private User user;

    private int star;
    private String review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        radioGroup = findViewById(R.id.radioGroup);
        radio1 = findViewById(R.id.radioButton);
        radio2 = findViewById(R.id.radioButton2);
        radio3 = findViewById(R.id.radioButton3);
        radio4 = findViewById(R.id.radioButton4);

        input = findViewById(R.id.EditText1);

        storeDatabase = new StoreDatabase(this);

        Intent intent = getIntent();
        if (intent.hasExtra("userInfo")) {
            user = (User) intent.getSerializableExtra("userInfo");
        }



    }

    public void reviewSubmitted(View v) {

        String firstName = user.getFirst();

        int x = radioGroup.getCheckedRadioButtonId();

        if (input.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }
        else if (radioGroup.getCheckedRadioButtonId()==-1) {

            Toast.makeText(this, "Please give a valid rating.", Toast.LENGTH_SHORT).show();
        }
        else {

            if (radio1.isChecked()) {
                star = 1;
            }
            else if (radio2.isChecked()) {
                star = 2;
            }
            else if (radio3.isChecked()) {
                star = 3;
            }
            else if (radio4.isChecked()) {
                star = 4;
            }
            else {
                star = 5;
            }

            review = input.getText().toString();

            storeDatabase.addReview(firstName, star, review);

            Intent intent = new Intent(this, UserProfilePage.class);
            intent.putExtra("userInfo", user);
            startActivity(intent);

        }



    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, UserProfilePage.class);
        i.putExtra("userInfo", user);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        storeDatabase.shutDown();
        super.onDestroy();
    }


}