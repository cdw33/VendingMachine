package com.audition;

import android.app.Activity;
import android.widget.TextView;

public class DisplayHandler extends VendingMachine {

    Activity activity;

    TextView tvDisplay;

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
}
