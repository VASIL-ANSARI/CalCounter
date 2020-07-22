package com.example.calcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import data.DatabaseHandler;
import android.view.*;
import model.Food;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText foodName, foodCals;
    private Button submitButton;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new DatabaseHandler(MainActivity.this);
        //ArrayList<Food> foodsFromDB = dba.getFoods();




            foodName = (EditText) findViewById(R.id.foodEditText);
            foodCals = (EditText) findViewById(R.id.caloriesEditText);
            submitButton = (Button) findViewById(R.id.submitButton);

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    saveDataToDB();

                }
            });
    }

    private void saveDataToDB() {

        Food food = new Food();
        String name = foodName.getText().toString().trim();
        String calsString = foodCals.getText().toString().trim();


        if (name.equals("") || calsString.equals("")) {

            Toast.makeText(getApplicationContext(), "No empty fields allowed", Toast.LENGTH_LONG).show();

        }else {
            int cals = Integer.parseInt(calsString);

            food.setFoodName(name);
            food.setCalories(cals);

            dba.addFood(food);
            dba.close();


            //clear the form
            foodName.setText("");
            foodCals.setText("");

            Intent intent=new Intent();
            setResult(RESULT_OK,intent);

            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }
}

