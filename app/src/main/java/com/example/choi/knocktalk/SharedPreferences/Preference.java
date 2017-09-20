package com.example.choi.knocktalk.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by choi on 17. 9. 20.
 */

public class Preference {
    public static SharedPreferences p = null;
    public static void setPreferances(Context context, String key, int value){
        p = context.getSharedPreferences("Last_index",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        editor.putInt(key,value);
        editor.commit();
    }
    public static int getPreferences(Context context, String key){
        p = context.getSharedPreferences("Last_index",context.MODE_PRIVATE);
        return p.getInt(key,0);
    }
}
