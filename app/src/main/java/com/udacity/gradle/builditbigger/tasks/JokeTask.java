package com.udacity.gradle.builditbigger.tasks;

import android.os.AsyncTask;
import android.support.test.espresso.idling.CountingIdlingResource;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class JokeTask extends AsyncTask<Void, Void, String> {
    private final OnJokeLoadedCallback mCallback;
    private CountingIdlingResource mCountingIdlingResource;
    private MyApi mApiService;

    public JokeTask(OnJokeLoadedCallback callback) {
        mCallback = callback;
    }

    public JokeTask(OnJokeLoadedCallback onJokeLoadedCallback, CountingIdlingResource countingResource) {
        mCallback = onJokeLoadedCallback;
        mCountingIdlingResource = countingResource;

    }

    @Override
    protected String doInBackground(Void... voids) {
        if (mCountingIdlingResource != null) mCountingIdlingResource.increment();
        if(mApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            mApiService = builder.build();
        }

        try {
            return mApiService.sayJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if(mCallback != null) {
            mCallback.onDone(s);
        }
        if (mCountingIdlingResource != null) {
            mCountingIdlingResource.decrement();
        }
    }
}
