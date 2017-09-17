package com.example.choi.knocktalk.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.example.choi.knocktalk.Adapter.SecondGridAdapter;
import com.example.choi.knocktalk.AdapterItem.SecondGriditem;
import com.example.choi.knocktalk.R;
import com.example.choi.knocktalk.Record_Video.Record_Refresh;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by choi on 17. 8. 20.
 */

public class Second extends Fragment {
    private FloatingActionButton refreshbtn;
    private GridView SecondGridView;
    private SecondGridAdapter secondGridAdapter;
    private ArrayList<SecondGriditem> secondGriditems;
    private Animation refresh_anim;
    private Record_Refresh record_refresh;
    private File file;
    private final String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/KNOCK_TALK";
    public Second() {

    }

    public static Second newInstance() {
        
        Bundle args = new Bundle();
        
        Second fragment = new Second();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        secondGriditems = new ArrayList<SecondGriditem>();
        file = new File(sdPath);
        File list[] = file.listFiles();
        for(int i=0;i<list.length;i++){
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(sdPath+"/"+list[i].getName(), MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
            secondGriditems.add(new SecondGriditem(bitmap,list[i].getName()));
            Log.e("fileName",list[i].getName());
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = (RelativeLayout) inflater.inflate(R.layout.second, container, false);
        SecondGridView = (GridView)view.findViewById(R.id.SecondGridView);
        secondGridAdapter = new SecondGridAdapter(view.getContext(),secondGriditems);
        SecondGridView.setAdapter(secondGridAdapter);
        SecondGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if(i==0) refreshbtn.show();
                else refreshbtn.hide();
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        refreshbtn = (FloatingActionButton)view.findViewById(R.id.refreshBtn);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh_anim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.refresh_anim);
        refreshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("secondBTN","OK");
                refreshbtn.startAnimation(refresh_anim);
                record_refresh = new Record_Refresh();
                record_refresh.start();
            }
        });
        SecondGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(sdPath+"/"+secondGriditems.get(position).getName());
                intent.setDataAndType(uri,"video/*");
                startActivity(intent);
            }
        });
    }
}


















