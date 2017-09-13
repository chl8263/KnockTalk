package com.example.choi.knocktalk.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.choi.knocktalk.R;
import com.example.choi.knocktalk.Record_Video.Record_Refresh;

/**
 * Created by choi on 17. 8. 20.
 */

public class Second extends Fragment {
    private FloatingActionButton btn;
    private RecyclerView recyclerView;
    private Animation refresh_anim;
    private Record_Refresh record_refresh;
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
        View view = (RelativeLayout) inflater.inflate(R.layout.second, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.second_recycle);
        btn = (FloatingActionButton)view.findViewById(R.id.refreshBtn);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh_anim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.refresh_anim);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("secondBTN","OK");
                btn.startAnimation(refresh_anim);
                record_refresh = new Record_Refresh();
                record_refresh.start();
            }
        });
    }
}