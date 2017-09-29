package com.example.choi.knocktalk.Dialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.choi.knocktalk.R;

/**
 * Created by choi on 17. 9. 24.
 */

public class First_OK_Dialog extends AppCompatActivity implements View.OnClickListener{
    private TextView name;
    private Button ok,not;
    private String filename;
    private final String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/KNOCK_TALK";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first_ok);
        setFinishOnTouchOutside(false);

        Intent intent = getIntent();
        filename = intent.getStringExtra("name");
        name=(TextView)findViewById(R.id.file_name);
        name.setText(filename);
        ok= (Button)findViewById(R.id.ok_btn);
        ok.setOnClickListener(this);
        not=(Button)findViewById(R.id.cancle_btn);
        not.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ok_btn:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(sdPath+"/"+filename+".avi");
                intent.setDataAndType(uri,"video/*");
                view.getContext().startActivity(intent);
                finish();
                break;
            case R.id.cancle_btn:
                finish();
                break;
        }
    }
}
