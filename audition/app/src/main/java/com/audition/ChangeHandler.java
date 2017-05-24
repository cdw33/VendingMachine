package com.audition;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// This class contains many Coin and change related functionality. Most of the logic for the Coin
// insert slot and the Coin return button & bay can be found in here. The methods are split between
// Coin insertion and Coin return functions. There are comments which distinguish their respective
// parts.

public class ChangeHandler extends VendingMachine {

    Activity activity;
    DatabaseHandler db;
    CallbackInterface ic;

    private ArrayList<Coin> listInsertedCoins; //Holds coins currently inserted
    private ArrayList<Coin> listReturnedCoins; //Holds coins in return bay

    // Coin specification - source U.S. Mint
    // https://www.usmint.gov/learn/coin-and-medal-programs/coin-specifications
    // Coin(int key, float diameter (millimeters), float width (mm), float weight (grams), float value ($))
    Coin penny      = new Coin(2.5f, 19.05f, 1.52f, 0.01f);
    Coin nickel     = new Coin(1, 5.0f, 21.21f, 1.95f, 0.05f);
    Coin dime       = new Coin(2, 2.268f, 17.91f, 1.35f, 0.1f);
    Coin quarter    = new Coin(3, 5.67f, 24.26f, 1.75f, 0.25f);
    Coin halfDollar = new Coin(11.034f, 30.61f, 2.15f, 0.5f);
    Coin dollar     = new Coin(8.1f, 26.49f, 2.0f, 1.0f);

    Map<Integer, Coin> coinKeyMap;

    ChangeHandler(Activity activity, DatabaseHandler db){
        this.activity = activity;
        this.ic = (CallbackInterface) activity;
        this.db = db;

        initialize();
    }

    private void initialize(){
        listInsertedCoins = new ArrayList<>();
        listReturnedCoins = new ArrayList<>();

        coinKeyMap = new HashMap<>();
        coinKeyMap.put(nickel.getKey(), nickel);
        coinKeyMap.put(dime.getKey(), dime);
        coinKeyMap.put(quarter.getKey(), quarter);
    }

    /************************
     *   Coin Insert Slot   *
     ************************/

    public void onCoinInserted(Coin coin){
        determineCoinValue(coin);

        if(!isCoinValid(coin)){ //If coin is invalid
            sendCoinToReturnBay(coin); //push to return slot
            return;
        }

        addCoinToInsertedList(coin);

        ic.updateDisplay();
    }

    public void addCoinToInsertedList(Coin coin){
        listInsertedCoins.add(coin);
    }

    public ArrayList<Coin> getListInsertedCoins(){
        return listInsertedCoins;
    }

    public boolean isCoinSlotEmpty(){
        return listInsertedCoins.isEmpty();
    }

    public void clearInsertedCoinsList(){
        listInsertedCoins.clear();
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
        //If coin is valid US currency, update value
        if(coin.isSame(penny)){
            coin.setValue(penny.getValue());
        }
        else if(coin.isSame(nickel)){
            coin.setValue(nickel.getValue());
            coin.setKey(db.NICKEL_KEY);
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
    //we only accept nickel, dime, quarter
    private boolean isCoinValid(Coin coin){
        if(coin.getValue() == nickel.getValue() ||
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

        clearInsertedCoinsList();

        ic.updateDisplay();
    }

    public void showCoinSelectDialog(final Activity activity, final DisplayHandler displayHandler){
        LayoutInflater li = LayoutInflater.from(activity);
        final View coinView = li.inflate(R.layout.coin_select, null);

        final String UNLOCK = "Unlock";
        final String LOCK   = "Lock";

        final AlertDialog coinSelectDialog = new AlertDialog.Builder(activity)
                .setView(coinView)
                .setTitle("Coin Select")
                .setPositiveButton("Lock", null)
                .setNegativeButton("Close", null)
                .setCancelable(true)
                .create();

        coinSelectDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                final Button buttonPenny      = (Button) coinView.findViewById(R.id.buttonPenny);
                final Button buttonNickel     = (Button) coinView.findViewById(R.id.buttonNickel);
                final Button buttonDime       = (Button) coinView.findViewById(R.id.buttonDime);
                final Button buttonQuarter    = (Button) coinView.findViewById(R.id.buttonQuarter);
                final Button buttonRandom     = (Button) coinView.findViewById(R.id.buttonRandom);
                final Button buttonHalfDollar = (Button) coinView.findViewById(R.id.buttonHalfDollar);
                final Button buttonDollar     = (Button) coinView.findViewById(R.id.buttonDollar);

                final Button buttonLock = coinSelectDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                //Coin Button onClickListeners
                buttonPenny.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCoinSelect(new Coin(penny));
                    }
                });
                buttonNickel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCoinSelect(new Coin(nickel));
                    }
                });
                buttonDime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCoinSelect(new Coin(dime));
                    }
                });
                buttonQuarter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCoinSelect(new Coin(quarter));
                    }
                });
                buttonHalfDollar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCoinSelect(new Coin(halfDollar));
                    }
                });
                buttonDollar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCoinSelect(new Coin(dollar));
                    }
                });
                buttonRandom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCoinSelect(new Coin());
                    }
                });
                buttonLock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence btnTxt = coinSelectDialog.getButton(coinSelectDialog.BUTTON_POSITIVE).getText();

                        btnTxt = btnTxt.equals(UNLOCK) ? LOCK : UNLOCK;

                        coinSelectDialog.getButton(coinSelectDialog.BUTTON_POSITIVE).setText(btnTxt);

                        updateDialogTitle();
                    }
                });
            }

            public void onCoinSelect(Coin coin){
                onCoinInserted(coin);
                ic.updateDisplay();

                updateDialogTitle();

                String lockStr = coinSelectDialog.getButton(coinSelectDialog.BUTTON_POSITIVE).getText().toString();
                if (lockStr.equals(LOCK)) {
                    coinSelectDialog.dismiss();
                }
            }

            public void updateDialogTitle(){
                String lockStr = coinSelectDialog.getButton(coinSelectDialog.BUTTON_POSITIVE).getText().toString();

                if (lockStr.equals(LOCK)) {
                    coinSelectDialog.setTitle("Coin Select");
                }
                else{
                    float currentTotal = getSumOfInsertedCoins();
                    coinSelectDialog.setTitle("Coin Select     -     $" + String.format("%.2f", currentTotal) + "     -");
                }
            }
        });

        coinSelectDialog.show();

        coinSelectDialog.getButton(coinSelectDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        coinSelectDialog.getButton(coinSelectDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);


    }

    /***********************
     *   Coin Return Bay   *
     ***********************/

    public void onCoinReturnClicked(){
        for(Coin coin : listInsertedCoins){
            listReturnedCoins.add(coin);
        }
        listInsertedCoins.clear();

        ic.updateDisplay();
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
                .setNegativeButton("Close", null)
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
                            ", Diameter: " + String.format("%.4f", coin.getDiameter()) + "mm,\n" +
                            "Width: " + String.format("%.4f", coin.getWidth()) + "mm" +
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

        returnSlotDialog.getButton(returnSlotDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        returnSlotDialog.getButton(returnSlotDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    /**************************
     *   Change Calculation   *
     **************************/
    
    // The only possible change values are 5, 10, 15, & 20 cents. If the buyer overpays by more,
    // change can be made with the extra coins. All 4 change configurations can be made with 3
    // coins; 2 Nickels and 1 dime. This function checks that those minimum requirements are met.
    public boolean isChangeAvailableForNextPurchase(){

        final int requiredNickelQty = 2;
        final int requiredDimeQty   = 1;

        if(db.getQuantityOfCoin(nickel.getKey()) < requiredNickelQty ||
                db.getQuantityOfCoin(dime.getKey()) < requiredDimeQty) {
            return false;
        }

        return true;
    }

    public void makeChange(float productPrice, float currentTotal){
        float changeNeeded = Math.abs(productPrice - currentTotal);
        changeNeeded = round(changeNeeded, 2); //round up to 2 decimal places

        //Get change map
        Map<Integer, Integer> changeMap = calculateChange(changeNeeded);

        //Iterate through map
        for (Map.Entry<Integer, Integer> entry : changeMap.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();

            for(int i=0; i<value; i++){ //for number of given coin in change
                db.decrementQuantityOfCoin(key); //remove from DB

                sendCoinToReturnBay(new Coin(coinKeyMap.get(key))); //Send to return bay
            }
        }
    }

    public boolean canChangeBeMade(float currentTotal, float productPrice){
        float changeNeeded = Math.abs(currentTotal - productPrice);
        changeNeeded = round(changeNeeded, 2); //round up to 2 decimal places

        //If change cannot be made, an empty Map will be returned
        return !calculateChange(changeNeeded).isEmpty();
    }

    /*
    In researching the specific issue of change calculation, I found several solutions using
    dynamic programming and Breadth-First Searching of trees. They all appeared rather slow
    and didn't exactly fit this use case. My solution is simply to attempt to make change like a
    cashier would. Collect quarters until any more would be too much change, repeat for dimes,
    then nickels. Because we only have those 3 coins, and we know that all change requests will
    be for multiples of 5, the solution was a lot simpler than first thought. At its worst case,
    this method will run in O(n), where n is the number of coins in the vending machine.
    */
    public Map<Integer, Integer> calculateChange(float changeNeeded){

        //Get change from DB
        Map<Integer, Integer> totalCoins = new HashMap<>();
        totalCoins.put(nickel.getKey(), db.getQuantityOfCoin(nickel.getKey()));
        totalCoins.put(dime.getKey(), db.getQuantityOfCoin(dime.getKey()));
        totalCoins.put(quarter.getKey(), db.getQuantityOfCoin(quarter.getKey()));

        //include change in coin slot
        for(Coin coin: listInsertedCoins){
            totalCoins.put(coin.getKey(), totalCoins.get(coin.getKey()) + 1);
        }

        //Initialize Map to hold change
        Map<Integer, Integer> changeMap = new HashMap<>();
        changeMap.put(nickel.getKey(), 0);
        changeMap.put(dime.getKey(), 0);
        changeMap.put(quarter.getKey(), 0);

        //Calculate change
        changeNeeded = changeHelper(totalCoins, changeMap, changeNeeded, quarter);
        changeNeeded = changeHelper(totalCoins, changeMap, changeNeeded, dime);
        changeNeeded = changeHelper(totalCoins, changeMap, changeNeeded, nickel);

        //Verify change can be make
        if (changeNeeded >= 0.01f) {
            changeMap.clear(); //Change cannot be made, return empty map
        }

        return changeMap;
    }

    private float changeHelper(Map<Integer, Integer> totalCoins, Map<Integer, Integer> changeMap, float changeNeeded, Coin coin){
        int key = coin.getKey();
        float val = coin.getValue();

        while(totalCoins.get(key) > 0 && changeNeeded - val >= 0 ){
            changeNeeded -= val;
            totalCoins.put(key, totalCoins.get(key) - 1);
            changeMap.put(key, changeMap.get(key) + 1);
        }

        return changeNeeded;
    }

    //http://docs.oracle.com/javase/6/docs/api/java/math/BigDecimal.html#ROUND_HALF_UP
    private static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue() + 0.001f;
    }
}
