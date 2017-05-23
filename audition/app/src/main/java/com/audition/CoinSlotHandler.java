package com.audition;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by Chris on 5/23/2017.
 */

public class CoinSlotHandler extends VendingMachine {

    Activity activity;

    ArrayList<Coin> listInsertedCoins; //Holds coins currently inserted

    CoinSlotHandler(Activity activity){
        this.activity = activity;

        initialize();
    }

    private void initialize(){
        listInsertedCoins = new ArrayList<Coin>();
    }

    public void onCoinInserted(Coin coin){
        //Determine Coin Value

        //If coin is invalid
             //push to return slot and return


        listInsertedCoins.add(coin);

        //displayHandler.updateDisplay();
    }

    public boolean isCoinSlotEmpty(){
        return listInsertedCoins.isEmpty();
    }

    //Returns total value of all inserted coins
    public float getSumOfInsertedCoins(){
        float totalValue = 0.0f;

        for(Coin coin : listInsertedCoins){
            totalValue += coin.getValue();
        }

        return totalValue;
    }




}
