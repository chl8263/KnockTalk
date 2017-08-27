package com.example.choi.knocktalk.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choi.knocktalk.R;

/**
 * Created by choi on 17. 8. 20.
 */

public class Third extends Fragment {
    public Third() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third, container, false);
        return view;
    }
}
