package com.example.choi.knocktalk.Setting;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choi.knocktalk.R;
import com.example.choi.knocktalk.SharedPreferences.Preference;

import java.util.ArrayList;

/**
 * Created by choi on 17. 9. 25.
 */

public class Set_password extends AppCompatActivity implements View.OnTouchListener {
    private ImageButton l1, l2, l3, l4, l5, l6, l7, l8, l9, l0, lb;
    private ImageView firstpw, secondpw, thirdpw, fourthpw;
    private ArrayList<Integer> pw;
    private ArrayList<Integer> pw_check;
    private TextView talk;
    private ImageView locked;
    String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_password);
        initstatusbar();
        init();
    }

    private void init() {
        pw = new ArrayList<Integer>();
        locked = (ImageView) findViewById(R.id.locked);
        talk = (TextView) findViewById(R.id.talk);
        l1 = (ImageButton) findViewById(R.id.one);
        l1.setOnTouchListener(this);
        l2 = (ImageButton) findViewById(R.id.two);
        l2.setOnTouchListener(this);
        l3 = (ImageButton) findViewById(R.id.three);
        l3.setOnTouchListener(this);
        l4 = (ImageButton) findViewById(R.id.four);
        l4.setOnTouchListener(this);
        l5 = (ImageButton) findViewById(R.id.five);
        l5.setOnTouchListener(this);
        l6 = (ImageButton) findViewById(R.id.six);
        l6.setOnTouchListener(this);
        l7 = (ImageButton) findViewById(R.id.seven);
        l7.setOnTouchListener(this);
        l8 = (ImageButton) findViewById(R.id.eight);
        l8.setOnTouchListener(this);
        l9 = (ImageButton) findViewById(R.id.nine);
        l9.setOnTouchListener(this);
        l0 = (ImageButton) findViewById(R.id.zero);
        l0.setOnTouchListener(this);
        lb = (ImageButton) findViewById(R.id.arrow);
        lb.setOnTouchListener(this);
        firstpw = (ImageView) findViewById(R.id.firstpw);
        firstpw.setOnTouchListener(this);
        secondpw = (ImageView) findViewById(R.id.secondpw);
        secondpw.setOnTouchListener(this);
        thirdpw = (ImageView) findViewById(R.id.thirdpw);
        thirdpw.setOnTouchListener(this);
        fourthpw = (ImageView) findViewById(R.id.fourthpw);
        fourthpw.setOnTouchListener(this);
    }

    public void checkPassWord() {
        switch (pw.size()) {
            case 0:
                password = "";
                firstpw.setImageResource(R.drawable.ic_dots_clear);
                secondpw.setImageResource(R.drawable.ic_dots_clear);
                thirdpw.setImageResource(R.drawable.ic_dots_clear);
                fourthpw.setImageResource(R.drawable.ic_dots_clear);
                break;
            case 1:
                password = "";
                firstpw.setImageResource(R.drawable.ic_dot);
                secondpw.setImageResource(R.drawable.ic_dots_clear);
                thirdpw.setImageResource(R.drawable.ic_dots_clear);
                fourthpw.setImageResource(R.drawable.ic_dots_clear);
                break;
            case 2:
                password = "";
                firstpw.setImageResource(R.drawable.ic_dot);
                secondpw.setImageResource(R.drawable.ic_dot);
                thirdpw.setImageResource(R.drawable.ic_dots_clear);
                fourthpw.setImageResource(R.drawable.ic_dots_clear);
                break;
            case 3:
                password = "";
                firstpw.setImageResource(R.drawable.ic_dot);
                secondpw.setImageResource(R.drawable.ic_dot);
                thirdpw.setImageResource(R.drawable.ic_dot);
                fourthpw.setImageResource(R.drawable.ic_dots_clear);
                break;
            case 4:
                firstpw.setImageResource(R.drawable.ic_dot);
                secondpw.setImageResource(R.drawable.ic_dot);
                thirdpw.setImageResource(R.drawable.ic_dot);
                fourthpw.setImageResource(R.drawable.ic_dot);
                for (int i = 0; i < pw.size(); i++) {
                    password += pw.get(i);
                }
                if (talk.getText().equals("현재 비밀번호를 입력하세요.")) {
                    if (password.equals(Preference.getPreferences_pass(getApplicationContext(), "passwd"))) {
                        //Toast.makeText(getApplicationContext(), "철컥", Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                talk.setText("바꿀 비밀번호를 입력하세요");
                                firstpw.setImageResource(R.drawable.ic_dots_clear);
                                secondpw.setImageResource(R.drawable.ic_dots_clear);
                                thirdpw.setImageResource(R.drawable.ic_dots_clear);
                                fourthpw.setImageResource(R.drawable.ic_dots_clear);
                                pw.clear();
                            }
                        }, 0);

                    } else {
                        talk.setText("비밀번호가 틀렸습니다");

                        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(500);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                firstpw.setImageResource(R.drawable.ic_dots_clear);
                                secondpw.setImageResource(R.drawable.ic_dots_clear);
                                thirdpw.setImageResource(R.drawable.ic_dots_clear);
                                fourthpw.setImageResource(R.drawable.ic_dots_clear);
                                pw.clear();
                                talk.setText("현재 비밀번호를 입력하세요.");
                            }
                        }, 500);
                        break;

                    }
                }
                if (talk.getText().equals("바꿀 비밀번호를 입력하세요")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            firstpw.setImageResource(R.drawable.ic_dots_clear);
                            secondpw.setImageResource(R.drawable.ic_dots_clear);
                            thirdpw.setImageResource(R.drawable.ic_dots_clear);
                            fourthpw.setImageResource(R.drawable.ic_dots_clear);
                            pw_check = (ArrayList<Integer>) pw.clone();
                            pw.clear();
                            talk.setText("다시한번 입력해 주세요");
                        }
                    }, 100);
                }

                if (talk.getText().equals("다시한번 입력해 주세요")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            firstpw.setImageResource(R.drawable.ic_dots_clear);
                            secondpw.setImageResource(R.drawable.ic_dots_clear);
                            thirdpw.setImageResource(R.drawable.ic_dots_clear);
                            fourthpw.setImageResource(R.drawable.ic_dots_clear);
                            if (pw.equals(pw_check)) {
                                Preference.setPreferances_pass(getApplicationContext(), "passwd", password);
                                finish();
                            } else {
                                talk.setText("비밀번호가 일치하지 않습니다.");
                                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(500);
                                firstpw.setImageResource(R.drawable.ic_dots_clear);
                                secondpw.setImageResource(R.drawable.ic_dots_clear);
                                thirdpw.setImageResource(R.drawable.ic_dots_clear);
                                fourthpw.setImageResource(R.drawable.ic_dots_clear);
                                Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        talk.setText("현재 비밀번호를 입력하세요.");
                                        pw.clear();
                                    }
                                }, 300);
                            }
                        }
                    }, 100);
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.one:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    l1.setBackgroundColor(Color.LTGRAY);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    l1.setBackgroundColor(0);
                    pw.add(1);
                    if (pw.size() <= 4)
                        checkPassWord();
                }
                break;
            case R.id.two:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    l2.setBackgroundColor(Color.LTGRAY);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    l2.setBackgroundColor(0);
                    pw.add(2);
                    if (pw.size() <= 4)
                        checkPassWord();
                }
                break;
            case R.id.three:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    l3.setBackgroundColor(Color.LTGRAY);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    l3.setBackgroundColor(0);
                    pw.add(3);
                    if (pw.size() <= 4)
                        checkPassWord();
                }
                break;
            case R.id.four:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    l4.setBackgroundColor(Color.LTGRAY);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    l4.setBackgroundColor(0);
                    pw.add(4);
                    if (pw.size() <= 4)
                        checkPassWord();
                }
                break;
            case R.id.five:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    l5.setBackgroundColor(Color.LTGRAY);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    l5.setBackgroundColor(0);
                    pw.add(5);
                    if (pw.size() <= 4)
                        checkPassWord();
                }
                break;
            case R.id.six:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    l6.setBackgroundColor(Color.LTGRAY);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    l6.setBackgroundColor(0);
                    pw.add(6);
                    if (pw.size() <= 4)
                        checkPassWord();
                }
                break;
            case R.id.seven:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    l7.setBackgroundColor(Color.LTGRAY);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    l7.setBackgroundColor(0);
                    pw.add(7);
                    if (pw.size() <= 4)
                        checkPassWord();
                }
                break;
            case R.id.eight:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    l8.setBackgroundColor(Color.LTGRAY);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    l8.setBackgroundColor(0);
                    pw.add(8);
                    if (pw.size() <= 4)
                        checkPassWord();
                }
                break;
            case R.id.nine:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    l9.setBackgroundColor(Color.LTGRAY);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    l9.setBackgroundColor(0);
                    pw.add(9);
                    if (pw.size() <= 4)
                        checkPassWord();
                }
                break;
            case R.id.zero:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    l0.setBackgroundColor(Color.LTGRAY);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    l0.setBackgroundColor(0);
                    pw.add(0);
                    if (pw.size() <= 4)
                        checkPassWord();
                }
                break;
            case R.id.arrow:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    lb.setBackgroundColor(Color.LTGRAY);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    lb.setBackgroundColor(0);
                    if (pw.size() > 0)
                        pw.remove(pw.size() - 1);
                    if (pw.size() <= 4)
                        checkPassWord();
                }
                break;
        }
        return true;
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
}
