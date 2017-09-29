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
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.choi.knocktalk.Dialog.Police_dialog;
import com.example.choi.knocktalk.FingerPrint.FingerPrintDialog;
import com.example.choi.knocktalk.Interface.Refresh_listener;
import com.example.choi.knocktalk.R;
import com.example.choi.knocktalk.Setting.PassWordActivity;
import com.example.choi.knocktalk.SharedPreferences.Preference;

/**
 * Created by choi on 17. 8. 20.
 */

public class Third extends Fragment implements View.OnClickListener {
    private FloatingActionButton floatingActionButton;
    private ImageButton policeBtn;
    private ImageButton capture;
    private ImageButton speaker;
    private ImageButton mic;
    private RelativeLayout rtspPlayView;
    private Refresh_listener refresh_listener;
    private int index_mic = 1;

    public Third() {
    }

    public Third(Refresh_listener refresh_listener) {
        this.refresh_listener = refresh_listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third, container, false);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingButton);
        policeBtn = (ImageButton) view.findViewById(R.id.police);
        //capture = (ImageButton) view.findViewById(R.id.capture);
        mic = (ImageButton) view.findViewById(R.id.mic);
        rtspPlayView = (RelativeLayout) view.findViewById(R.id.rtsp);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        floatingActionButton.setOnClickListener(this);
        policeBtn.setOnClickListener(this);
        mic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingButton:
                Log.e("asd", "asdasdasd");
                int style = Preference.getPreferences(view.getContext(), "lock_style");
                if (style == 0) {
                    Intent intent = new Intent(getActivity(), FingerPrintDialog.class);
                    startActivity(intent);
                } else if (style == 1) {
                    Intent intent = new Intent(getActivity(), PassWordActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.police:
                Intent intent = new Intent(getContext(), Police_dialog.class);
                getContext().startActivity(intent);
                break;
            case R.id.mic:
                if (index_mic == 1) {
                    mic.setImageResource(R.drawable.mic_no);
                    refresh_listener.mic_stop();
                    index_mic = 2;
                } else if (index_mic == 2) {
                    mic.setImageResource(R.drawable.mic_ok);
                    refresh_listener.mic_go();
                    index_mic = 1;
                }
                break;

        }
    }
}



































