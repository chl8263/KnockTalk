package com.example.choi.knocktalk.Setting;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.choi.knocktalk.R;
import com.example.choi.knocktalk.Setting_Dialog.Lock_style_dialog;
import com.example.choi.knocktalk.SharedPreferences.Preference;

/**
 * Created by choi on 17. 9. 25.
 */

public class SettingActivity extends AppCompatActivity {
    LinearLayout openstyle;
    LinearLayout passwd_setting;
    private TextView textview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initstatusbar();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("setting");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            Drawable drawable = getDrawable(R.drawable.arrow_back);
            drawable.setTint(Color.WHITE);
            if (drawable != null) {
                actionBar.setHomeAsUpIndicator(drawable);
            }
        }

        textview = (TextView) findViewById(R.id.lock_style);
        openstyle = (LinearLayout) findViewById(R.id.set_open_style);
        openstyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Lock_style_dialog.class);
                startActivityForResult(intent, 0);
            }
        });
        int style = Preference.getPreferences(getApplicationContext(), "lock_style");
        if (style == 0) {
            textview.setText("지문");
        } else if (style == 1) {
            textview.setText("비밀번호");
        }
        passwd_setting = (LinearLayout) findViewById(R.id.passwd_setting);
        passwd_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Set_password.class);
                startActivity(intent);
            }
        });
    }

    private void initstatusbar() {
        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(Color.parseColor("#abbaab"));
            }
        } else getWindow().setStatusBarColor(Color.parseColor("#000"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            int style = Preference.getPreferences(getApplicationContext(), "lock_style");
            if (style == 0) {
                textview.setText("지문");
            } else if (style == 1) {
                textview.setText("비밀번호");
            }
        }
    }
}
