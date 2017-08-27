package com.example.choi.knocktalk.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.choi.knocktalk.R;

/**
 * Created by choi on 17. 8. 20.
 */

public class Second extends Fragment {
    public Second() {
    }

    public static Second newInstance() {
        
        Bundle args = new Bundle();
        
        Second fragment = new Second();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = (LinearLayout) inflater.inflate(R.layout.second, container, false);
        return view;
    }
}