package com.example.choi.knocktalk;

/**
 * Created by choi on 17. 8. 19.
 */

public class NDKAdapter {
    public NDKAdapter(){
    }
    static{
        System.loadLibrary("VideoPlayer");
    }
    public static native void setDataSource(String uri);
    public static native int play(Object surface);

}
