package com.example.choi.knocktalk.Main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.TabLayout;
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
import com.example.choi.knocktalk.Interface.Refresh_listener;
import com.example.choi.knocktalk.R;
import com.example.choi.knocktalk.Service.MoveService;
import com.example.choi.knocktalk.Setting.SettingActivity;
import com.example.choi.knocktalk.Sound.Sound_Exit;
import com.example.choi.knocktalk.Sound.Sound_Init;
import com.example.choi.knocktalk.Sound.Sound_Recev;
import com.example.choi.knocktalk.Sound.Sound_Send;
import com.example.choi.knocktalk.Util.Contact;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import static com.example.choi.knocktalk.R.id.tab;

public class MainActivity extends AppCompatActivity {
    private String ip = Contact.ip_address;
    private DrawerLayout drawerLayout;
    private ArrayList<DrawerItem> drawerItems;
    private DrawerAdapter drawerAdapter;
    private ListView drawlistview;
    private ImageView drawerBtn;
    private Sound_Init init = null;//= new Sound_Init();
    private Sound_Exit exit = null;//= new Sound_Exit();
    private Sound_Send send = null;//= new Sound_Send();
    private Sound_Recev recev = null;//= new Sound_Recev();
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager pager;
    private TabLayout tabLayout;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private String type = null;
    private int gettype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        type = getIntent().getDataString();
        gettype = getIntent().getIntExtra("aaa", 0);
        Log.e("asdddddddddddddddddd", "" + gettype);
        new Start_service().start();
        initstatusbar();
        init();
        startService();
        make_img_file();
        make_File();
        setDrawerLayout();
        setDrawerLayoutButton();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
    }

    private void make_File() {
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String abPath = sdPath += "/KNOCK_TALK";
        File file = new File(abPath);
        file.mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void make_img_file() {
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String abPath = sdPath += "/KNOCK_TALK_capture";
        File file = new File(abPath);
        file.mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        } else {
            long tempTime = System.currentTimeMillis();
            long intervalTime = tempTime - backPressedTime;
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                super.onBackPressed();
            } else {
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), "종료하시려면 한번더 눌러주세요", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void setDrawerLayoutButton() {
        drawerBtn = (ImageView) findViewById(R.id.menuimg);
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
        drawerAdapter = new DrawerAdapter(getApplicationContext(), drawerItems);
        drawerItems.add(new DrawerItem(R.drawable.settings, "설정"));
        drawlistview = (ListView) findViewById(R.id.drawableListView);
        drawlistview.setAdapter(drawerAdapter);
        drawlistview.setDividerHeight(20);
        drawlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                drawerLayout.closeDrawer(Gravity.START);
                            }
                        }, 200);
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
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), refresh_listener);
        tabLayout = (TabLayout) findViewById(tab);
        tabLayout.addTab(tabLayout.newTab().setText("HOME"));
        tabLayout.addTab(tabLayout.newTab().setText("RECORD"));
        tabLayout.addTab(tabLayout.newTab().setText("STREAMING"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(viewPagerAdapter);
        if (type != null) {
            pager.setCurrentItem(2);
            if (send == null) {
                send = new Sound_Send();
                send.start();
            }
            if (recev == null) {
                recev = new Sound_Recev();
                recev.start();
            }
        } else {
            if (gettype == 1) {
                pager.setCurrentItem(1);
            } else {
                pager.setCurrentItem(0);
            }
        }
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
                        break;
                    case 1:
                        if (exit != null)
                            exit = null;
                        exit = null;
                        exit = new Sound_Exit();
                        exit.start();
                        if (send != null) {
                            send.interrupt();
                            send = null;
                        }
                        if (recev != null) {
                            recev.interrupt();
                            recev = null;
                        }
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

    private class Start_service extends Thread {
        private DatagramPacket packet = null;
        private DatagramSocket socket = null;
        byte[] data = new byte[4];

        public Start_service() {
        }

        @Override
        public void run() {
            super.run();
            try {
                data = "BACK".getBytes();
                socket = new DatagramSocket();
                packet = new DatagramPacket(data, data.length, InetAddress.getByName(ip), 9002);
                socket.send(packet);
                Log.e("START_SERVICE", "good");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    Refresh_listener refresh_listener = new Refresh_listener() {
        @Override
        public void dowork() {

        }

        @Override
        public void notWork() {

        }

        @Override
        public void mic_stop() {
            if (send != null) {
                send.interrupt();
                send = null;
            }
        }

        @Override
        public void mic_go() {
            if (send == null) {
                send = new Sound_Send();
                send.start();
            }
        }
    };
}