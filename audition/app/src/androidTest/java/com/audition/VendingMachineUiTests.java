package com.audition;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class VendingMachineUiTests {
    @Rule
    public ActivityTestRule<VendingMachine> mActivityRule =
            new ActivityTestRule<>(VendingMachine.class);

    //Test purchasing a product
    @Test
    public void buyProduct() {

        String expectedString = "Candy\n\n";

        //Insert $0.65
        onView(withId(R.id.buttonAddCoin)).perform(click());
        onView(withId(R.id.buttonQuarter)).perform(click());

        onView(withId(R.id.tvLCD)).check(matches(withText("$0.25")));

        onView(withId(R.id.buttonAddCoin)).perform(click());
        onView(withId(R.id.buttonQuarter)).perform(click());

        onView(withId(R.id.tvLCD)).check(matches(withText("$0.50")));

        onView(withId(R.id.buttonAddCoin)).perform(click());
        onView(withId(R.id.buttonDime)).perform(click());

        onView(withId(R.id.tvLCD)).check(matches(withText("$0.60")));

        onView(withId(R.id.buttonAddCoin)).perform(click());
        onView(withId(R.id.buttonNickel)).perform(click());

        onView(withId(R.id.tvLCD)).check(matches(withText("$0.65")));

        //Select Product
        onView(withId(R.id.buttonProduct3)).perform(click());

        //Ensure Display updates correctly
        SystemClock.sleep(500);
        onView(withId(R.id.tvLCD)).check(matches(withText("THANK YOU")));

        openProductRetrievalBay(expectedString);
    }

    //Test selecting product with not enough money inserted
    @Test
    public void productSoldOut() {
        //Insert $0.35
        onView(withId(R.id.buttonAddCoin)).perform(click());
        onView(withId(R.id.buttonQuarter)).perform(click());

        onView(withId(R.id.tvLCD)).check(matches(withText("$0.25")));

        onView(withId(R.id.buttonAddCoin)).perform(click());
        onView(withId(R.id.buttonDime)).perform(click());

        onView(withId(R.id.tvLCD)).check(matches(withText("$0.35")));

        //Select an item which is sold out
        onView(withId(R.id.buttonProduct4)).perform(click()); //Gum - Sold Out

        //Ensure display states item is sold out
        onView(withId(R.id.tvLCD)).check(matches(withText("SOLD OUT")));

        //Ensure display returns to current total
        SystemClock.sleep(1200);
        onView(withId(R.id.tvLCD)).check(matches(withText("$0.35")));
    }

    //Test attempting to buy a sold out item
    @Test
    public void notEnoughMoney() {
        //Insert $0.25
        onView(withId(R.id.buttonAddCoin)).perform(click());
        onView(withId(R.id.buttonQuarter)).perform(click());

        onView(withId(R.id.tvLCD)).check(matches(withText("$0.25")));

        //Attempt to purchase an item costing $1.00
        onView(withId(R.id.buttonProduct1)).perform(click());

        //Ensure the display shows the correct item price
        onView(withId(R.id.tvLCD)).check(matches(withText("$1.00")));

        //Ensure the display returns to the current total
        SystemClock.sleep(1200);
        onView(withId(R.id.tvLCD)).check(matches(withText("$0.25")));
    }

    //Test Coin Return
    @Test
    public void testCoinReturn() {

        String expectedString = "Weight: 5.6700g, Diameter: 24.2600mm" +
                ",\nWidth: 1.7500mm, Value: $0.25\n\n";

        //Insert a quarter in coin slot
        onView(withId(R.id.buttonAddCoin)).perform(click());
        onView(withId(R.id.buttonQuarter)).perform(click());
        onView(withId(R.id.tvLCD)).check(matches(withText("$0.25")));

        //Press Coin Return button
        onView(withId(R.id.buttonReturnCoins)).perform(click());

        //Verify Display resets
        onView(withId(R.id.tvLCD)).check(matches(withText("INSERT COIN")));

        openCoinReturnBay(expectedString);
    }

    //Test Coin Return
    @Test
    public void testMakeChange() {

        String expectedChangeString = "Weight: 5.0000g, Diameter: 21.2100mm,\n" +
                "Width: 1.9500mm, Value: $0.05\n\nWeight: 2.2680g, Diameter: 17.9100mm,\n" +
                "Width: 1.3500mm, Value: $0.10\n\n";

        String expectedProductString = "Chips\n\n";

        //Insert $0.65
        onView(withId(R.id.buttonAddCoin)).perform(click());
        onView(withId(R.id.buttonQuarter)).perform(click());

        onView(withId(R.id.tvLCD)).check(matches(withText("$0.25")));

        onView(withId(R.id.buttonAddCoin)).perform(click());
        onView(withId(R.id.buttonQuarter)).perform(click());

        onView(withId(R.id.tvLCD)).check(matches(withText("$0.50")));

        onView(withId(R.id.buttonAddCoin)).perform(click());
        onView(withId(R.id.buttonDime)).perform(click());

        onView(withId(R.id.tvLCD)).check(matches(withText("$0.60")));

        onView(withId(R.id.buttonAddCoin)).perform(click());
        onView(withId(R.id.buttonNickel)).perform(click());

        onView(withId(R.id.tvLCD)).check(matches(withText("$0.65")));

        //Select $0.50 Product
        onView(withId(R.id.buttonProduct2)).perform(click());

        //Check that extra coins have been returned
        openCoinReturnBay(expectedChangeString);

        //Check that product was delivered
        openProductRetrievalBay(expectedProductString);
    }

    public void openCoinReturnBay(String expectedString){
        //Open Coin Return Bay
        onView(withId(R.id.buttonCoinReturnBay)).perform(click());

        //Check that quarter has been returned
        onView(withId(R.id.tvReturnedCoins)).check(matches(withText(expectedString)));

        //Remove coins from bay
        onView(withText("Remove Coins")).perform(click());

        //Open Coin Return Bay again
        onView(withId(R.id.buttonCoinReturnBay)).perform(click());

        //Ensure bay is empty
        onView(withId(R.id.tvReturnedCoins)).check(matches(withText("")));

        //Close bay
        onView(withText("Close")).perform(click());
    }

    public void openProductRetrievalBay(String expectedString){
        //Open Product Retrieval Bay
        onView(withId(R.id.buttonProductBay)).perform(click());

        //Check that correct product has been dispensed
        onView(withId(R.id.tvDispensedProducts)).check(matches(withText(expectedString)));

        //Remove product from bay
        onView(withText("Remove Products")).perform(click());

        //Open Product Retrieval Bay again
        onView(withId(R.id.buttonProductBay)).perform(click());

        //Ensure bay is empty
        onView(withId(R.id.tvDispensedProducts)).check(matches(withText("")));

        //Close bay
        onView(withText("Close")).perform(click());
    }
}
