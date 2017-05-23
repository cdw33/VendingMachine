package com.audition;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Chris on 5/23/2017.
 */

public class ButtonHandler extends VendingMachine {

    Activity activity;

    //Product Selection Buttons
    Button buttonProduct1;
    Button buttonProduct2;
    Button buttonProduct3;
    Button buttonProduct4;
    Button buttonProduct5;
    Button buttonProduct6;
    Button buttonProduct7;
    Button buttonProduct8;
    Button buttonProduct9;

    Button buttonReturnCoins;
    Button buttonAddCoin;
    Button buttonProductBay;
    Button buttonCoinReturnBay;

    ButtonHandler(Activity activity){
        this.activity = activity;

        initializeButtons();
        initOnClickListeners();
    }

    private void initializeButtons(){
        buttonReturnCoins    = (Button) activity.findViewById(R.id.buttonReturnCoins);
        buttonAddCoin        = (Button) activity.findViewById(R.id.buttonAddCoin);
        buttonProductBay     = (Button) activity.findViewById(R.id.buttonProductBay);
        buttonCoinReturnBay  = (Button) activity.findViewById(R.id.buttonCoinReturnBay);

        buttonProduct1 = (Button) activity.findViewById(R.id.buttonProduct1);
        buttonProduct2 = (Button) activity.findViewById(R.id.buttonProduct2);
        buttonProduct3 = (Button) activity.findViewById(R.id.buttonProduct3);
        buttonProduct4 = (Button) activity.findViewById(R.id.buttonProduct4);
        buttonProduct5 = (Button) activity.findViewById(R.id.buttonProduct5);
        buttonProduct6 = (Button) activity.findViewById(R.id.buttonProduct6);
        buttonProduct7 = (Button) activity.findViewById(R.id.buttonProduct7);
        buttonProduct8 = (Button) activity.findViewById(R.id.buttonProduct8);
        buttonProduct9 = (Button) activity.findViewById(R.id.buttonProduct9);
    }

    private void initOnClickListeners(){
        /*
            Coin Return
        */
        buttonReturnCoins.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        /*
            Add Coin
        */
        buttonAddCoin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        /*
            Open Product Bay
        */
        buttonProductBay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        /*
            Open Coin Return Bay
        */
        buttonCoinReturnBay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        /*
            Product Selection Button onClickListeners
        */
        buttonProduct1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        buttonProduct2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        buttonProduct3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        buttonProduct4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        buttonProduct5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        buttonProduct6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        buttonProduct7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        buttonProduct8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        buttonProduct9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }

}
