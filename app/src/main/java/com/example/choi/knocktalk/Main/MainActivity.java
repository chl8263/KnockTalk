package com.example.choi.knocktalk.Main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.choi.knocktalk.Adapter.ViewPagerAdapter;
import com.example.choi.knocktalk.R;
import com.example.choi.knocktalk.Service.MoveService;
import com.example.choi.knocktalk.Sound.Sound_Exit;
import com.example.choi.knocktalk.Sound.Sound_Init;
import com.example.choi.knocktalk.Sound.Sound_Recev;
import com.example.choi.knocktalk.Sound.Sound_Send;

public class MainActivity extends AppCompatActivity {
    private Sound_Init init;//= new Sound_Init();
    private Sound_Exit exit;//= new Sound_Exit();
    private Sound_Send send;//= new Sound_Send();
    private Sound_Recev recev;//= new Sound_Recev();
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager pager;
    private TabLayout tabLayout;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //RtspPlayView rtspPlayView = new RtspPlayView(getApplicationContext());
        //setContentView(rtspPlayView);
        setContentView(R.layout.activity_main);

       // Bundle type = getIntent().getExtras();
        //Toast.makeText(getApplicationContext(),type.getString("type"),Toast.LENGTH_SHORT).show();
        initstatusbar();
        init();
        startService();
    }
    private void startService(){
        Intent intent = new Intent(getApplicationContext(), MoveService.class);
        startService(intent);
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

    private void init() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("HOME"));
        tabLayout.addTab(tabLayout.newTab().setText("RECORD"));
        tabLayout.addTab(tabLayout.newTab().setText("STREAMING"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(viewPagerAdapter);
        pager.setOffscreenPageLimit(3); //viewpager 3가지를 전부 띄워놓음
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        if (exit != null) {
                            if (exit.isAlive()) {
                                exit.interrupt();
                                Log.e("dead", "dead");
                            }
                        }
                        exit = null;
                        exit = new Sound_Exit();
                        exit.start();
                        if (send!=null)
                            send.interrupt();
                        if (recev!=null)
                            recev.interrupt();
                        //send = null;//.interrupt();
                        //recev = null;//.interrupt();
                        break;
                    case 1:
                        if (exit != null)
                            exit = null;
                        exit = null;
                        exit = new Sound_Exit();
                        exit.start();
                        if (send!=null)
                            send.interrupt();
                        if (recev!=null)
                            recev.interrupt();
                        //send = null;//.interrupt();
                        //recev = null;//.interrupt();
                        break;
                    case 2:
                        if (init != null)
                            init = null;
                        init = null;
                        init = new Sound_Init();
                        init.start();
                        recev = new Sound_Recev();
                        recev.start();
                        send = new Sound_Send();
                        send.start();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}