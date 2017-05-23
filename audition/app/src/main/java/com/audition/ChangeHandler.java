package com.audition;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Chris on 5/23/2017.
 */

public class ChangeHandler extends VendingMachine {

    Activity activity;

    DatabaseHandler db;

    ArrayList<Coin> listInsertedCoins; //Holds coins currently inserted
    ArrayList<Coin> listReturnedCoins; //Holds coins in return bay

    ChangeHandler(Activity activity, DatabaseHandler db){
        this.activity = activity;
        this.db = db;

        initialize();
    }

    private void initialize(){
        listInsertedCoins = new ArrayList<Coin>();
        listReturnedCoins = new ArrayList<Coin>();
    }

    /*
        Coin Insert Slot
    */

    public void onCoinInserted(Coin coin){
        determineCoinValue(coin);

        if(!isCoinValid(coin)){ //If coin is invalid
            sendCoinToReturnBay(coin); //push to return slot
            return;
        }

        listInsertedCoins.add(coin);
    }

    public ArrayList<Coin> getListInsertedCoins(){
        return listInsertedCoins;
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
            coin.setKey(db.NICKLE_KEY);
        }
        else if (coin.isSame(dime)) {
            coin.setValue(dime.getValue());
            coin.setKey(db.DIME_KEY);
        }
        else if (coin.isSame(quarter)) {
            coin.setValue(quarter.getValue());
            coin.setKey(db.QUARTER_KEY);
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

    public void moveCoinsToStorage(){
        //Add each coin in coin slot to DB
        for(Coin coin : listInsertedCoins){ //For each inserted coin
            db.incrementQuantityOfCoin(coin.getKey()); //Add to change DB
        }

        listInsertedCoins.clear();
    }

    /*
        Coin Return Bay
    */

    public void onCoinReturnClicked(){
        for(Coin coin : listInsertedCoins){
            listReturnedCoins.add(coin);
        }
        listInsertedCoins.clear();
    }

    private void emptyReturnBay(){
        listReturnedCoins.clear();
    }

    private int getNumberCoinsInReturnBay(){
        return listReturnedCoins.size();
    }

    public void sendCoinToReturnBay(Coin coin){
        listReturnedCoins.add(coin);
    }

    public void showReturnedCoinsDialog(final Activity activity){
        LayoutInflater li = LayoutInflater.from(activity);
        final View returnView = li.inflate(R.layout.coin_return_bay, null);

        final AlertDialog returnSlotDialog = new AlertDialog.Builder(activity)
                .setView(returnView)
                .setTitle("Returned Coins")
                .setPositiveButton("Remove Coins", null)
                .setNegativeButton(android.R.string.cancel, null)
                .setCancelable(true)
                .create();

        returnSlotDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button returnButton = returnSlotDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                final TextView tvReturnedCoins  = (TextView) returnView.findViewById(R.id.tvReturnedCoins);

                //List and display returned coins
                String returnedCoinSB = "";
                for(Coin coin : listReturnedCoins){
                    returnedCoinSB = returnedCoinSB + "Weight: " + String.format("%.4f", coin.getWeight()) + "g" +
                            ", Diameter: " + String.format("%.4f", coin.getDiameter()) + "mm" +
                            ",\nWidth: " + String.format("%.4f", coin.getWidth()) + "mm" +
                            ", Value: " + (coin.getValue() == -1.0f ? "?" : "$" + String.format("%.2f", coin.getValue())) + "\n\n";
                }
                tvReturnedCoins.setText(returnedCoinSB);

                //Key Click Listeners
                returnButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(getNumberCoinsInReturnBay() > 0){
                            Toast.makeText(activity, "All coins removed from return slot.",
                                    Toast.LENGTH_SHORT).show();
                            emptyReturnBay();
                        }

                        returnSlotDialog.dismiss();
                    }
                });

            }
        });

        returnSlotDialog.show();
    }

}
