package com.udacity.gradle.builditbigger;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.iamzhaoyuan.android.jokedisplay.JokeDisplayActivity;
import com.iamzhaoyuan.android.jokedisplay.JokeDisplayFragment;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.lang.ref.WeakReference;

import timber.log.Timber;

public class JokeMakerAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private WeakReference<Context> context;

    @Override
    protected String doInBackground(Context... params) {
        context = new WeakReference<>(params[0]);
        String joke = null;

        if (myApiService == null) {
            String rootUrl = "http://" + context.get().getString(R.string.ip_address) + ":8080/_ah/api/";
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(rootUrl)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }


        try {
            joke = myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            Timber.d(e, "Sth wrong when getting a joke.");
        }

        return joke;
    }

    @Override
    protected void onPostExecute(String joke) {
        Intent intent = new Intent(context.get(), JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayFragment.JOKE_KEY, joke);
        context.get().startActivity(intent);
    }
}
