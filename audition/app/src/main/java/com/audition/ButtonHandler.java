package com.audition;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

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
                //showCoinSelectDialog(activity);
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

    public void showCoinSelectDialog(final Activity activity){
        LayoutInflater li = LayoutInflater.from(activity);
        final View coinView = li.inflate(R.layout.coin_select, null);

        final AlertDialog coinSelectDialog = new AlertDialog.Builder(activity)
                .setView(coinView)
                .setTitle("Coin Select")
                .setNegativeButton(android.R.string.cancel, null)
                .setCancelable(true)
                .create();

        coinSelectDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button buttonPenny       = (Button) coinView.findViewById(R.id.buttonPenny);
                final Button buttonNickle      = (Button) coinView.findViewById(R.id.buttonNickle);
                final Button buttonDime        = (Button) coinView.findViewById(R.id.buttonDime);
                final Button buttonQuarter     = (Button) coinView.findViewById(R.id.buttonQuarter);
                final Button buttonRandom      = (Button) coinView.findViewById(R.id.buttonRandom);
                final Button buttonHalfDollar  = (Button) coinView.findViewById(R.id.buttonHalfDollar);
                final Button buttonDollar      = (Button) coinView.findViewById(R.id.buttonDollar);

                //Coin Button onClickListeners
                buttonPenny.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeHandler.onCoinInserted(new Coin(2.5f, 19.05f, 1.52f));
                        coinSelectDialog.dismiss();
                    }
                });
                buttonNickle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeHandler.onCoinInserted(new Coin(5.0f, 21.21f, 1.95f));
                        coinSelectDialog.dismiss();
                    }
                });
                buttonDime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeHandler.onCoinInserted(new Coin(2.268f, 17.91f, 1.35f));
                        coinSelectDialog.dismiss();
                    }
                });
                buttonQuarter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeHandler.onCoinInserted(new Coin(5.67f, 24.26f, 1.75f));
                        coinSelectDialog.dismiss();
                    }
                });
                buttonHalfDollar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeHandler.onCoinInserted(new Coin(11.034f, 30.61f, 2.15f));
                        coinSelectDialog.dismiss();
                    }
                });
                buttonDollar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeHandler.onCoinInserted(new Coin(8.1f, 26.49f, 2.0f));
                        coinSelectDialog.dismiss();
                    }
                });
                buttonRandom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeHandler.onCoinInserted(new Coin());
                        coinSelectDialog.dismiss();
                    }
                });
            }
        });

        coinSelectDialog.show();
    }

}
