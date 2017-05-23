package com.audition;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductHandler extends VendingMachine {

    Activity activity;
    DatabaseHandler db;

    ArrayList<Product> listDispensedProducts;

    ProductHandler(Activity activity, DatabaseHandler db){
        this.activity = activity;
        this.db = db;

        initialize();
    }

    private void initialize(){
        listDispensedProducts = new ArrayList<>();
    }

    public boolean isProductAvailable(int productID){
        return (db.getQuantityOfProduct(productID) > 0);
    }

    public float getProductPrice(int productID){
        return db.getPriceOfProduct(productID);
    }

    public void dispenseProduct(int productID){
        String productName = db.getNameOfProduct(productID);
        listDispensedProducts.add(new Product(productName));
        db.decrementQuantityOfProduct(productID);
    }

    private int getNumberOfProductsInBay(){
        return listDispensedProducts.size();
    }

    private void removeProductsInBay(){
        listDispensedProducts.clear();
    }

    public void showDispensedProductsDialog(final Activity activity){
        LayoutInflater li = LayoutInflater.from(activity);
        final View returnView = li.inflate(R.layout.product_bay, null);

        final AlertDialog dispensedProductDialog = new AlertDialog.Builder(activity)
                .setView(returnView)
                .setTitle("Product Bay")
                .setPositiveButton("Remove Products", null)
                .setNegativeButton(android.R.string.cancel, null)
                .setCancelable(true)
                .create();

        dispensedProductDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button returnButton = dispensedProductDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                final TextView tvDispensedProducts  = (TextView) returnView.findViewById(R.id.tvDispensedProducts);

                //List and display returned coins
                String stringDispensedProducts = "";
                for(Product product : listDispensedProducts){
                    stringDispensedProducts = stringDispensedProducts + product.getName() + "\n\n";
                }
                tvDispensedProducts.setText(stringDispensedProducts);

                //Key Click Listeners
                returnButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(getNumberOfProductsInBay() > 0){
                            Toast.makeText(activity, "All products removed from product bay.",
                                    Toast.LENGTH_SHORT).show();
                            removeProductsInBay();
                        }

                        dispensedProductDialog.dismiss();
                    }
                });

            }
        });

        dispensedProductDialog.show();
    }

}
