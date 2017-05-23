package com.audition;

import android.app.Activity;
import android.widget.TextView;

public class DisplayHandler extends VendingMachine {

    Activity activity;

    TextView tvDisplay;

    final String INSERT_COIN  = "INSERT COIN";
    final String SOLD_OUT     = "SOLD OUT";
    final String THANK_YOU    = "THANK YOU";
    final String EXACT_CHANGE = "EXACT CHANGE ONLY";

    DisplayHandler(Activity activity){
        this.activity = activity;

        initializeDisplay();
    }

    private void initializeDisplay(){
        tvDisplay = (TextView) activity.findViewById(R.id.tvLCD);
    }

    public void setDisplayText(String msg){
        tvDisplay.setText(msg);
    }

    public String getDisplayText(){
        return tvDisplay.getText().toString();
    }

    public void updateDisplay(){
        //Determine state
        //is money in machine?
//
//        if (coinSlotHandler.isCoinSlotEmpty()) { //If no coins inserted
//            setDisplayText(INSERT_COIN);
//        }
//
//        setDisplayText(String.valueOf(coinSlotHandler.getSumOfInsertedCoins()));

        setDisplayText(INSERT_COIN);
    }

    public void updateDisplay(float currentTotal){
        //Determine state
        //is money in machine?

        if (currentTotal > 0.0f) { //If no coins inserted
            setDisplayText(String.valueOf(currentTotal));
            return;
        }

        setDisplayText(INSERT_COIN);


    }
}
