package com.example.choi.knocktalk.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.example.choi.knocktalk.Adapter.FirstGridAdapter;
import com.example.choi.knocktalk.AdapterItem.GridItem;
import com.example.choi.knocktalk.R;

import java.util.ArrayList;

/**
 * Created by choi on 17. 8. 20.
 */

public class First extends Fragment {
    private ArrayList<GridItem> items;
    private FirstGridAdapter firstGridAdapter;
    private FloatingActionButton first_grid_addPerson;

    public First() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        dbHelper = new DBManager(getActivity().getApplicationContext(), "FIRSTGRID", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();*/
        items = new ArrayList<GridItem>();
        /*for(int i=0;i<4;i++){
            dbHelper.insert("android.resource://com.example.choi.knocktalk/drawable/person",i); //drawable Uri
        }*/
        /*items.add(new GridItem(R.drawable.person,"father",R.drawable.crown));
        items.add(new GridItem(R.drawable.person,"mother",R.drawable.crown));
        items.add(new GridItem(R.drawable.person,"sister",R.drawable.crown));
        items.add(new GridItem(R.drawable.person_add," ",R.drawable.crown));*/
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = (RelativeLayout) inflater.inflate(R.layout.first, container, false);
        first_grid_addPerson = view.findViewById(R.id.first_grid_addPerson);
        GridView gridView = (GridView) view.findViewById(R.id.MainGridView);
        firstGridAdapter = new FirstGridAdapter(view.getContext(), items);
        gridView.setAdapter(firstGridAdapter);

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == 0) {
                    first_grid_addPerson.show();
                } else {
                    first_grid_addPerson.hide();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //
                startActivityForResult(intent, position);
                Log.e("click", Integer.toString(position));
            }
        });
        //firstGridAdapter.notifyDataSetChanged(); 아이템 갱신
        return view;
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       *//* SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from firstgrid",null);*//**//*
        if (data == null) {

        } else if (data != null) {
            Uri uri = data.getData();
            *//**//*Cursor cursor2 = getContext().getContentResolver().query(data.getData(),null,null,null,null);
            cursor.moveToNext();
            String path  = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            Uri uri = Uri.fromFile(new File(path));
            cursor.close();*//**//*
            String imgpath = uri.toString();

            dbHelper.update(imgpath, requestCode);
            items.get(requestCode).setGridimage(imgpath);
            firstGridAdapter.notifyDataSetChanged();
            for (int i = 0; i < items.size(); i++) {
                Log.e("asd", items.get(i).getGridimage().toString());
            }
           *//**//* while (cursor2.moveToNext()){
                Log.e("zzz",cursor.getString(0));
            }*//**//*
            Log.e("asd", data.toString());
        }*//*
    }*/
}























