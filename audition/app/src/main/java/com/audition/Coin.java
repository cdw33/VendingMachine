package com.audition;

import java.util.Random;

public class Coin extends VendingMachine{

    private float diameter; //mm
    private float width; //mm
    private float weight; //g

    private float value; //$USD

    public final float UNKNOWN_VALUE = -1.0f;

    Coin(){ //Generate random coin
        Random rand = new Random();

        diameter = rand.nextFloat();
        width = rand.nextFloat();
        weight = rand.nextFloat();

        value = UNKNOWN_VALUE; //unknown value
    }

    Coin(float d, float wi, float we){ //Create coin of unknown value
        diameter = d;
        width = wi;
        weight = we;

        value = UNKNOWN_VALUE; //unknown value
    }

    //Create coin with known value
    Coin(float d, float wi, float we, float v){
        diameter = d;
        width = wi;
        weight = we;
        value = v;
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
        final float tolerance = 0.05f;

        float allowableError = spec1 * tolerance;
        float actualError = Math.abs(spec1 - spec2);

        return (actualError < allowableError);
    }
}
