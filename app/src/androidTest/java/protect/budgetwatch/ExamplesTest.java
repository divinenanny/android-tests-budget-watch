package protect.budgetwatch;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static protect.budgetwatch.Resources.childAtPosition;

/**
 * Created by sraap on 24-1-2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExamplesTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule(MainActivity.class);

    //Usability: Orientation (portrait to landscape and back)
    //Checks if rotation does not crash the app
    @Test
    public void rotateScreenTwice() {
        onView(withId(R.id.list)).check(matches(isDisplayed()));
        rotateScreen();
        onView(withId(R.id.list)).check(matches(isDisplayed()));
        rotateScreen();
        onView(withId(R.id.list)).check(matches(isDisplayed()));
    }

    //Usability: Navigation
    //Opens settings, checks Receipt Quality settings
    @Test
    public void viewSettings() {
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
        //Click on the option "40" ("Receipt Quality" closes automatically)
        onView(withText("40")).perform(click());
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


    //Usability: Navigation
    //Browses through Budgets and Transactions to check information displayed on screen and back buttons
    @Test
    public void viewBudgetsTransactions() {
        //Check if "Budgets" is displayed in menu
        onView(allOf(withId(R.id.menu),
                withText("Budgets"),
                childAtPosition(
                        childAtPosition(
                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                1),
                        0),
                isDisplayed())).check(matches(isDisplayed()));
        //Check if "Transactions" is displayed in menu
        onView(allOf(withId(R.id.menu),
                withText("Transactions"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed())).check(matches(isDisplayed()));
        //Click on Budgets
        onView(allOf(childAtPosition(withId(R.id.list),0),isDisplayed())).perform(click());
        //Check that help text for no budgets is displayed
        onView(allOf(withId(R.id.helpText), withText("You don't have any budgets at the moment. Click the + (plus) button up top to get started.\n\nBudget Watch lets you create budgets, then track spending during the month."),
                childAtPosition(
                        childAtPosition(
                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                1),
                        0))).check(matches(isDisplayed()));
        //Click back in toolbar
        onView(withContentDescription("Navigate up")).perform(click());
        //Check main menu is displayed
        onView(withId(R.id.list)).check(matches(isDisplayed()));
        //Click on Budgets
        onView(allOf(childAtPosition(withId(R.id.list),0),isDisplayed())).perform(click());
        //Click on Android back button
        pressBack();
        //Check main menu is displayed
        onView(withId(R.id.list)).check(matches(isDisplayed()));
        //Click on Transactions
        onView(allOf(childAtPosition(withId(R.id.list),1),isDisplayed())).perform(click());
        //Check that help text for no expense transactions is displayed
        onView(allOf(withId(R.id.helpText), withText("You don't have any expense transactions at the moment. Click the + (plus) button up top to get started."),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.pager),
                                0),
                        0))).check(matches(isDisplayed()));
        //Click Revenue in toolbar
        onView(withText("Revenues")).perform(click());
        //Check that help text for no revenue transactions is displayed
        onView(allOf(withId(R.id.helpText), withText("You don't have any revenue transactions at the moment. Click the + (plus) button up top to get started."),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.pager),
                                1),
                        0))).check(matches(isDisplayed()));
        //Click back in toolbar
        onView(withContentDescription("Navigate up")).perform(click());
        //Check main menu is displayed
        onView(withId(R.id.list)).check(matches(isDisplayed()));
        //Click on Transactions
        onView(allOf(childAtPosition(withId(R.id.list),1),isDisplayed())).perform(click());
        //Click on Android back button
        pressBack();
        //Check main menu is displayed
        onView(withId(R.id.list)).check(matches(isDisplayed()));
    }

    //Supporting method to rotate the screen, used in the rotateScreenTwice test
    private void rotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;

        Activity activity = activityRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
