package com.example.choi.knocktalk.Fragment;

import android.os.Bundle;
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
import com.example.choi.knocktalk.R;
import com.example.choi.knocktalk.SQLite.DBManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by choi on 17. 8. 20.
 */

public class First extends Fragment {
    private Animation refresh_anim;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private DBManager dbManager;
    List<First_ExpandableApater.Item> data = new ArrayList<>();

    public First() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = new DBManager(getActivity().getApplicationContext(), "KNOCK_TALK", null, 1);
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
            }

            adapter_item.get(check).invisibleChildren.add(new First_ExpandableApater.Item(First_ExpandableApater.CHILD, name.get(i), 0));
        }
        for (int i = 0; i < adapter_item.size(); i++) {
            data.add(adapter_item.get(i));
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
        recyclerView.setAdapter(new First_ExpandableApater(data));
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.first_refresh);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh_anim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.refresh_anim);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("firestBTN", "OK");
                floatingActionButton.startAnimation(refresh_anim);
                //first floating button 구현
            }
        });
    }
}























