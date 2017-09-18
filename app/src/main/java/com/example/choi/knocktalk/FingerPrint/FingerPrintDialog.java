package com.example.choi.knocktalk.FingerPrint;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.choi.knocktalk.R;
import com.github.ajalt.reprint.core.AuthenticationFailureReason;
import com.github.ajalt.reprint.core.AuthenticationListener;
import com.github.ajalt.reprint.core.Reprint;

/**
 * Created by choi on 17. 8. 30.
 */

public class FingerPrintDialog extends AppCompatActivity{
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        /*WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        getWindow().setAttributes(layoutParams);*/
        setContentView(R.layout.activity_fingerprint);

        this.setFinishOnTouchOutside(false);    //화면이외의 영역에 터치할시에 꺼지는것 방지
        imageView = (ImageView)findViewById(R.id.fingerImage);
        imageView.setColorFilter(Color.RED);
        Reprint.initialize(getApplicationContext());
        Reprint.authenticate(new AuthenticationListener() {
            @Override
            public void onSuccess(int moduleTag) {
                Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_SHORT).show();
                imageView.setColorFilter(Color.GREEN);
                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setColorFilter(Color.BLACK);
                    }
                },2000);*/
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(600);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }

            @Override
            public void onFailure(AuthenticationFailureReason failureReason, boolean fatal, CharSequence errorMessage, int moduleTag, int errorCode) {
                Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_SHORT).show();
                imageView.setColorFilter(Color.RED);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setColorFilter(Color.BLACK);
                    }
                },2000);
            }
        });
    }
}
