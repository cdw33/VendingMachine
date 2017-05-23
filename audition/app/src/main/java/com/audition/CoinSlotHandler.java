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
        determineCoinValue(coin);

        if(!isCoinValid(coin)){ //If coin is invalid
            //push to return slot
            return;
        }

        listInsertedCoins.add(coin);
    }

    public ArrayList<Coin> getListInsertedCoins(){
        return listInsertedCoins;
    }

    public void coinReturnClicked(){
        listInsertedCoins.clear();
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

    private void determineCoinValue(Coin coin){
        // nickle  5.000g 21.21mm 1.95mm
        // dime    2.268g 17.91mm 1.35mm
        // quarter 5.670g 24.26mm 1.75mm
        // Coin specification - source U.S. Mint
        // https://www.usmint.gov/learn/coin-and-medal-programs/coin-specifications

        //Coin(float diameter (millimeters), float width (mm), float weight (grams), float value ($))
        Coin penny = new Coin(2.5f, 19.05f, 1.52f, 0.01f);
        Coin nickle = new Coin(5.0f, 21.21f, 1.95f, 0.05f);
        Coin dime = new Coin(2.268f, 17.91f, 1.35f, 0.1f);
        Coin quarter = new Coin(5.67f, 24.26f, 1.75f, 0.25f);
        Coin halfDollar = new Coin(11.034f, 30.61f, 2.15f, 0.5f);
        Coin dollar = new Coin(8.1f, 26.49f, 2.0f, 1.0f);

        //If coin is valid US currency, update value
        if(coin.isSame(penny)){
            coin.setValue(penny.getValue());
        }
        else if(coin.isSame(nickle)){
            coin.setValue(nickle.getValue());
        }
        else if (coin.isSame(dime)) {
            coin.setValue(dime.getValue());
        }
        else if (coin.isSame(quarter)) {
            coin.setValue(quarter.getValue());
        }
        else if (coin.isSame(halfDollar)) {
            coin.setValue(halfDollar.getValue());
        }
        else if (coin.isSame(dollar)) {
            coin.setValue(dollar.getValue());
        }
    }

    //Valid means coin can be accepted. A US Penny is authentic but not valid since
    //we only accept nickle, dime, quarter
    private boolean isCoinValid(Coin coin){
        Coin nickle  = new Coin(5.0f, 21.21f, 1.95f, 0.05f);
        Coin dime    = new Coin(2.268f, 17.91f, 1.35f, 0.1f);
        Coin quarter = new Coin(5.67f, 24.26f, 1.75f, 0.25f);

        if(coin.getValue() == nickle.getValue() ||
                coin.getValue() == dime.getValue() ||
                coin.getValue() ==quarter.getValue()){
            return true;
        }

        return false;
    }

}
