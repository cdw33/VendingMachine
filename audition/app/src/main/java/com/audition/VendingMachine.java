package com.audition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class VendingMachine extends AppCompatActivity {

    ButtonHandler buttonHandler;
    DisplayHandler displayHandler;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vending_machine);

        initialize();
    }

    private void initialize(){
        buttonHandler = new ButtonHandler(this);
        displayHandler = new DisplayHandler(this);
        databaseHandler = new DatabaseHandler(this);
    }

}
