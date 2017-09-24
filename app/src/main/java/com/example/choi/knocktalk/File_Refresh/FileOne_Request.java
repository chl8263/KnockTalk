package com.example.choi.knocktalk.File_Refresh;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.choi.knocktalk.Interface.Refresh_listener;
import com.example.choi.knocktalk.SQLite.DBManager;
import com.example.choi.knocktalk.SharedPreferences.Preference;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by choi on 17. 9. 24.
 */

public class FileOne_Request extends Thread {
    private Refresh_listener refresh_listener;
    private int portnumber = 9004;
    private String ip = "192.168.0.3";
    private Socket socket = null;
    private FileOutputStream fileOutputStream;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Context context;
    private String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String abPath = sdPath += "/KNOCK_TALK";
    private DBManager dbManager;
    private String filename;
    private int index;
    public FileOne_Request(Context context ,String filename) {
        this.context = context;
        this.filename = filename;
        Log.e("CREATE", "ok");

        dbManager = new DBManager(context.getApplicationContext(), "KNOCK_TALK", null, 1);
        index = dbManager.getIndex(filename);
        //dbManager.insert("CREATE TABLE IF NOT EXISTS TOTAL (name TEXT NOT NULL, index_number INTEGER NOT NULL);");*/
    }



    @Override
    public void run() {
        super.run();
        try {
            socket = new Socket(ip, portnumber);
            Log.e("CREATE", "socket ok");

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            dataOutputStream.writeUTF("GETO");
            dataOutputStream.writeInt(index); //필요한 인덱스 요청

            //int resultCount = dataInputStream.readInt(); //서버에서 보낼 파일이 몇개인지 알려줌
            //if (resultCount != 0) {
               // Log.e("진입", "" + resultCount);
                //for (int i = 1; i <= resultCount; i++) { //여기 for문부터 쭉 돌면 됨
                    String fileContent = dataInputStream.readUTF();

                    Log.e("file", fileContent + "log");
                    String fileContentsplit[] = fileContent.split(",");

                    int index = Integer.parseInt(fileContentsplit[0]);
                    Log.e("file", index + "" +fileContentsplit[0]+fileContentsplit[1]+fileContentsplit[2] );
                    fileOutputStream = new FileOutputStream(abPath + "/" + fileContentsplit[1]);
                    long fileSize = Long.parseLong(fileContentsplit[2]); // 파일 사이즈

                   /* if (i == resultCount) { //마지막 인덱스를 Preferce 에 저장
                        Preference.setPreferances(context.getApplicationContext(), "Last_index", index);
                    }*/
                    //dbManager.insert("insert into TOTAL values('"+fileContentsplit[1]+ "','"+fileContentsplit[0]+"');");

                    int recvdataCount = 0;
                    int count = 0;
                    int byteSize = 1024;
                    int check = 0;
                    int totalcount = 0;

                    byte[] buffer = new byte[byteSize];

                    Log.e("파일받는중", "로딩중...." + fileSize);

                    while (count < fileSize) {
                        check = 0;
                        Log.e("파일받는중", "받는중..................");
                        recvdataCount = dataInputStream.read(buffer);
                        fileOutputStream.write(buffer, 0, recvdataCount);
                        check += recvdataCount;
                        count += recvdataCount;
                        totalcount++;
                        Log.e("count", "n = " + recvdataCount + " count : " + count + "  num : " + totalcount);
                        while (count < fileSize && check < byteSize) {
                            byte[] tmpbuffer = new byte[byteSize - check];
                            recvdataCount = dataInputStream.read(tmpbuffer);
                            fileOutputStream.write(tmpbuffer, 0, recvdataCount);
                            Log.e("count", "n = " + recvdataCount + " count : " + count + " byteSize-check : " + (byteSize - check));
                            check += recvdataCount;
                            count += recvdataCount;
                            Log.e("count", "check = " + check);
                        }
                    }
                    fileOutputStream.close();
                //}
                Log.e("전송완료", "완료됨........................");

                dataOutputStream.writeUTF("END");

                refresh_listener.dowork(); //////////////////////////////////////////////////////////
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
               /* Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);*/
                Log.e("Preference", String.valueOf(Preference.getPreferences(context.getApplicationContext(),"Last_index")));
            //} else {
            /*    Log.e("진입", "ㄴ");
                socket.close();
                dataInputStream.close();
                dataOutputStream.close();
                refresh_listener.notWork();  ////////////////////////////////////////////////////////////
                *//*Intent intent = new Intent();
                intent.setAction("refresh");*//*
                //context.sendBroadcast(intent);
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("진입","Exception");
            e.printStackTrace();
        }
    }
}
