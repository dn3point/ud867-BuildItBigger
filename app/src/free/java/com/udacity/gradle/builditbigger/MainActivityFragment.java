package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import timber.log.Timber;

public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private JokeMakerAsyncTask mJokeMakeTask;
    private InterstitialAd mInterstitialAd;
    private AdRequest mAdRequest;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = root.findViewById(R.id.adView);
        mAdRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(mAdRequest);

        if (getActivity() != null) {
            mInterstitialAd = new InterstitialAd(getActivity());
            mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
            mInterstitialAd.loadAd(mAdRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    mInterstitialAd.loadAd(mAdRequest);
                    displayJoke();
                }
            });
        } else {
            Timber.d("getActivity() is null.");
        }

        Button jokeButton = root.findViewById(R.id.btn_joke);
        jokeButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mJokeMakeTask != null) {
            mJokeMakeTask.cancel(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_joke:
                showInterstitialAd();
                break;
            default:
                break;
        }
    }

    private void displayJoke() {
        mJokeMakeTask = new JokeMakerAsyncTask();
        mJokeMakeTask.execute(getActivity());
    }

    private void showInterstitialAd() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Timber.d("The interstitial wasn't loaded yet.");
            mInterstitialAd.loadAd(mAdRequest);
            displayJoke();
        }
    }
}
