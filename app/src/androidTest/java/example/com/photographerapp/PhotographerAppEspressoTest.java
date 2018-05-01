package example.com.photographerapp;

import android.provider.MediaStore;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.EspressoKey;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.intent.Intents.intended;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.KeyEvent;

import junit.framework.AssertionFailedError;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;




@RunWith(AndroidJUnit4.class)
public class PhotographerAppEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

//    @Rule
//    public IntentsTestRule mIntentsRule = new IntentsTestRule<>(PhotographerAppEspressoTest.class);

    @Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.
//        onView(withId(R.id.takePhotoButton))
//                .perform(click());
//                .perform(typeText("HELLO"), closeSoftKeyboard());
//        onView(withId(R.id.send_message)).perform(click());
        // Check that the text was changed.
        //onView(withId(R.id.test_message)).check(matches(withText("HELLO")));
        //onView(withContentDescription("Navigate up")).perform(click());
        //intended(hasAction(equalTo(MediaStore.ACTION_IMAGE_CAPTURE)));

    }

    @Test
    public void TestFilter(){
        onView( withId(R.id.btnFilter)).perform(click() );

        //onView( withId(R.id.startDatePickBtn) ).perform( click() );

        //onView( withId(R.id.startDatePickBtn) ).perform(typeText("30/04/18"), closeSoftKeyboard());
        //onView( withId(R.id.searchFromDate) ).perform(typeText("01/01/18"), closeSoftKeyboard());
        onView( withId(R.id.btnSearch)).perform(click() );
        for(int i = 0; i <=5; i++){
            Log.d("testing loop","loop: " + i);
            onView(withId(R.id.btnRight)).perform(click());
        }

    }
}