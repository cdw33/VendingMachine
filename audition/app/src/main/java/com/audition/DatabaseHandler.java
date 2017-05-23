package com.audition;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    final int NICKLE_KEY  = 1;
    final int DIME_KEY    = 2;
    final int QUARTER_KEY = 3;


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
        db.execSQL("CREATE TABLE IF NOT EXISTS " + COINS_TABLE_NAME + "(" + KEY + " INT, " + VALUE + " FLOAT," + QUANTITY + " INT);");

        addDefaultDataToDatabase();
    }

    //Delete all tables and DB, then recreate with default data
    private void resetDatabase(){
        db.execSQL("DROP TABLE IF EXISTS " + INVENTORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + COINS_TABLE_NAME);
    }

    //Add default data to DB
    //TODO - update data from XML file
    private void addDefaultDataToDatabase(){
        addProductsToDB(1, "Cola", 1.00f, 15);
        addProductsToDB(2, "Chips", 0.50f, 12);
        addProductsToDB(3, "Candy", 0.65f, 8);

        addCoinsToDB(1, 0.05f, 15);
        addCoinsToDB(2, 0.10f, 12);
        addCoinsToDB(3, 0.25f, 8);
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
        insertValues.put(KEY, key);
        insertValues.put(NAME, name);
        insertValues.put(PRICE, price);
        insertValues.put(QUANTITY, quantity);
        db.insert(INVENTORY_TABLE_NAME, null, insertValues);
    }

    public int getQuantityOfProduct(int productID){
        Cursor cursor = queryBuilder(QUANTITY, INVENTORY_TABLE_NAME, KEY, String.valueOf(productID));
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(QUANTITY));
    }

    public float getPriceOfProduct(int productID){
        Cursor cursor = queryBuilder(PRICE, INVENTORY_TABLE_NAME, KEY, String.valueOf(productID));
        cursor.moveToFirst();
        return cursor.getFloat(cursor.getColumnIndex(PRICE));
    }

    public String getNameOfProduct(int productID){
        Cursor cursor = queryBuilder(NAME, INVENTORY_TABLE_NAME, KEY, String.valueOf(productID));
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(NAME));
    }

    private Cursor queryBuilder(String columnName, String tableName, String keyName, String keyValue){
//        Cursor cursor = db.rawQuery("SELECT quantity FROM inventory WHERE key = ?", new String[] { String.valueOf(productID) });
        return db.rawQuery("SELECT " + columnName + " FROM " + tableName + " WHERE " + keyName + " = ?", new String[] { keyValue });
    }

    public boolean decrementQuantityOfProduct(int productID){
        int prevQuantity = getQuantityOfProduct(productID);

        if(prevQuantity == 0){
            return false;
        }

        int currQuantity = prevQuantity-1;

        ContentValues args = new ContentValues();
        args.put(QUANTITY, currQuantity);

        db.update(INVENTORY_TABLE_NAME, args, String.format("%s = ?", KEY),
                new String[]{String.valueOf(productID)});

        return true;
    }

    // COIN INVENTORY DATABASE

    public void addCoinsToDB(int key, float value, int quantity){
        ContentValues insertValues = new ContentValues();
        insertValues.put(KEY, key);
        insertValues.put(VALUE, value);
        insertValues.put(QUANTITY, quantity);
        db.insert(COINS_TABLE_NAME, null, insertValues);
    }

    public void incrementQuantityOfCoin(int coinKey){
        int newCoinQty = getQuantityOfCoin(coinKey) + 1;

        ContentValues newValues = new ContentValues();
        newValues.put(QUANTITY, newCoinQty);

        db.update(COINS_TABLE_NAME, newValues, KEY + "=" + String.valueOf(coinKey), null);
    }

    public void decrementQuantityOfCoin(int coinKey){
        int newCoinQty = getQuantityOfCoin(coinKey) - 1;

        ContentValues newValues = new ContentValues();
        newValues.put(QUANTITY, newCoinQty);

        db.update(COINS_TABLE_NAME, newValues, KEY + "=" + String.valueOf(coinKey), null);
    }

    public int getQuantityOfCoin(int coinKey){
        Cursor cursor = db.rawQuery("SELECT " + QUANTITY + " FROM " + COINS_TABLE_NAME + " WHERE " + KEY + " = ?", new String[] { String.valueOf(coinKey) });
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(QUANTITY));
    }
}
