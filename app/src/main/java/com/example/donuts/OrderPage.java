package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

public class OrderPage extends AppCompatActivity {


    private Order orderItem;

    private TextView title;
    public TextView priceLabel;
    public ElegantNumberButton quantityAdjuster;
    private ImageView image;
    public Button addToCartButton;


    ArrayList<CheckBox>checkBoxes;
    public CheckBox checkBoxOne;
    public CheckBox checkBoxTwo;
    public CheckBox checkBoxThree;
    public CheckBox checkBoxFour;
    public CheckBox checkBoxFive;





    //All the values we will add to the Order object orderItem
    private String itemName;
    public int itemQuantity;
    public double itemPrice;
    private ArrayList<String> customItems;
    public int itemImage;

    //If an item is being edited, we need to save the position of that item
    private int editPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        //Set the checkboxes so we can configure them properly, and then put them into an array
        checkBoxes = new ArrayList<>();
        checkBoxOne = findViewById(R.id.checkBox);
        checkBoxes.add(checkBoxOne);
        checkBoxTwo = findViewById(R.id.checkBox2);
        checkBoxes.add(checkBoxTwo);
        checkBoxThree = findViewById(R.id.checkBox3);
        checkBoxes.add(checkBoxThree);
        checkBoxFour = findViewById(R.id.checkBox4);
        checkBoxes.add(checkBoxFour);
        checkBoxFive = findViewById(R.id.checkBox5);
        checkBoxes.add(checkBoxFive);

        //LEAVE THIS FOR NOW
        checkBoxFive.setVisibility(View.INVISIBLE);

        //Intialize the addToCart Button
        addToCartButton = findViewById(R.id.addToCart);
        addToCartButton.setEnabled(false);

        //Initialize the price label
        priceLabel = findViewById(R.id.price);


        //Initialize the quantity input text field
        quantityAdjuster = findViewById(R.id.counterButton);


        //Get intent - the intent will either be a user choosing from the menu
        //or a user editing their order from the cart

        Intent intent = getIntent();

        //This will be the intent when the user chooses from the menu
        if (intent.hasExtra("name") && intent.hasExtra("image")) {

            itemName = intent.getStringExtra("name");

            //Since we hardcoded images, default value will never be zero
            itemImage = intent.getIntExtra("image", 0);

            addToCartButton.setText("ADD TO CART");

        }
        else if (intent.hasExtra("orderToEdit") && intent.hasExtra("position")) {

            Order o = (Order) intent.getSerializableExtra("orderToEdit");
            editPosition = intent.getIntExtra("position", 0);

            itemName = o.getName();
            itemImage = o.getImage();

            HashSet<String>h = new HashSet<>();
            h.addAll(o.getCustomItems());



            for (CheckBox c : checkBoxes) {
                String s = c.getText().toString();
                if (h.contains(s)) {
                    c.setChecked(true);
                }
            }

            quantityAdjuster.setNumber(String.valueOf(o.getQuantity()));
            addToCartButton.setText("EDIT ITEM");
            priceLabel.setText("$" + String.format(Locale.US, "%.2f", o.getItemPrice()));

        }
        //Failure for some reason
        else {

        }



        //Initialize customItems
        customItems = new ArrayList<>();



        //Sets Action Bar title
        setTitle(itemName);

        //Sets the menu item name in the order page
        title = findViewById(R.id.orderPageTitle);
        title.setText(itemName);


        //Sets the menu item image in the order page
        image = findViewById(R.id.orderPageImage);
        image.setImageResource(itemImage);





        //Adjusts the price label accordingly as the quantity label is adjusted
        quantityAdjuster.setOnClickListener((ElegantNumberButton.OnClickListener) v -> {
            String input = quantityAdjuster.getNumber();
            itemQuantity = Integer.parseInt(input);

            //Set the price of the menu item
            //I'm just saying the price of a donit is $1.50 for now (will change later)
            itemPrice = 1.5 * itemQuantity;
            //String formattedString = "$" + String.format(Locale.US, "%.2f", itemPrice);
            priceLabel.setText("$" + String.format(Locale.US, "%.2f", itemPrice));

            addToCartButton.setEnabled(true);
        });




    }

    public void calculatePriceClicked(View v) {

        String input = quantityAdjuster.getNumber();
        itemQuantity = Integer.parseInt(input);

        //Set the price of the menu item
        //I'm just saying the price of a donit is $1.50 for now (will change later)
        itemPrice = 1.5 * itemQuantity;
        //String formattedString = "$" + String.format(Locale.US, "%.2f", itemPrice);
        priceLabel.setText("$" + String.format(Locale.US, "%.2f", itemPrice));

        addToCartButton.setEnabled(true);

    }


    public void addToCartClicked(View v) {

        Intent returnToMain;
        Intent returnToCart;

        //In this code, we will first clear the customItems array if we received the order (in this case, if we edit
        //an order from the shopping cart page) from the shopping cart page
        customItems.clear();

        for (CheckBox c : checkBoxes) {
            if (c.isChecked()==true) {
                customItems.add(c.getText().toString());
            }
        }

        if (addToCartButton.getText().toString().equals("ADD TO CART")) {

            returnToMain = new Intent(this, MainActivity.class);
            orderItem = new Order(itemName, customItems, itemQuantity, itemPrice, itemImage);
            returnToMain.putExtra("order", orderItem);
            startActivity(returnToMain);

        }

        else if (addToCartButton.getText().toString().equals("EDIT ITEM")){

            returnToCart = new Intent(this, ShoppingCartPage.class);
            orderItem = new Order(itemName, customItems, itemQuantity, itemPrice, itemImage);
            returnToCart.putExtra("editedOrder", orderItem);
            returnToCart.putExtra("position", editPosition);
            startActivity(returnToCart);


        }
        //Error for some reason
        else {

        }




    }
}