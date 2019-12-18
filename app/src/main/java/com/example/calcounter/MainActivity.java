package com.example.calcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import data.DatabaseHandler;
import android.view.*;
import model.Food;
import android.widget.*;
public class MainActivity extends AppCompatActivity {
    private EditText title, desc;
    private Button submit;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dba = new DatabaseHandler(MainActivity.this);

        title = (EditText) findViewById(R.id.foodEditText);
        desc = (EditText) findViewById(R.id.caloriesEditText);
        submit = (Button) findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDataToDB();

            }
        });
    }

    private void saveDataToDB() {
        Food food = new Food();
        String name = title.getText().toString().trim();
        String description = desc.getText().toString().trim();

        if (name.equals("") || description.equals("")) {
            Toast.makeText(getApplicationContext(), "No empty fields allowed", Toast.LENGTH_LONG).show();

        }else {
            food.setTitle(name);
            food.setDesc(description);

            dba.addFood(food);
            dba.close();


            //clear the form
            title.setText("");
            desc.setText("");

            //take users to next screen (display all entered items)
            startActivity(new Intent(MainActivity.this, DisplayActivity.class));
        }
    }
}

