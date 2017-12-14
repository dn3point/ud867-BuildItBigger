package com.udacity.gradle.builditbigger;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private JokeMakerAsyncTask task;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        Button jokeButton = root.findViewById(R.id.btn_joke);
        jokeButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (task != null) {
            task.cancel(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_joke:
                task = new JokeMakerAsyncTask();
                task.execute(getActivity());
                break;
            default:
                break;
        }
    }
}
