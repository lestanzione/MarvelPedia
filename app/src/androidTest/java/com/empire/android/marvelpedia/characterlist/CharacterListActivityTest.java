package com.empire.android.marvelpedia.characterlist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.empire.android.marvelpedia.App;
import com.empire.android.marvelpedia.R;
import com.empire.android.marvelpedia.RecyclerViewItemCountAssertion;
import com.empire.android.marvelpedia.di.ApplicationComponent;
import com.empire.android.marvelpedia.di.DaggerApplicationComponent;
import com.empire.android.marvelpedia.di.MockNetworkModule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CharacterListActivityTest {

    @Rule
    public ActivityTestRule<CharacterListActivity> activityRule = new ActivityTestRule<>(CharacterListActivity.class, true, false);

    private MockWebServer server = new MockWebServer();

    @Before
    public void setUp() throws IOException {
        setUpServer();
        server.start();
    }

    @After
    public void cleanup() throws IOException {
        server.shutdown();
    }

    public void setUpServer() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(new MockNetworkModule(server))
                .build();

        ((App) InstrumentationRegistry.getTargetContext().getApplicationContext())
                .setApplicationComponent(applicationComponent);
    }

    @Test
    public void onLaunchWithoutComicIdShouldShowCharacterList() throws IOException, InterruptedException {

        server.enqueue(new MockResponse()
                .setBody(readFile("character_list_response.json")));

        activityRule.launchActivity(new Intent());

        onView(withId(R.id.charactersRecyclerView))
                .check(RecyclerViewItemCountAssertion.withItemCount(5));

        onView(withId(R.id.progressBar))
                .check(matches(not(isDisplayed())));

        assertEquals(1, server.getRequestCount());

    }

    @Test
    public void searchForNameShouldShowFilteredCharacterList() throws IOException, InterruptedException {

        server.enqueue(new MockResponse()
                .setBody(readFile("character_list_response.json")));

        server.enqueue(new MockResponse()
                .setBody(readFile("character_list_spider_response.json")));

        activityRule.launchActivity(new Intent());

        onView(withId(R.id.charactersRecyclerView))
                .check(RecyclerViewItemCountAssertion.withItemCount(5));

        onView(withId(R.id.progressBar))
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.searchMenuItem))
                .perform(click());

        onView(withId(android.support.design.R.id.search_src_text))
                .perform(typeText("Spider"));
        // OR
//        onView(isAssignableFrom(AutoCompleteTextView.class))
//                .perform(typeText("Spider"));

    }

    private String readFile(String fileName) throws IOException {
        InputStream stream = InstrumentationRegistry.getContext()
                .getAssets()
                .open(fileName);

        Source source = Okio.source(stream);
        BufferedSource buffer = Okio.buffer(source);

        return buffer.readUtf8();
    }

}