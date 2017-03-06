package protect.budgetwatch;

import android.os.SystemClock;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static protect.budgetwatch.Resources.childAtPosition;

/**
 * Created by sraap on 6-3-2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class BudgetSleepTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void BudgetSetup() {
        //Open budgets, check that Budgets is opened, check that there are no budgets
        SystemClock.sleep(1000);
        onView(allOf(childAtPosition(withId(R.id.list), 0), isDisplayed())).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Budgets")).check(matches(isDisplayed()));
        onView(withText("You don't have any budgets at the moment. Click the + (plus) button up top " +
                "to get started.\n\nBudget Watch lets you create budgets, then track spending " +
                "during the month.")).check(matches(isDisplayed()));
    }

    @Test
    public void createBudget() {
        String budgetName = "Test budget";
        String budgetMax = "250";

        SystemClock.sleep(1000);
        onView(allOf(childAtPosition(withId(R.id.list), 0), isDisplayed())).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Budgets")).check(matches(isDisplayed()));
        SystemClock.sleep(1000);
        onView(withId(R.id.action_add)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.budgetName)).perform(replaceText(budgetName), closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withId(R.id.value)).perform(replaceText(budgetMax), closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withId(R.id.saveButton)).perform(click());
        SystemClock.sleep(1000);
        onView(allOf(withId(R.id.budgetName),childAtPosition(childAtPosition(withId(R.id.list),0),0))).check(matches(withText(budgetName)));
    }
}
