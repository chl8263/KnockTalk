package com.example.choi.knocktalk.Loading;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.choi.knocktalk.Main.MainActivity;
import com.example.choi.knocktalk.R;

/**
 * Created by choi on 17. 8. 20.
 */

public class LoadingActivity extends AppCompatActivity {
    private TextView Knock,Talk;
    private Handler handler,handler2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        Knock = (TextView)findViewById(R.id.Knock);
        Talk = (TextView)findViewById(R.id.Talk);
        Animation Knock_anim = AnimationUtils.loadAnimation(this,R.anim.lodanim);
        Knock.startAnimation(Knock_anim);

        Talk.setVisibility(View.INVISIBLE);
        handler = new Handler();
        handler2 = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Talk.setVisibility(View.VISIBLE);
                Animation Talk_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.lodanim2);
                Talk.startAnimation(Talk_anim);
            }
        },800);
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                LoadingActivity.this.finish();
            }
        },1500);

    }

}
