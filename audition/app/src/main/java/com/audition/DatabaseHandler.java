package com.audition;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Chris on 5/23/2017.
 */

public class DatabaseHandler extends VendingMachine {

    Activity activity;

    SQLiteDatabase db;

    DatabaseHandler(Activity activity){
        this.activity = activity;

        initializeDatabase();
    }

    private void initializeDatabase(){
        db = activity.openOrCreateDatabase("VendingMachineDB", Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS inventory(key INT, name VARCHAR,price FLOAT,quantity INT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS change(value FLOAT,quantity INT);");
    }
}
