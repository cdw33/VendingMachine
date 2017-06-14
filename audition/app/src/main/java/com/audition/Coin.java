package com.audition;

import java.util.Random;

// This class outlines a Coin object. When a coin is inserted into the machine, its specifications are
// measures and stored in this object. Then the new coin can be checked against known coin specs
// to determine its value and/or validity

public class Coin {

    private float diameter; //mm
    private float width; //mm
    private float weight; //g

    private float value; //$USD

    private int key; //DB Key

    public final float UNKNOWN_VALUE = -1.0f;

    Coin(){ //Generate random coin
        Random rand = new Random();

        diameter = rand.nextFloat() * 15;
        width    = rand.nextFloat() * 2;
        weight   = rand.nextFloat() * 10;

        value = UNKNOWN_VALUE; //unknown value
        key   = 0;
    }

    Coin(float coinWeight, float coinDiameter, float coinWidth){ //Create coin of unknown value
        weight   = coinWeight;
        diameter = coinDiameter;
        width    = coinWidth;

        value = UNKNOWN_VALUE; //unknown value
        key   = 0;
    }

    //Create coin with known value
    Coin(float coinWeight, float coinDiameter, float coinWidth, float coinValue){
        weight   = coinWeight;
        diameter = coinDiameter;
        width    = coinWidth;
        value    = coinValue;
        key      = 0;
    }

    Coin(int key, float coinWeight, float coinDiameter, float coinWidth, float coinValue){
        this.key = key;
        weight   = coinWeight;
        diameter = coinDiameter;
        width    = coinWidth;
        value    = coinValue;
    }

    Coin(Coin copyCoin){
        this.weight   = copyCoin.getWeight();
        this.diameter = copyCoin.getDiameter();
        this.width    = copyCoin.getWidth();
        this.value    = copyCoin.getValue();
        this.key      = copyCoin.getKey();
    }

    public float getDiameter() {
        return diameter;
    }

    public float getWeight() {
        return weight;
    }

    public float getWidth() {
        return width;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    //Compare 2 coins for equivalent diameter, weight, and width
    //Can be used to compare inserted coin specs against known coin specs
    public boolean isSame(Coin compareCoin){
        if( isWithinTolerance(this.diameter, compareCoin.diameter) &&
                isWithinTolerance(this.weight, compareCoin.weight) &&
                isWithinTolerance(this.width, compareCoin.width) ){
            return true;
        }

        return false;
    }

    //Returns true if given measurements are within the allowable tollerance
    private boolean isWithinTolerance(float spec1, float spec2){
        //Measurement tolerance percentage, coin can be +/- %tolerance of exact coin specs
        //Currently %5, may be lowered after testing
        final float TOLERANCE = 0.05f;

        float allowableError = spec1 * TOLERANCE;
        float actualError = Math.abs(spec1 - spec2);

        return (actualError < allowableError);
    }
}
