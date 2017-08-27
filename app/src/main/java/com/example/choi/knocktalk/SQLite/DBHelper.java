package com.example.choi.knocktalk.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by choi on 17. 8. 24.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE firstgrid (URI String, position INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public void insert(String firstgridimg,int position){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO firstgrid VALUES('"+firstgridimg+"',"+position+");");
        //db.close();
    }
    public void update(String URICHANGE,int position){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE firstgrid SET URI='"+URICHANGE+"'WHERE position="+position+";");

    }
}
