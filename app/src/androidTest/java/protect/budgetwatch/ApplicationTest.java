package protect.budgetwatch;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void openAppRotateScreen() {
        onView(withId(R.id.list)).check(matches(isDisplayed()));
        rotateScreen();
        onView(withId(R.id.list)).check(matches(isDisplayed()));
    }

    @Test
    public void setReceiptQuality() {
        //Check that app is open
        onView(withId(R.id.list)).check(matches(isDisplayed()));
        //Click on the settings icon in the top toolbar
        onView(withId(R.id.action_settings)).perform(click());
        //Check that settings is opened
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        //Click on the "Receipt Quality" option
        onView(withText("Receipt Quality")).perform(click());
        //Check that the option "40" is selected
        onView(withText("40")).check(matches(isChecked()));
        //Click on the option "60" ("Receipt Quality" closes automatically)
        onView(withText("60")).perform(click());
        //Click on the "Receipt Quality" option
        onView(withText("Receipt Quality")).perform(click());
        //Check that the option "60" is selected
        onView(withText("60")).check(matches(isChecked()));
        //Click on the option "40" (To reset the app for the next run of this test)
        onView(withText("40")).perform(click());
    }

    private void rotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;

        Activity activity = activityRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}