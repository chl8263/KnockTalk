package com.example.choi.knocktalk.Fragment;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.choi.knocktalk.Adapter.SecondGridAdapter;
import com.example.choi.knocktalk.AdapterItem.SecondCarditem;
import com.example.choi.knocktalk.Interface.Refresh_listener;
import com.example.choi.knocktalk.R;
import com.example.choi.knocktalk.Record_Video.Record_Refresh;
import com.example.choi.knocktalk.SQLite.DBManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by choi on 17. 8. 20.
 */

public class Second extends Fragment{
    private FloatingActionButton refreshbtn;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SecondGridAdapter secondGridAdapter;
    private ArrayList<SecondCarditem> secondCarditems;
    private Animation refresh_anim;
    private Record_Refresh record_refresh;
    private File file;
    private DBManager dbManager;
    private final String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/KNOCK_TALK";

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

        secondCarditems = new ArrayList<SecondCarditem>();

        dbManager = new DBManager(getActivity().getApplicationContext(), "KNOCK_TALK", null, 1);
        dbManager.insert("CREATE TABLE IF NOT EXISTS TOTAL (name TEXT NOT NULL, index_number INTEGER NOT NULL);");
        ArrayList sort_ten = dbManager.getSort_Ten();

        file = new File(sdPath);
        File list[] = file.listFiles();

        List <String> filenameList = new ArrayList<String>();
        int j=0;
        for(int i=0;i<list.length;i++){
            filenameList.add(list[i].getName());
            j++;
            if(j==9) break;
        }
        Collections.reverse(filenameList);

        for (int i = 0; i<filenameList.size(); i++){
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(sdPath+"/"+filenameList.get(i), MediaStore.Images.Thumbnails.FULL_SCREEN_KIND); //sercer에서 이름이 어떻게 넘어오는지 알아야 함
            secondCarditems.add(new SecondCarditem(bitmap, filenameList.get(i)));
            Log.e("fileName",list[i].getName());
        }

        /*for (int i = sort_ten.size()-1; i>=0; i--) {
            Log.e("asdadasd", String.valueOf(sort_ten.get(i)));
        }*/
        /*for (int i = sort_ten.size()-1; i>=0; i--){
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(sdPath+"/"+sort_ten.get(i), MediaStore.Images.Thumbnails.FULL_SCREEN_KIND); //sercer에서 이름이 어떻게 넘어오는지 알아야 함
            secondCarditems.add(new SecondCarditem(bitmap,list[i].getName()));
            Log.e("fileName",list[i].getName());
        }*/
        /*Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(sdPath+"/"+"2017-09-08-09-58-47.avi", MediaStore.Images.Thumbnails.FULL_SCREEN_KIND); //sercer에서 이름이 어떻게 넘어오는지 알아야 함
        secondCarditems.add(new SecondCarditem(bitmap,sdPath+"/"+"2017-09-08-09-58-47.avi"));*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = (RelativeLayout) inflater.inflate(R.layout.second, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.second_recycleView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        secondGridAdapter = new SecondGridAdapter(view.getContext(), secondCarditems);
        recyclerView.setAdapter(secondGridAdapter);
        refreshbtn = (FloatingActionButton) view.findViewById(R.id.refreshBtn);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh_anim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.refresh_anim);
        refreshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("secondBTN", "OK");
                refreshbtn.startAnimation(refresh_anim);
                record_refresh = new Record_Refresh(getContext(),refresh_listener);
                record_refresh.start();
            }
        });
    }
    Refresh_listener refresh_listener = new Refresh_listener() {
        @Override
        public void dowork() {
            Log.e("call","back");

        }

        @Override
        public void notWork() {
            Log.e("callBAck","notwork");
        }
    };
}


















