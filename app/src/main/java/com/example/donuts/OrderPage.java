package com.example.donuts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

public class OrderPage extends AppCompatActivity {


    private Order orderItem;

    private TextView title;
    public TextView priceLabel;
    public TextView rewardsLabel;
    public ElegantNumberButton quantityAdjuster;
    private ImageView image;
    public Button addToCartButton;
    public Button redeemButton;


    ArrayList<CheckBox>checkBoxes;
    public CheckBox checkBoxOne;
    public CheckBox checkBoxTwo;
    public CheckBox checkBoxThree;
    public CheckBox checkBoxFour;



    //Shared preferences object
    private SharedPreferences myPrefs;


    public User user;



    //Item type
    private String type;

    //Boolean to determine whether user has used reward points for this specific order
    public boolean isRedeemed;
    public int redeemedQuantity;

    //All the values we will add to the Order object orderItem
    private String itemName;
    public int itemQuantity;
    public double itemPrice;
    private ArrayList<String> customItems;
    public int itemImage;

    //If an item is being edited, we need to save the position of that item
    private int editPosition;


    ///////CHANGE LATER
    public int rewardPoints;


    public StoreDatabase storeDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        //Initialize shared preferences object myPref
        myPrefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);

        redeemButton = findViewById(R.id.redeemButton);
        rewardsLabel = findViewById(R.id.rewardPointsLabel);

        storeDatabase = new StoreDatabase(this);



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


        //Intialize the addToCart Button
        addToCartButton = findViewById(R.id.addToCart);
        addToCartButton.setEnabled(false);

        //Initialize the price label
        priceLabel = findViewById(R.id.price);


        //Initialize the quantity input text field
        quantityAdjuster = findViewById(R.id.counterButton);


        Intent intent = getIntent();

        if (intent.hasExtra("userInfo")) {
            user = (User) intent.getSerializableExtra("userInfo");

        }


        rewardPoints = myPrefs.getInt("remainingRewardPoints", 0);


        rewardsLabel.setText(String.valueOf(rewardPoints));

        //This will be the intent when the user chooses from the menu
        if (intent.hasExtra("name") && intent.hasExtra("image") && intent.hasExtra("type")) {

            itemName = intent.getStringExtra("name");

            //Since we hardcoded images, default value will never be zero
            itemImage = intent.getIntExtra("image", 0);

            type = intent.getStringExtra("type");

            setCheckBoxNames(type);

            addToCartButton.setText("ADD TO CART");

        }
        //CHECK IF REDEEMED
        else if (intent.hasExtra("orderToEdit") && intent.hasExtra("position")) {

            Order o = (Order) intent.getSerializableExtra("orderToEdit");
            editPosition = intent.getIntExtra("position", 0);

            itemName = o.getName();
            itemImage = o.getImage();
            type = o.getType();
            isRedeemed = o.getIsReedemed();

            setCheckBoxNames(type);

            HashSet<String>h = new HashSet<>();
            h.addAll(o.getCustomItems());


            //Will see what custom items were selected on order user wishes to edit,
            //then will proceed to recheck those items
            //Fall back on
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


        //Change quantityAdjuster to increment by 12s.  Do this inside the onclick listener.
        //Pass the text on the quantity adjuster to a method that converts a string to an int and
        //increments that int by 12.  Then, set text of quantityAdjuster to what the method returns




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



        //Check to see if user is eligible for reward
        if (getNumberOfMaximumFreeItems(rewardPoints) >= 1) {
            redeemButton.setEnabled(true);
        }



        //Adjusts the price label accordingly as the quantity label is adjusted
        quantityAdjuster.setOnClickListener((ElegantNumberButton.OnClickListener) v -> {

            String input = quantityAdjuster.getNumber();

            if (!isRedeemed) {
                itemPrice = 0;
                itemQuantity = Integer.parseInt(input);

                //Set the price of the menu item
                //I'm just saying the price of a donit is $1.50 for now (will change later)

                double pricePerItem;


                switch (type) {
                    case "Donut":
                        pricePerItem = 1.5;
                        break;
                    case "Beverage":
                        pricePerItem = 2.0;
                        break;
                    case "Bagel":
                        pricePerItem = 1.0;
                        break;
                    default:
                        //Don't need this
                        pricePerItem = 1;
                }


                itemPrice = pricePerItem * itemQuantity;
                //String formattedString = "$" + String.format(Locale.US, "%.2f", itemPrice);
                priceLabel.setText("$" + String.format(Locale.US, "%.2f", itemPrice));

                addToCartButton.setEnabled(true);
            }
            else {
                quantityAdjuster.setNumber(String.valueOf(itemQuantity));

            }
        });

    }



    private void setCheckBoxNames(String type) {
        //We need to set checkbox text and visibility based on the type of menu item being ordered
        switch (type) {
            case "Donut":
                break;
            case "Beverage":
                checkBoxOne.setText("Milk");
                checkBoxTwo.setText("Sugar");
                checkBoxThree.setText("Decaf");
                checkBoxFour.setText("Cream");
                break;
            case "Bagel":
                checkBoxOne.setText("Jelly");
                checkBoxTwo.setText("Cream Cheese");
                checkBoxThree.setText("Cut in Half");
                checkBoxFour.setText("Buttered");
            default:
                ;
        }
    }


    //Alert dialog asking how many items user wishes to redeem (text edit that gives note of the max they can redeem)

    //Find out how many free instances of single item they can POTENTIALLY get based on their points
    public int getNumberOfMaximumFreeItems(int points) {

        //5 bagel
        //8 donut
        //11 drink

        int remainingPoints = points;
        int rewardsAvailable = 0;


        int rewardPointCost = getItemRewardPointValue();

        while(remainingPoints >= rewardPointCost) {
            remainingPoints = remainingPoints - rewardPointCost;
            rewardsAvailable = rewardsAvailable + 1;
        }

        return rewardsAvailable;

    }

    private int getItemRewardPointValue() {
        int rewardPointCost;
        switch (type) {
            case "Donut":
                rewardPointCost = 8;
                break;
            case "Beverage":
                rewardPointCost = 11;
                break;

            case "Bagel":
                rewardPointCost = 5;
                break;
            default:
                //Won't happen
                rewardPointCost = 0;
        }
        return rewardPointCost;
    }


    public void redeemRewardClicked(View v) {

        //Will change the value of reward points to what's in shared preferences
        int rewardsAvailable = getNumberOfMaximumFreeItems(Integer.parseInt(rewardsLabel.getText().toString()));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String prompt = "Enter Number of " + itemName + "'s to Redeem:";

        final View customLayout = getLayoutInflater().inflate(R.layout.reward_alert_dialog, null);
        builder.setView(customLayout);

        TextView title = customLayout.findViewById(R.id.alertPromptLabel);
        EditText userInput = customLayout.findViewById(R.id.alertRewardsInput);
        TextView rewardsAvailableLabel = customLayout.findViewById(R.id.alertRewardsAvailableLabel);
        TextView warningLabel = customLayout.findViewById(R.id.alertWarningLabel);

        title.setText(prompt);
        //Change later
        rewardsAvailableLabel.setText("Available: " + rewardsAvailable);
        warningLabel.setVisibility(View.INVISIBLE);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                int rewardsRequested = Integer.parseInt(userInput.getText().toString());

                //Initialize editor for myPref
                SharedPreferences.Editor myPrefEditor = myPrefs.edit();
                //Initialize gson object so it's possible to save objects in sharedPreferences object
                Gson gson = new Gson();

                int pointsRemaining = myPrefs.getInt("remainingRewardPoints", 0);

                //This will be fixed when I create shared preferences - change reward points to remaining reward points
                int updatedPointsRemaining = pointsRemaining - (rewardsRequested * getItemRewardPointValue());

                myPrefEditor.putInt("remainingRewardPoints", updatedPointsRemaining);
                myPrefEditor.commit();

                itemQuantity = rewardsRequested;
                itemPrice = 0.0;
                if (isRedeemed) {
                    //If we already redeemed a reward and we still can redeem more, then we need to add how many items
                    //that we have already redeemed with the number of items we seek to redeem
                    itemQuantity = itemQuantity+ Integer.parseInt(quantityAdjuster.getNumber());
                    quantityAdjuster.setNumber(String.valueOf(itemQuantity));
                }
                else {
                    quantityAdjuster.setNumber(String.valueOf(itemQuantity));
                }
                isRedeemed = true;
                rewardsLabel.setText(String.valueOf(updatedPointsRemaining));
                priceLabel.setText("$" + String.format(Locale.US, "%.2f", itemPrice));
                if (getNumberOfMaximumFreeItems(updatedPointsRemaining) < 1) {
                    redeemButton.setEnabled(false);
                }
                addToCartButton.setEnabled(true);


            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        setTextChangeListener(rewardsAvailable, userInput, warningLabel, dialog);

    }


    private void setTextChangeListener(int rewardsAvailable, EditText userInput, TextView warningLabel, AlertDialog dialog) {
        //Only enable ok button when input is below rewards available
        userInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                warningLabel.setVisibility(View.INVISIBLE);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {


                if (s.length() < 1) {
                    warningLabel.setVisibility(View.INVISIBLE);
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                }
                else {

                    /////////ADD INVENTORY CHECK//////////////

                    int rewardsRequested = Integer.parseInt(userInput.getText().toString());

                    if (rewardsRequested > rewardsAvailable || rewardsRequested == 0) {
                        warningLabel.setVisibility(View.VISIBLE);
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    }
                    else {
                        warningLabel.setVisibility(View.INVISIBLE);
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                }
            }
        });
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

            //Shared preference code
            //Fall back on

            //Initialize editor for myPref
//            SharedPreferences.Editor myPrefEditor = myPrefs.edit();
//            //Initialize gson object so it's possible to save objects in sharedPreferences object
//            Gson gson = new Gson();
//            orderItem = new Order(itemName, customItems, itemQuantity, itemPrice, itemImage);
//
//            String jsonFormattedObject = gson.toJson(orderItem);
//            myPrefEditor.putString("lastOrder", jsonFormattedObject);
//            myPrefEditor.commit();
//
//            returnToMain = new Intent(this, MainActivity.class);
//            returnToMain.putExtra("order", "order");
//            startActivity(returnToMain);






            //Fall back on
            returnToMain = new Intent(this, MainActivity.class);
            orderItem = new Order(itemName, customItems, itemQuantity, itemPrice, itemImage, type);
            returnToMain.putExtra("order", orderItem);
            returnToMain.putExtra("userInfo", user);
            startActivity(returnToMain);

        }

        else if (addToCartButton.getText().toString().equals("EDIT ITEM")){

            //Will have to change this so that it edits
            returnToCart = new Intent(this, ShoppingCartPage.class);
            orderItem = new Order(itemName, customItems, itemQuantity, itemPrice, itemImage, type, isRedeemed);
            returnToCart.putExtra("editedOrder", orderItem);
            returnToCart.putExtra("position", editPosition);
            returnToCart.putExtra("userInfo", user);
            startActivity(returnToCart);


        }
        //Error for some reason
        else {

        }




    }
}