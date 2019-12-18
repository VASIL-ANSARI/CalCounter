package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Food;

public class DatabaseHandler extends SQLiteOpenHelper {
    private Context ctx;
    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERY_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY," + Constants.TITLE + " TEXT,"
                + Constants.DESCRIPTION + " TEXT,"
                + Constants.DATE + " LONG );";

        db.execSQL(CREATE_GROCERY_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(db);

    }

    //Add list
    public void addList(Food grocery) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.TITLE, grocery.getTitle());
        values.put(Constants.DESCRIPTION, grocery.getDesc());
        values.put(Constants.DATE, java.lang.System.currentTimeMillis());



        //Insert the row
        db.insert(Constants.TABLE_NAME, null, values);

        Log.d("Saved!!", "Saved to DB");

    }


    //Get a Grocery
    public Food getGrocery(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {Constants.KEY_ID,
                        Constants.TITLE, Constants.DESCRIPTION},
                Constants.KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();


        Food item = new Food();
        item.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID)));
        item.setDesc(cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION)));


        return item;
    }


    //Get all Groceries
    public ArrayList<Food> getFoods() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Food> groceryList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {
                Constants.KEY_ID,Constants.TITLE,
                Constants.DESCRIPTION}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Food grocery = new Food();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                grocery.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE)));
                grocery.setDesc(cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION)));


                // Add to the groceryList
                groceryList.add(grocery);

            }while (cursor.moveToNext());
        }

        return groceryList;
    }

    //add content to db - add food
    public void addFood(Food food) {

        SQLiteDatabase dba = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.TITLE, food.getTitle());
        values.put(Constants.DESCRIPTION, food.getDesc());

        dba.insert(Constants.TABLE_NAME, null, values);

        Log.v("Added Food item", "Yes!!");

        dba.close();
    }



}
