package com.udacity.gradle.builditbigger;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private JokeMakerAsyncTask task;
    @BindView(R.id.btn_joke) Button mJokeButton;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);
        mJokeButton.setOnClickListener(this);

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
                task = new JokeMakerAsyncTask(getActivity(), getView());
                task.execute();
                break;
            default:
                break;
        }
    }
}
