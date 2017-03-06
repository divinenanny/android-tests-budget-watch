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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static protect.budgetwatch.Resources.childAtPosition;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class BudgetTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void BudgetSetup() {
        //Open budgets, check that Budgets is opened, check that there are no budgets
        onView(allOf(childAtPosition(withId(R.id.list), 0), isDisplayed())).perform(click());
        onView(withText("Budgets")).check(matches(isDisplayed()));
        onView(withText("You don't have any budgets at the moment. Click the + (plus) button up top " +
                "to get started.\n\nBudget Watch lets you create budgets, then track spending " +
                "during the month.")).check(matches(isDisplayed()));
    }

    @Test
    public void createBudget() {
        String budgetName = "Test budget";
        String budgetMax = "250";

        onView(allOf(childAtPosition(withId(R.id.list), 0), isDisplayed())).perform(click());
        onView(withText("Budgets")).check(matches(isDisplayed()));
        onView(withId(R.id.action_add)).perform(click());
        onView(withId(R.id.budgetName)).perform(replaceText(budgetName), closeSoftKeyboard());
        onView(withId(R.id.value)).perform(replaceText(budgetMax), closeSoftKeyboard());
        onView(withId(R.id.saveButton)).perform(click());
        onView(allOf(withId(R.id.budgetName),childAtPosition(childAtPosition(withId(R.id.list),0),0))).check(matches(withText(budgetName)));
    }

    @Test
    public void deleteBudgets() {
        onView(allOf(childAtPosition(withId(R.id.list), 0), isDisplayed())).perform(click());
        onView(withText("Budgets")).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.budgetName),childAtPosition(childAtPosition(withId(R.id.list),0),0))).perform(longClick());
        onView(allOf(withId(android.R.id.title), withText("Edit"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.action_edit), withContentDescription("Edit"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.action_delete), withContentDescription("Delete"), isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.button1), withText("Confirm"),
                        withParent(allOf(withId(R.id.buttonPanel),
                                withParent(withId(R.id.parentPanel)))),
                        isDisplayed())).perform(click());
        onView(withText("Budgets")).check(matches(isDisplayed()));
    }

    @Test
    public void cancelCreateBudget() {
        String budgetName = "Test budget";
        String budgetMax = "250";

        onView(allOf(childAtPosition(withId(R.id.list), 0), isDisplayed())).perform(click());
        onView(withText("Budgets")).check(matches(isDisplayed()));
        onView(withId(R.id.action_add)).perform(click());
        //check text
        onView(withId(R.id.budgetName)).perform(replaceText(budgetName), closeSoftKeyboard());
        //check value
        onView(withId(R.id.value)).perform(replaceText(budgetMax), closeSoftKeyboard());
        onView(withId(R.id.budgetName)).perform(replaceText(budgetName), closeSoftKeyboard());
        onView(withId(R.id.value)).perform(replaceText(budgetMax), closeSoftKeyboard());
        onView(withId(R.id.saveButton)).perform(click());
        onView(allOf(withId(R.id.budgetName),childAtPosition(childAtPosition(withId(R.id.list),0),0))).check(matches(withText(budgetName)));
    }

    //test 1
    //Click op de +
    //Check that Grocery en 100 zijn de placeholders
    //Type in beide velden
    //Click cancel
    //Check dat geen budget is gemaakt

    //test 2
    //Click op de +
    //Type in alleen naam veld
    //Click save
    //Check voor Toast "Budget is empty"
    //Click cancel

    //test 3
    //Click op de +
    //Type in alleen budget veld
    //Click save
    //check dat er niks gebeurd
    //Click cancel

    //test 4
    //Click op de +
    //Type in beide velden
    //CLick save
    //Check dat budget is aangemaakt

    //Test 5
    //Click op de +
    //Type in beide velden
    //CLick save
    //Check dat budget is aangemaakt

}
