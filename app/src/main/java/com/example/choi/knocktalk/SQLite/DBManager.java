package com.example.choi.knocktalk.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.example.choi.knocktalk.SharedPreferences.Preference;

import java.io.File;
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
    private final String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/KNOCK_TALK";

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
    public int getIndex(String name){
        int index = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select index_number from TOTAL where name='"+name+"'",null);
        if (cursor.moveToFirst()) {
            index = cursor.getInt(0);
        }
        return index;
    }

    public String getname(int index){
        String result = new String();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select name from TOTAL where index_number='"+index+"'",null);
        if (cursor.moveToFirst()) {
            result = cursor.getString(0);
        }
        return result;
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
    public List<String> getTotalsort_Ten(){
        SQLiteDatabase db = getReadableDatabase();
        List<String> result = new ArrayList<String>();
        List<String> result_ten = new ArrayList<String>();
        Cursor cursor = db.rawQuery("select name from Total",null);
        while (cursor.moveToNext()){
            result.add(cursor.getString(0));
        }
        Collections.reverse(result);

        for(int i=0;i<10;i++){
            result_ten.add(result.get(i));
        }
        return result_ten;
    }
    public List<String> getTotalsort_Ten_Real(){
        List<String> ten = getTotalsort_Ten();
        List<String> result = new ArrayList<String>();
        List<String> result_real = new ArrayList<String>();
        File file = new File(sdPath);
        File list[] = file.listFiles();
        for(int i =0; i<list.length;i++){
            String split[] = list[i].getName().split("\\.");
            result.add(split[0]);
        }
        /*for(int i =0; i<result.size();i++){
            if(!ten.contains(result.get(i))){

            }
        }*/
        ten.removeAll(result);
        return ten;
    }
    public int getMaxIndex(){
        List<Integer> index = new ArrayList<Integer>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select index_number from TOTAL",null);
        while (cursor.moveToNext()){
            index.add(cursor.getInt(0));
        }
        Collections.reverse(index);
        return index.get(0);
    }
    public List<Integer> getTen__REEEEEEEEAL(int last,int first){
        File file = new File(sdPath);
        File list[] = file.listFiles();
        int maxindex = getMaxIndex();
        List<String> list_clone = new ArrayList<String>();
        List<Integer> result = new ArrayList<Integer>();
        for(int i=0;i<list.length;i++){ //저장된 동영상의 이름
            String split[] = list[i].getName().split("\\.");
            list_clone.add(split[0]);
        }
        List<String> db_extence = new ArrayList<String>(); //현재 db에 저장되어 있는 이름
        for(int i=first;i<maxindex;i++){ //저장된 동영상의 이름
            db_extence.add(getname(i));
        }
        for(int i=0;i<db_extence.size();i++){
            if(!db_extence.get(i).contains((CharSequence) list_clone))
                result.add(getIndex(db_extence.get(i)));
        }
        for(int i=maxindex+1;i<=last;i++){
            result.add(i);
        }
        return result;
    }
    public void delectDB (){
        SQLiteDatabase db =getWritableDatabase();
        db.execSQL("DELETE from TOTAL");
    }
}



























