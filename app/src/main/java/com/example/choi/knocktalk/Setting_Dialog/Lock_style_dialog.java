package com.example.choi.knocktalk.Setting_Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.choi.knocktalk.R;
import com.example.choi.knocktalk.SharedPreferences.Preference;

/**
 * Created by choi on 17. 9. 25.
 */

public class Lock_style_dialog extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton pringerfrint, password;
    private Button OK, Close;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_lockstyle);
        setFinishOnTouchOutside(false);

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);

        pringerfrint = (RadioButton) findViewById(R.id.finger);
        password = (RadioButton) findViewById(R.id.password);
        OK = (Button) findViewById(R.id.OK);
        Close = (Button) findViewById(R.id.close);
        int style = Preference.getPreferences(getApplicationContext(), "lock_style");
        if (style == 0) {
            pringerfrint.setChecked(true);
            password.setChecked(false);
        } else if (style == 1) {
            pringerfrint.setChecked(false);
            password.setChecked(true);
        }
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radiobtnId = radioGroup.getCheckedRadioButtonId();
                View radiobutton = radioGroup.findViewById(radiobtnId);
                int index = radioGroup.indexOfChild(radiobutton);
                Preference.setPreferances(getApplicationContext(), "lock_style", index);
                setResult(100);
                finish();
            }
        });
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}