package com.iamzhaoyuan.android.jokedisplay;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 */
public class JokeDisplayFragment extends Fragment {

    public static final String JOKE_KEY = "joke_key";

    private TextView mTextview;

    public JokeDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_joke_display, container, false);
        // TODO Replace by Butter Knife
        mTextview = rootView.findViewById(R.id.joke_text_view);
        Intent intent = getActivity().getIntent();
        String joke = null;
        if (intent != null) {
            joke = intent.getStringExtra(JOKE_KEY);
        }
        if (joke != null && !joke.isEmpty()) {
            mTextview.setText(joke);
        } else {
            mTextview.setText(R.string.msg_no_joke);
            Timber.d("No joke passed from intent");
        }

        return rootView;
    }

}
