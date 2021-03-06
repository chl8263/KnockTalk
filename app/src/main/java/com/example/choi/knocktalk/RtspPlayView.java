package com.example.choi.knocktalk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by choi on 17. 8. 19.
 */

public class RtspPlayView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private NDKAdapter adapter;
    private Thread th;
    private String uri = "rtsp://192.168.0.2:8091/test1";

    public RtspPlayView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        adapter = new NDKAdapter();
        adapter.setDataSource(uri);
    }

    public RtspPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
        adapter = new NDKAdapter();
        adapter.setDataSource(uri);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    adapter.play(holder.getSurface());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        th.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        th.interrupt();
        th = null;
    }
}
