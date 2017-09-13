package com.example.choi.knocktalk.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choi.knocktalk.FingerPrint.FingerPrintDialog;
import com.example.choi.knocktalk.R;

/**
 * Created by choi on 17. 8. 20.
 */

public class Third extends Fragment{
    private FloatingActionButton floatingActionButton;
    public Third() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third, container, false);
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.floatingButton);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("asd","asdasdasd");
                Intent intent = new Intent(getActivity(),FingerPrintDialog.class);
                startActivity(intent);
            }
        });
    }
}
