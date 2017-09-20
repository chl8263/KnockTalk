package com.example.choi.knocktalk.Main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.choi.knocktalk.Adapter.DrawerAdapter;
import com.example.choi.knocktalk.Adapter.ViewPagerAdapter;
import com.example.choi.knocktalk.AdapterItem.DrawerItem;
import com.example.choi.knocktalk.R;
import com.example.choi.knocktalk.Service.MoveService;
import com.example.choi.knocktalk.Sound.Sound_Exit;
import com.example.choi.knocktalk.Sound.Sound_Init;
import com.example.choi.knocktalk.Sound.Sound_Recev;
import com.example.choi.knocktalk.Sound.Sound_Send;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ArrayList<DrawerItem> drawerItems;
    private DrawerAdapter drawerAdapter;
    private ListView drawlistview;
    private ImageView drawerBtn;
    private Sound_Init init;//= new Sound_Init();
    private Sound_Exit exit;//= new Sound_Exit();
    private Sound_Send send;//= new Sound_Send();
    private Sound_Recev recev;//= new Sound_Recev();
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager pager;
    private TabLayout tabLayout;
    private String type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //RtspPlayView rtspPlayView = new RtspPlayView(getApplicationContext());
        //setContentView(rtspPlayView);
        setContentView(R.layout.activity_main);

        type = getIntent().getDataString();
        initstatusbar();
        init();
        startService();
        make_File();
        setDrawerLayout();
        setDrawerLayoutButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPagerAdapter.notifyDataSetChanged();
    }

    private void make_File() {
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String abPath = sdPath += "/KNOCK_TALK";
        File file = new File(abPath);
        file.mkdirs();
        try {
            file.createNewFile();
            Toast.makeText(getApplicationContext(), "성공 ", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "ㄴㄴ ", Toast.LENGTH_SHORT).show();
        }

    }

   private void Permission(){
       if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED)
           else
   }

    private void startService() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(drawerLayout.isDrawerOpen(Gravity.START))
            drawerLayout.closeDrawer(Gravity.START);
        else super.onBackPressed();
    }

    private void setDrawerLayoutButton(){
        drawerBtn = (ImageView)findViewById(R.id.menuimg);
        drawerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawlistview);
            }
        });
    }
    private void setDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerItems = new ArrayList<DrawerItem>();
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerAdapter = new DrawerAdapter(getApplicationContext(),drawerItems);

        drawerItems.add(new DrawerItem(R.drawable.settings,"설정"));
        drawlistview = (ListView)findViewById(R.id.drawableListView);
        drawlistview.setAdapter(drawerAdapter);
        drawlistview.setDividerHeight(5);
        drawlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:

                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        });

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
        if (type != null) {
            pager.setCurrentItem(2);
        } else pager.setCurrentItem(0);
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
                        if (send != null)
                            send.interrupt();
                        if (recev != null)
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
                        if (send != null)
                            send.interrupt();
                        if (recev != null)
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