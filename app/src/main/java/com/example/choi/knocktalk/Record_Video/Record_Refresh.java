package com.example.choi.knocktalk.Record_Video;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by choi on 17. 9. 7.
 */

public class Record_Refresh extends Thread{
    private int portnumber = 10001;
    private String ip = "113.198.84.54";
    private Socket socket =null;
    private BufferedReader bufferedReader;
    private String filename;
    private long filesize;
    private FileOutputStream fileOutputStream;
    private DataInputStream dataInputStream;
    private File file;
    public Record_Refresh(){
        Log.e("CREATE","ok");
    }

    @Override
    public void run() {
        super.run();
        try {
            socket = new Socket(ip,portnumber);
            Log.e("CREATE","socket ok");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("CREATE","no");
        }catch (Exception e){
            e.printStackTrace();
            Log.e("CREATE","no");
        }
        try {
            String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            String abPath = sdPath+= "/KNOCK_TALK";
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataInputStream = new DataInputStream(socket.getInputStream());
            fileOutputStream = new FileOutputStream(abPath+="/test.avi");

            int n=0;
            int count = 0;
            int byteSize=1024;
            byte [] buffer = new byte[byteSize];
            //String a = bufferedReader.readLine(); //제목
            long aa = Long.parseLong(bufferedReader.readLine());//ㄴ파일 사이즈
            Log.e("파일받는중","로딩중...." +aa);
            while(count<aa){
                Log.e("파일받는중","받는중..................");
                n = dataInputStream.read(buffer);
                fileOutputStream.write(buffer,0,n);
                count+=n;
                Log.e("ount","n = " + n + " count : " +count);
            }
            Log.e("전송완료","완료됨........................");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


















