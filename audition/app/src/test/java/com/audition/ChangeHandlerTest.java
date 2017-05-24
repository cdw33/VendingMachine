package com.audition;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Chris on 5/24/2017.
 */
public class ChangeHandlerTest {



    Coin testCoin1 = new Coin();
    Coin testCoin2 = new Coin();
    Coin testCoin3 = new Coin();

    @Test
    public void getListInsertedCoins() throws Exception {
        //Build expected list
        ArrayList<Coin> testExpectedList = new ArrayList<>();
        testExpectedList.add(testCoin1);
        testExpectedList.add(testCoin2);
        testExpectedList.add(testCoin3);

        //Build list for test
        ChangeHandler ch = new ChangeHandler(null, null);
        ch.addCoinToInsertedList(testCoin1);
        ch.addCoinToInsertedList(testCoin2);
        ch.addCoinToInsertedList(testCoin3);

        ArrayList<Coin> testList = ch.getListInsertedCoins();

        for(int i=0; i<testList.size(); i++){
            assertTrue(testList.get(i).isSame(testExpectedList.get(i)));
        }
    }

    @Test
    public void isCoinSlotEmpty() throws Exception {
        ChangeHandler ch = new ChangeHandler(null, null);

        ch.clearInsertedCoinsList();

        assertTrue(ch.isCoinSlotEmpty());
    }

    @Test
    public void getSumOfInsertedCoins() throws Exception {
        final float expectedSum = 4.65f;

        testCoin1.setValue(1.0f);
        testCoin2.setValue(3.5f);
        testCoin3.setValue(0.15f);

        ChangeHandler ch = new ChangeHandler(null, null);

        ch.addCoinToInsertedList(testCoin1);
        ch.addCoinToInsertedList(testCoin2);
        ch.addCoinToInsertedList(testCoin3);

        assertEquals(expectedSum, ch.getSumOfInsertedCoins(), 0);
    }
}