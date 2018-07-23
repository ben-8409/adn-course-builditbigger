package com.udacity.gradle.builditbigger;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.udacity.gradle.builditbigger.tasks.JokeTask;
import com.udacity.gradle.builditbigger.tasks.OnJokeLoadedCallback;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestAsyncJokeTest {
    private CountingIdlingResource mIdlingResource;

    @Test
    public void testJoke() {
        //I know the IdlingResource should be managed in the production code, only registering
        // and unregistering should be done in the test.
        //Increment the idling resource
        new JokeTask(new OnJokeLoadedCallback() {
            @Override
            public void onDone(String joke) {
                Log.v("TestAsyncJokeTest", joke);
                assertNotNull(joke);
                assertTrue(joke.length() > 0);
            }
        }, mIdlingResource).execute();
    }

    @Before
    public void registerIdlingResource() {
        mIdlingResource = new CountingIdlingResource("LoadJoke");
        // To prove that the test fails, omit this call:
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
