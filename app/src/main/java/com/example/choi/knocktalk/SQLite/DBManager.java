package com.example.choi.knocktalk.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.choi.knocktalk.SharedPreferences.Preference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by choi on 17. 8. 24.
 */

public class DBManager extends SQLiteOpenHelper {
    private SQLiteDatabase data;
    private ArrayList<Integer> Index_List;
    private Context context;
    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE firstgrid (URI String, position INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public void insert(String _query){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }
    public void update(String _query){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }
    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public ArrayList getSort_Ten(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList arrayList = new ArrayList<String>();
        int count=0;

        for(int i=Preference.getPreferences(context.getApplicationContext(),"Last_index");i>0;i--){
            String name = null;
            Cursor cursor = db.rawQuery("select name from TOTAL where index_number="+i+"",null);
            if (cursor.moveToFirst()) {
                name = cursor.getString(0);
            }
            count++;
            if (count==9) break;
            arrayList.add(name);
        }

        Collections.reverse(arrayList);
        return arrayList;
    }
    public String getTotal(){
        SQLiteDatabase db = getReadableDatabase();
        String result="";
        Cursor cursor = db.rawQuery("select name from Total",null);
        while (cursor.moveToNext()){
            result+=cursor.getString(0);
            result+=",";
        }
        return result;
    }
    public List<String> getTotalsort(){
        SQLiteDatabase db = getReadableDatabase();
        List<String> result = new ArrayList<String>();
        Cursor cursor = db.rawQuery("select name from Total",null);
        while (cursor.moveToNext()){
            result.add(cursor.getString(0));
        }
        Collections.reverse(result);
        return result;
    }
}





























