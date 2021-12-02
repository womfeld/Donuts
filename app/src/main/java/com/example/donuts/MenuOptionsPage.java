package com.example.donuts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MenuOptionsPage extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;


    private String[] menuList;
    private int[] menuImagesList;
    private String type;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_options_page);

        setTitle("Menu");

        Intent intent = getIntent();

        //This section of code receives the intent with the menu (titles and images) and saves
        //the menu titles into menuList and the menu images into menuImages
        if (intent.hasExtra("menu") && intent.hasExtra("images") && intent.hasExtra("type") && intent.hasExtra("userInfo")) {

            menuList = intent.getStringArrayExtra("menu");
            menuImagesList = intent.getIntArrayExtra("images");
            type = intent.getStringExtra("type");
            user = (User) intent.getSerializableExtra("userInfo");

        }

        recyclerView = findViewById(R.id.menuRecycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MenuAdapter(this, menuList, menuImagesList);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        // Now check for menu items
        if (item.getItemId() == R.id.goToHomePage) {
            Intent home = new Intent(this, MainActivity.class);
            startActivity(home);
            return true;
        } else if (item.getItemId() == R.id.goToManagerPage) {
            Intent intent = new Intent(this, UserPortal.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.goToShoppingCart) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }



    public void onClick(View view) {

        int pos = recyclerView.getChildLayoutPosition(view);

        String itemTitle = menuList[pos];
        int image = menuImagesList[pos];

        Intent i = new Intent(this, OrderPage.class);
        i.putExtra("name", itemTitle);
        i.putExtra("image", image);
        i.putExtra("type", type);
        startActivity(i);



    }


    public boolean onLongClick(View view) {

        return false;
    }

}