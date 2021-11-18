package com.example.donuts;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    private Context context;
    private String[] menuList;
    private int[] images;

    //Maybe change this later to look more like stock assistant

    //Try this after code works
    private MenuOptionsPage menuOptionsPage;
    public MenuAdapter(MenuOptionsPage menuOptionsPage, String[] menuOptionsList, int[] menuImages){
    //public MenuAdapter(Context context, String[] menuOptionsList, int[] menuImages){

        //this.context = context;
        this.menuOptionsPage = menuOptionsPage;
        this.menuList = menuOptionsList;
        this.images = menuImages;

    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.menu_option, parent, false);
//        MenuViewHolder viewHolder = new MenuViewHolder(view);
//        return viewHolder;

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_option, parent, false);

        itemView.setOnClickListener((View.OnClickListener) this.menuOptionsPage);
        itemView.setOnLongClickListener((View.OnLongClickListener) this.menuOptionsPage);

        return new MenuViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {

        holder.optionTitle.setText(menuList[position]);
        holder.rowImage.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return menuList.length;
    }
}
