package com.example.calcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Util.Utils;
import data.CustomListViewadapter;
import data.DatabaseHandler;
import model.Food;


public class DisplayActivity extends AppCompatActivity {

    private DatabaseHandler dba;
    private ArrayList<Food> dbFoods = new ArrayList<>();
    private CustomListViewadapter foodAdapter;
    private ListView listView;

    private Food myFood;
    private TextView totalCals, totalFoods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_foods);

        listView = (ListView) findViewById(R.id.list);

        refreshData();
    }

    private void refreshData() {
        dbFoods.clear();

        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<Food> foodsFromDB = dba.getFoods();


        for (int i = 0; i < foodsFromDB.size(); i++){

            String name = foodsFromDB.get(i).getTitle();
            String dateText = foodsFromDB.get(i).getRecordDate();
            String cals = foodsFromDB.get(i).getDesc();
            int foodId = foodsFromDB.get(i).getId();

            Log.v("FOOD IDS: ", String.valueOf(foodId));


            myFood = new Food();
            myFood.setTitle(name);
            myFood.setDesc(cals);
            myFood.setId(foodId);

            dbFoods.add(myFood);



        }
        dba.close();

        //setup adapter
        foodAdapter = new CustomListViewadapter(DisplayActivity.this, R.layout.list_row, dbFoods);
        listView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();

    }
}

