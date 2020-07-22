package com.example.calcounter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import Util.Utils;
import data.CustomListViewadapter;
import data.DatabaseHandler;
import model.Food;


public class DisplayActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private DatabaseHandler dba;
    private ArrayList<Food> dbFoods = new ArrayList<>();
    private CustomListViewadapter foodAdapter;
    private ListView listView;
    private Object mActionMode=null;

    private Food myFood;
    private TextView totalCals, totalFoods;
    private static int selectedItem=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_foods);

        listView =  findViewById(R.id.list);
        totalCals = findViewById(R.id.totalAmountTextView);
        totalFoods =  findViewById(R.id.totalItemsTextView);


        refreshData();



                /*if(mActionMode!=null){
                    return false;
                }
                selectedItem=-1;
                selectedItem=pos;
                Food food=dbFoods.get(selectedItem);
                mActionMode=DisplayActivity.this.startActionMode(mActionModeCallback);
                arg1.setSelected(true);*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==10){
            if(resultCode==RESULT_OK){
                refreshData();
            }
        }
        if(requestCode==12){
            if(resultCode==RESULT_OK){
                refreshData();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void refreshData() {
        dbFoods.clear();

        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<Food> foodsFromDB = dba.getFoods();

        int calsValue = dba.totalCalories();
        int totalItems = dba.getTotalItems();

        String formattedValue = Utils.formatNumber(calsValue);
        String formattedItems = Utils.formatNumber(totalItems);

        totalCals.setText("Total Calories: " + formattedValue);
        totalFoods.setText("Total Foods: " + formattedItems);

        for (int i = 0; i < foodsFromDB.size(); i++){

            String name = foodsFromDB.get(i).getFoodName();
            String dateText = foodsFromDB.get(i).getRecordDate();
            int cals = foodsFromDB.get(i).getCalories();
            int foodId = foodsFromDB.get(i).getFoodId();

            Log.v("FOOD IDS: ", String.valueOf(foodId));


            myFood = new Food();
            myFood.setFoodName(name);
            myFood.setRecordDate(dateText);
            myFood.setCalories(cals);
            myFood.setFoodId(foodId);

            dbFoods.add(myFood);

        }
        dba.close();

        //setup adapter
        foodAdapter = new CustomListViewadapter(DisplayActivity.this, R.layout.list_row, dbFoods);
        listView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_foods, menu);

        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.onActionViewExpanded();

        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.d("message","expanded");
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                foodAdapter.updateList(dbFoods);
                searchView.setQuery("",false);
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("message","Closed");
                return true;
            }
        });
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.add_item_settings){
            startActivityForResult(new Intent(this,MainActivity.class),10);
            return true;
        }
        /*if(id==R.id.action_search){
            SearchView searchView=(SearchView)item.getActionView();
            searchView.setOnQueryTextListener(this);
            searchView.onActionViewExpanded();
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    foodAdapter.updateList(dbFoods);
                    return true;
                }
            });
            return true;

        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String input=s.toLowerCase();

        ArrayList<Food>  newList=new ArrayList<>();

        for(Food name:dbFoods){
            if(name.getFoodName().toLowerCase().contains(input)){
                newList.add(name);
            }
        }

        foodAdapter.updateList(newList);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}

