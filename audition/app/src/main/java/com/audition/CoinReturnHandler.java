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

public class CoinReturnHandler extends VendingMachine{

    Activity activity;

    ArrayList<Coin> listReturnedCoins;

    CoinReturnHandler(Activity activity){
        this.activity = activity;

        initialize();
    }

    private void initialize(){
        listReturnedCoins = new ArrayList<Coin>();
    }

    public void onCoinReturnClicked(ArrayList<Coin> listInsertedCoins){
        for(Coin coin : listInsertedCoins){
            listReturnedCoins.add(coin);
        }
        listInsertedCoins.clear();
    }

    private void removeReturnedCoins(){
        listReturnedCoins.clear();
    }

    private int getNumberCoinsInReturn(){
        return listReturnedCoins.size();
    }

    public void returnCoin(Coin coin){
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
                        if(getNumberCoinsInReturn() > 0){
                            Toast.makeText(activity, "All coins removed from return slot.",
                                    Toast.LENGTH_SHORT).show();
                            removeReturnedCoins();
                        }

                        returnSlotDialog.dismiss();
                    }
                });

            }
        });

        returnSlotDialog.show();
    }
}
