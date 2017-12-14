package com.udacity.gradle.builditbigger;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.iamzhaoyuan.android.jokedisplay.JokeDisplayActivity;
import com.iamzhaoyuan.android.jokedisplay.JokeDisplayFragment;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import timber.log.Timber;

public class JokeMakerAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private ProgressBar mProgressBar;
    private Button mButton;

    private Context mContext;
    private View mView;

    public JokeMakerAsyncTask(Context context, View view) {
        mContext = context;
        mView = view;
    }

    @Override
    protected void onPreExecute() {
        mButton = mView.findViewById(R.id.btn_joke);
        mProgressBar = mView.findViewById(R.id.progress_bar);

        mButton.setClickable(false);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... params) {
        // TODO null check for context
        String joke = null;

        if (myApiService == null) {
            String rootUrl = "http://" + mContext.getString(R.string.ip_address) + ":8080/_ah/api/";
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(rootUrl)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
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
        Intent intent = new Intent(mContext, JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayFragment.JOKE_KEY, joke);
        mProgressBar.setVisibility(View.INVISIBLE);
        mButton.setClickable(true);
        mContext.startActivity(intent);
    }

}
