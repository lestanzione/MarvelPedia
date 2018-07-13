package com.empire.android.marvelpedia.main;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.empire.android.marvelpedia.R;
import com.empire.android.marvelpedia.RecyclerViewItemCountAssertion;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onLaunchShouldHaveTwoOptions(){

        onView(withId(R.id.mainOptionsRecyclerView))
            .check(RecyclerViewItemCountAssertion.withItemCount(2));

    }

}