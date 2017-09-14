package com.brahmachilakala.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by brahma on 14/09/17.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

//    @Before
//    public void init() {
//        mMainActivityActivityTestRule.getActivity()
//                .getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment_main, new MainFragment()).commit();
//    }

    @Test
    public void clickRecyclerViewItem_OpensRecipeDetailActivity() {

        onView(allOf(withId(R.id.rvRecipes), hasFocus())).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.tv_recipe_ingredients)).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.rvSteps), hasFocus())).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.tv_step_description)).check(matches(isDisplayed()));
    }
}
