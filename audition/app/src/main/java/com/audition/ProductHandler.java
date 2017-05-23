package com.audition;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by Chris on 5/23/2017.
 */

public class ProductHandler extends VendingMachine {

    Activity activity;

    ArrayList<Product> listDispensedProducts;

    ProductHandler(Activity activity){
        this.activity = activity;

        initialize();
    }

    private void initialize(){
        listDispensedProducts = new ArrayList<Product>();
    }


}
