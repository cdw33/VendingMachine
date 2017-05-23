package com.audition;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.Map;

public class VendingMachine extends AppCompatActivity {

//    ButtonHandler buttonHandler;
    DisplayHandler    displayHandler;
    DatabaseHandler   databaseHandler;
    ChangeHandler     changeHandler;
    ProductHandler    productHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vending_machine);

        initialize();
    }

    private void initialize(){
//        buttonHandler     = new ButtonHandler(this);
        displayHandler    = new DisplayHandler(this);
        databaseHandler   = new DatabaseHandler(this);
        changeHandler     = new ChangeHandler(this);
        productHandler    = new ProductHandler(this);
    }



    public void onProductButtonClicked(int productID){
        String productName = databaseHandler.getNameOfProduct(productID);
        float productPrice = databaseHandler.getPriceOfProduct(productID);
        float currentTotal = changeHandler.getSumOfInsertedCoins();

        //Check Product Availability
        if (!productHandler.isProductAvailable(productID, databaseHandler)) { //If sold out
            //Flash SOLD OUT
            return;
        }

        //Check if enough money inserted
//        if(!isProductPaidFor(productID)){ //If not enough
//            lcdController.flashMessage(productPrice, currentTotal);
//            return;
//        }
    }

    public void onButtonClicked(View v){
        switch (v.getId()){
            case R.id.buttonReturnCoins:
                changeHandler.onCoinReturnClicked();
                displayHandler.updateDisplay();
                break;
            case R.id.buttonAddCoin:
                showCoinSelectDialog(this);
                break;
            case R.id.buttonProductBay:
                productHandler.showDispensedProductsDialog(this);
                break;
            case R.id.buttonCoinReturnBay:
                changeHandler.showReturnedCoinsDialog(this);
                break;
            case R.id.buttonProduct1:
                onProductButtonClicked(1);
                break;
            case R.id.buttonProduct2:
                onProductButtonClicked(2);
                break;
            case R.id.buttonProduct3:
                onProductButtonClicked(3);
                break;
            case R.id.buttonProduct4:
                onProductButtonClicked(4);
                break;
            case R.id.buttonProduct5:
                onProductButtonClicked(5);
                break;
            case R.id.buttonProduct6:
                onProductButtonClicked(6);
                break;
            case R.id.buttonProduct7:
                onProductButtonClicked(7);
                break;
            case R.id.buttonProduct8:
                onProductButtonClicked(8);
                break;
            case R.id.buttonProduct9:
                onProductButtonClicked(9);
                break;
            default:
                return;
        }
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
                        onCoinSelect(new Coin(2.5f, 19.05f, 1.52f));
                    }
                });
                buttonNickle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCoinSelect(new Coin(5.0f, 21.21f, 1.95f));
                    }
                });
                buttonDime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCoinSelect(new Coin(2.268f, 17.91f, 1.35f));
                    }
                });
                buttonQuarter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCoinSelect(new Coin(5.67f, 24.26f, 1.75f));
                    }
                });
                buttonHalfDollar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCoinSelect(new Coin(11.034f, 30.61f, 2.15f));
                    }
                });
                buttonDollar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCoinSelect(new Coin(8.1f, 26.49f, 2.0f));
                    }
                });
                buttonRandom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCoinSelect(new Coin());
                    }
                });
            }

            public void onCoinSelect(Coin coin){
                changeHandler.onCoinInserted(coin);
                displayHandler.updateDisplay(changeHandler.getSumOfInsertedCoins());
                coinSelectDialog.dismiss();
            }
        });


        coinSelectDialog.show();
    }
}
