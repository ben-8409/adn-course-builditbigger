package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import com.udacity.gradle.builditbigger.tasks.JokeTask;
import com.udacity.gradle.builditbigger.tasks.OnJokeLoadedCallback;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class TestAsyncJokeTest {
    @Test
    public void testJoke() {
        new JokeTask(new OnJokeLoadedCallback() {
            @Override
            public void onDone(String joke) {
                assertNotNull(joke);
                assert(joke.length() > 0);
            }
        });
    }
}
