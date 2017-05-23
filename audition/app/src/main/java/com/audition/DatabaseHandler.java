package com.audition;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Chris on 5/23/2017.
 */

public class DatabaseHandler extends VendingMachine {

    Activity activity;

    SQLiteDatabase db;

    final String DB_NAME = "VendingMachineDB";

    final String INVENTORY_TABLE_NAME = "inventory";
    final String COINS_TABLE_NAME     = "coins";

    final String KEY      = "key";
    final String NAME     = "name";
    final String PRICE    = "price";
    final String QUANTITY = "quantity";
    final String VALUE    = "value";

//    enum QueryTypes{
//        CREATE_TABLE,
//        DROP_TABLE,
//        INSERT,
//        SELECT,
//    }

    DatabaseHandler(Activity activity){
        this.activity = activity;

        initializeDatabase();
    }

    // DATABASE INITIALIZATION

    private void initializeDatabase(){
        db = activity.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS " + INVENTORY_TABLE_NAME + "(" + KEY + " INT, " + NAME + " VARCHAR," + PRICE + " FLOAT," + QUANTITY + " INT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + COINS_TABLE_NAME + "(" + VALUE + " FLOAT," + QUANTITY + " INT);");

        addDefaultDataToDatabase();
    }

    //Delete all tables and DB, then recreate with default data
    private void resetDatabase(){
        db.execSQL("DROP TABLE IF EXISTS " + INVENTORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + COINS_TABLE_NAME);

        activity.deleteDatabase(DB_NAME);

        initializeDatabase();
    }

    //Add default data to DB
    //TODO - update data from XML file
    private void addDefaultDataToDatabase(){
        addProductsToDB(1, "Cola", 1.00f, 15);
        addProductsToDB(2, "Chips", 0.50f, 12);
        addProductsToDB(3, "Candy", 0.65f, 8);

        addCoinsToDB(0.05f, 15);
        addCoinsToDB(0.10f, 12);
        addCoinsToDB(0.25f, 8);
    }

//    private String createQuery(QueryTypes qt){
//        switch (qt){
//            case CREATE_TABLE:
//                break;
//            case DROP_TABLE:
//                break;
//            case INSERT:
//                break;
//            case SELECT:
//                break;
//        }
//    }

    // PRODUCT INVENTORY DATABASE

    public void addProductsToDB(int key, String name, float price, int quantity){
        ContentValues insertValues = new ContentValues();
        insertValues.put("key", key);
        insertValues.put("name", name);
        insertValues.put("price", price);
        insertValues.put("quantity", quantity);
        db.insert("inventory", null, insertValues);
    }

    // COIN INVENTORY DATABASE

    public void addCoinsToDB(float value, int quantity){
        ContentValues insertValues = new ContentValues();
        insertValues.put("value", value);
        insertValues.put("quantity", quantity);
        db.insert("coins", null, insertValues);
    }
}
