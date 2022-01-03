package com.example.android.twoactivities;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ActivityInputOutputTest {

    public static final String EDIT_TEXT_MAIN_MESSAGE = "this is a test";

    @Rule
    public ActivityTestRule testRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void buttonClickedActivityLaunch(){
        onView(withId(R.id.button_main)).perform(click());
        onView(withId(R.id.text_header)).check(matches(isDisplayed()));
        onView(withId(R.id.button_second)).perform(click());
        onView(withId(R.id.text_header_reply)).check(matches(isDisplayed()));
    }

    @Test
    public void textTypedButtonClicked(){
        onView(withId(R.id.editText_main)).perform(typeText(EDIT_TEXT_MAIN_MESSAGE));
        onView(withId(R.id.button_main)).perform(click());
        onView(withId(R.id.text_message)).check(matches(withText(EDIT_TEXT_MAIN_MESSAGE)));
    }


}
