package com.example.choi.knocktalk.Dialog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.choi.knocktalk.R;

/**
 * Created by choi on 17. 9. 27.
 */

public class Police_dialog  extends AppCompatActivity implements View.OnClickListener{
    private Button ok,not;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.police_dialog);
        setFinishOnTouchOutside(false);

        ok= (Button)findViewById(R.id.ok_btn);
        ok.setOnClickListener(this);
        not=(Button)findViewById(R.id.cancle_btn);
        not.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ok_btn:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:112"));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
                finish();
                break;
            case R.id.cancle_btn:
                finish();
                break;
        }
    }
}

