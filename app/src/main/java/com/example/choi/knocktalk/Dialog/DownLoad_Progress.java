package com.example.choi.knocktalk.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.choi.knocktalk.Main.MainActivity;
import com.example.choi.knocktalk.R;

/**
 * Created by choi on 17. 9. 24.
 */

public class DownLoad_Progress extends AppCompatActivity {
    public DownLoad_Progress() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.down_progress);
        setFinishOnTouchOutside(false);

        Intent intent = getIntent();
        String getString = intent.getStringExtra("download");
        Handler handler = new Handler();
        if (getString.equals("one")) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            }, 2000);
        } else if (getString.equals("all")) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            }, 3000);
        } else if (getString.equals("ten")) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("aaa", 1);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }

    }
}
