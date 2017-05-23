package com.audition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

// This is the main class for the Vending Machine. This class contains objects which perform
// specific jobs within the machine. The UI Button handling is implemented here, as well as the
// topmost logic of the vending process.

public class VendingMachine extends AppCompatActivity implements CallbackInterface {

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
        databaseHandler   = new DatabaseHandler(this);
        displayHandler    = new DisplayHandler(this);
        changeHandler     = new ChangeHandler(this, databaseHandler);
        productHandler    = new ProductHandler(this, databaseHandler);
    }

    @Override
    public void updateDisplay(){
        displayHandler.updateDisplay(changeHandler.getSumOfInsertedCoins());
    }

    public void onProductButtonClicked(int productID){
        float productPrice = databaseHandler.getPriceOfProduct(productID);
        float currentTotal = changeHandler.getSumOfInsertedCoins();

        //Check Product Availability
        if (!productHandler.isProductAvailable(productID)) { //If sold out
            displayHandler.flashMessage(displayHandler.SOLD_OUT);
            return;
        }

        //Check if enough money inserted
        if(currentTotal < productPrice){ //If not enough money inserted
            displayHandler.flashPrice(productPrice);
            return;
        }

        //TODO - Check if exact change is required

        //Move inserted coins into coin storage
        changeHandler.moveCoinsToStorage();

        //Dispense product
        productHandler.dispenseProduct(productID);

        //TODO - Make change for customer

        //Update Display
        displayHandler.flashMessage(displayHandler.THANK_YOU);
    }

    //Each button in its respective layout has its onClick value set to this function.
    //The button is identified by its given ID and it is handled accordingly
    public void onButtonClicked(View v){
        switch (v.getId()){
            case R.id.buttonReturnCoins:
                changeHandler.onCoinReturnClicked();
                displayHandler.updateDisplay();
                break;
            case R.id.buttonAddCoin:
                changeHandler.showCoinSelectDialog(this, displayHandler);
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

}
