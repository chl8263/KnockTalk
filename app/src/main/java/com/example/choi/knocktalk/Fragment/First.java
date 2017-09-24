package com.example.choi.knocktalk.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.choi.knocktalk.Adapter.First_ExpandableApater;
import com.example.choi.knocktalk.File_Refresh.AllFileName_request;
import com.example.choi.knocktalk.First_Dialog.DownLoad_Progress;
import com.example.choi.knocktalk.R;
import com.example.choi.knocktalk.SQLite.DBManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by choi on 17. 8. 20.
 */

public class First extends Fragment {
    private final int EXISTENCE=3;
    private final int NONEXISTENCE=4;
    private Animation refresh_anim;
    private RecyclerView recyclerView;
    private FloatingActionButton refresh_list;
    private DBManager dbManager;
    List<First_ExpandableApater.Item> data = new ArrayList<>();
    private final String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/KNOCK_TALK";
    public First() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = new DBManager(getActivity().getApplicationContext(), "KNOCK_TALK", null, 1);
        Log.e("first_Checkdb", dbManager.getTotal());
        //List<String> name = dbManager.getTotalsort();
        /*for (int i = 0; i < name.size(); i++) {
            Log.e("First_sort_db", name.get(i));
        }
        HashMap<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < name.size(); i++) {
            String titleSplit[] = name.get(i).split("-");
            Log.e("split", titleSplit[0] + "-" + titleSplit[1] + "-" + titleSplit[2]);
            String a = titleSplit[0] + "-" + titleSplit[1] + "-" + titleSplit[2];
            First_ExpandableApater.Item places;
            if (!map.containsKey(a)) {
                map.put(a, a);
                places = new First_ExpandableApater.Item(First_ExpandableApater.HEADER, a);
            }
            places.

        }
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            String key = (String)iterator.next();
            Log.e("HASH_MAP",key);
            Log.e("HASH_MAP : Value",map.get(key));
        }*/
        File file = new File(sdPath);
        File list[] = file.listFiles();
        List <String> filenameList = new ArrayList<String>();
        for(int i=0;i<list.length;i++) {
            Log.e("aaaaaaaaaaaa",list[i].getName());

        }
        for(int i=0;i<list.length;i++) {
            String split[] = list[i].getName().split("\\.");
            filenameList.add(split[0]);
        }
        for(int i=0;i<filenameList.size();i++) {
            Log.e("sssssssssssssssss",filenameList.get(i));
        }
        List<First_ExpandableApater.Item> adapter_item = new ArrayList<First_ExpandableApater.Item>();
        List<String> name = dbManager.getTotalsort();
        HashMap<String, String> checkTitle = new HashMap<String, String>();
        int check = -1;
        for (int i = 0; i < name.size(); i++) {
            String titleSplit[] = name.get(i).split("-");
            String title = titleSplit[0] + "-" + titleSplit[1] + "-" + titleSplit[2];
            if (!checkTitle.containsKey(title)) {
                checkTitle.put(title, title);
                adapter_item.add(new First_ExpandableApater.Item(First_ExpandableApater.HEADER, title));
                check++;
                adapter_item.get(check).invisibleChildren = new ArrayList<>();
            }
            if(filenameList.contains(name.get(i))) {
                adapter_item.get(check).invisibleChildren.add(new First_ExpandableApater.Item(First_ExpandableApater.CHILD, name.get(i), EXISTENCE));
            }else adapter_item.get(check).invisibleChildren.add(new First_ExpandableApater.Item(First_ExpandableApater.CHILD, name.get(i), NONEXISTENCE));
        }
        for (int i = 0; i < adapter_item.size(); i++) {
            data.add(adapter_item.get(i));
        }
        Iterator<String> iterator = checkTitle.keySet().iterator();
        while (iterator.hasNext()){
            String key = (String)iterator.next();
            Log.e("HASH_MAP",key);
            Log.e("HASH_MAP : Value",checkTitle.get(key));
        }
       /* String nameSplit[] = dbManager.getTotal().split(",");
        for(int i=0;i<nameSplit.length;i++){
            String[] titleSplit = nameSplit[i].split("-");
            String title=titleSplit[0]+"-"+titleSplit[1]+"-"+titleSplit[2];

            First_ExpandableApater.Item places = new First_ExpandableApater.Item(First_ExpandableApater.HEADER, title);
            places.invisibleChildren.add(new First_ExpandableApater.Item(First_ExpandableApater.CHILD, nameSplit[i]));
            //List <String> title = new ArrayList<>();
        }
          */



        /*First_ExpandableApater.Item places = new First_ExpandableApater.Item(First_ExpandableApater.HEADER, "Places");
        places.invisibleChildren = new ArrayList<>();
        places.invisibleChildren.add(new First_ExpandableApater.Item(First_ExpandableApater.CHILD, "Kerala"));
        places.invisibleChildren.add(new First_ExpandableApater.Item(First_ExpandableApater.CHILD, "Tamil Nadu"));
        places.invisibleChildren.add(new First_ExpandableApater.Item(First_ExpandableApater.CHILD, "Karnataka"));
        places.invisibleChildren.add(new First_ExpandableApater.Item(First_ExpandableApater.CHILD, "Maharashtra"));
        First_ExpandableApater.Item places2 = new First_ExpandableApater.Item(First_ExpandableApater.HEADER, "Places2");
        places2.invisibleChildren = new ArrayList<>();
        places2.invisibleChildren.add(new First_ExpandableApater.Item(First_ExpandableApater.CHILD, "aaa"));
        places2.invisibleChildren.add(new First_ExpandableApater.Item(First_ExpandableApater.CHILD, "asd"));
        places2.invisibleChildren.add(new First_ExpandableApater.Item(First_ExpandableApater.CHILD, "qqq"));
        places2.invisibleChildren.add(new First_ExpandableApater.Item(First_ExpandableApater.CHILD, "aMvvvvv"));

        data.add(places);
        data.add(places2);*/
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = (RelativeLayout) inflater.inflate(R.layout.first, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.first_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new First_ExpandableApater(data,getActivity().getApplicationContext()));
        refresh_list = (FloatingActionButton) view.findViewById(R.id.first_refresh);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh_anim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.refresh_anim);
        refresh_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("firestBTN", "OK");
                refresh_list.startAnimation(refresh_anim);
                Intent intent = new Intent(view.getContext(), DownLoad_Progress.class);
                view.getContext().startActivity(intent);
                AllFileName_request allFileName_request = new AllFileName_request(getContext());
                allFileName_request.start();
                //first floating button 구현
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && refresh_list.getVisibility() == View.VISIBLE) {
                    refresh_list.hide();
                } else if (dy < 0 && refresh_list.getVisibility() != View.VISIBLE) {
                    refresh_list.show();
                }
            }
        });
    }
}























