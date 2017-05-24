package com.audition;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for Coin Class
 */
public class CoinTest {
    final float testDiameter = 5.0f;
    final float testWeight = 21.21f;
    final float testWidth = 1.95f;
    final float testValue = 0.05f;
    final int testKey = 4;

    final float unknownValue = -1.0f;

    Coin testCoinWithKey = new Coin(testKey, testDiameter, testWidth, testWeight, testValue);
    Coin testCoinWithValue = new Coin(testDiameter, testWidth, testWeight, testValue);
    Coin testCoinUnknownValue = new Coin(testDiameter, testWidth, testWeight);
    Coin testCoinRandom = new Coin();

    final double delta = 0;

    @Test
    public void getKey() throws Exception {
        assertEquals(testKey, testCoinWithKey.getKey(), delta);
    }

    @Test
    public void getDiameter() throws Exception {
        assertEquals(testDiameter, testCoinWithValue.getDiameter(), delta);
    }

    @Test
    public void getWeight() throws Exception {
        assertEquals(testWeight, testCoinWithValue.getWeight(), delta);
    }

    @Test
    public void getWidth() throws Exception {
        assertEquals(testWidth, testCoinWithValue.getWidth(), delta);
    }

    @Test
    public void getValue() throws Exception {
        assertEquals(testValue, testCoinWithValue.getValue(), delta);
        assertEquals(unknownValue, testCoinUnknownValue.getValue(), delta);
        assertEquals(unknownValue, testCoinRandom.getValue(), delta);
    }

    @Test
    public void setValue() throws Exception {
        float testSetValue = 0.50f;

        testCoinWithValue.setValue(testSetValue);
        testCoinRandom.setValue(testSetValue);

        assertEquals(testSetValue, testCoinWithValue.getValue(), delta);
        assertEquals(testSetValue, testCoinRandom.getValue(), delta);
    }

    @Test
    public void setKey() throws Exception {
        int testSetKey = 3;

        testCoinWithKey.setKey(testSetKey);

        assertEquals(testSetKey, testCoinWithKey.getKey(), delta);
    }

    @Test
    public void isSame() throws Exception {
        Coin idealCoin = new Coin(5.0f, 21.21f, 1.95f);
        Coin exactSameCoin = new Coin(5.0f, 21.21f, 1.95f);
        Coin inToleranceTestCoin = new Coin(4.76f, 21.15f, 1.86f);
        Coin outOfToleranceTestCoin = new Coin(4.75f, 21.15f, 1.86f);
        Coin differentCoin = new Coin(3.2f, 18.34f, 1.65f);

        assertTrue(idealCoin.isSame(exactSameCoin));
        assertTrue(idealCoin.isSame(inToleranceTestCoin));
        assertFalse(idealCoin.isSame(outOfToleranceTestCoin));
        assertFalse(idealCoin.isSame(differentCoin));
    }
}