package com.audition;

import android.app.Activity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

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
        tvDisplay.setText(INSERT_COIN);
    }

    public void setDisplayText(String msg){
        tvDisplay.setText(msg);
    }

    public String getDisplayText(){
        return tvDisplay.getText().toString();
    }

    public void updateDisplay(){
        setDisplayText(INSERT_COIN);
    }

    public void updateDisplay(float currentTotal){
        if (currentTotal > 0.0f) { //If no coins inserted
            setDisplayText("$" + String.format("%.2f", currentTotal));
            return;
        }

        updateDisplay();
    }

    // TODO - Make Generic Method to handle multiple input cases
    public void flashMessage(String flashMsg){
        final String resetMsg = getDisplayText(); //Get current display contents

        setDisplayText(flashMsg); //Write temporary message to display

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setDisplayText(resetMsg); //Restore original display contents
                    }
                });
            }
        }, 1000);
    }

    public void flashPrice(float price){
        flashMessage("$" + String.format("%.2f", price));
    }
}
