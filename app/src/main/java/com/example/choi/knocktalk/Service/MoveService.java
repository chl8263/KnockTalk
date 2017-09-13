package com.example.choi.knocktalk.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.choi.knocktalk.Main.MainActivity;
import com.example.choi.knocktalk.R;

/**
 * Created by choi on 17. 9. 13.
 */

public class MoveService extends Service {
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("service", "시작중");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("type","1");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                    builder = new NotificationCompat.Builder(getApplicationContext());
                    builder.setSmallIcon(R.drawable.noti_icon)
                            .setContentTitle("Knock Talk")
                            .setContentText("수상한움직임 발견....!!")
                            .setDefaults(Notification.DEFAULT_VIBRATE)// 수신 알람 적용
                            .setAutoCancel(true) //알림바에서 자동 삭제
                            //.addAction(R.drawable.noti_cancel, "취소",pi) //버튼 추가하기
                            //.addAction(R.drawable.noti_ok, "확인하기", pi)
                            .setContentIntent(pi)
                    ;

                    notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
                    notificationManager.notify(0, builder.build());

                    break;
            }
        }
    };
}
























