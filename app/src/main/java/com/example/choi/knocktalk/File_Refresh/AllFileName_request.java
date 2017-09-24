package com.example.choi.knocktalk.File_Refresh;

import android.content.Context;
import android.util.Log;

import com.example.choi.knocktalk.Interface.Refresh_listener;
import com.example.choi.knocktalk.SQLite.DBManager;
import com.example.choi.knocktalk.SharedPreferences.Preference;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by choi on 17. 9. 24.
 */

public class AllFileName_request extends Thread {
    private Refresh_listener refresh_listener;
    private int portnumber = 9004;
    private String ip = "192.168.0.3";
    private Socket socket = null;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Context context;
    private DBManager dbManager;

    public AllFileName_request(Context context) {
        this.context = context;
        Log.e("CREATE", "ok");/*
        dbManager = new DBManager(context, "KNOCK_TALK", null, 1);
        dbManager.insert("CREATE TABLE IF NOT EXISTS TOTAL (name TEXT NOT NULL, index_number INTEGER NOT NULL);");*/
        dbManager = new DBManager(context.getApplicationContext(), "KNOCK_TALK", null, 1);
        dbManager.insert("CREATE TABLE IF NOT EXISTS TOTAL (name TEXT NOT NULL, index_number INTEGER NOT NULL);");
    }

    @Override
    public void run() {
        super.run();
        try {
            dbManager.delectDB();
            socket = new Socket(ip, portnumber);
            Log.e("CREATE", "socket ok");

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            dataOutputStream.writeUTF("GETI");
            //dataOutputStream.writeInt(Preference.getPreferences(context.getApplicationContext(),"Last_index")); //필요한 인덱스 요청

            int resultCount = dataInputStream.readInt(); //서버에서 보낼 파일이 몇개인지 알려줌y

            for (int i = 0; i < resultCount; i++) {
                String content = dataInputStream.readUTF();
                String[] contentSplit = content.split(",");
                dbManager.insert("insert into TOTAL values('" + contentSplit[1] + "','" + contentSplit[0] + "');");
                if (i == resultCount-1) { //마지막 인덱스를 Preferce 에 저장
                    Preference.setPreferances(context.getApplicationContext(), "Last_index", Integer.parseInt(contentSplit[0]));
                }
            }
            dataOutputStream.writeUTF("END");
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
            /*if (resultCount != 0) {
                Log.e("진입", "" + resultCount);
                for (int i = 1; i <= resultCount; i++) { //여기 for문부터 쭉 돌면 됨
                    String fileContent = dataInputStream.readUTF();

                    Log.e("file", fileContent + "log");
                    String fileContentsplit[] = fileContent.split(",");

                    int index = Integer.parseInt(fileContentsplit[0]);
                    Log.e("file", index + "" +fileContentsplit[0]+fileContentsplit[1]+fileContentsplit[2] );

                   *//* if (i == resultCount) { //마지막 인덱스를 Preferce 에 저장
                        Preference.setPreferances(context.getApplicationContext(), "Last_index", index);
                    }*//*
                    //dbManager.insert("insert into TOTAL values('"+fileContentsplit[1]+ "','"+fileContentsplit[0]+"');");

                }
                Log.e("전송완료", "완료됨........................");

                dataOutputStream.writeUTF("END");

                refresh_listener.dowork(); //////////////////////////////////////////////////////////
                dataInputStream.close();
                dataOutputStream.close();
                Log.e("Preference", String.valueOf(Preference.getPreferences(context.getApplicationContext(),"Last_index")));
            } else {
                Log.e("진입", "ㄴ");
                socket.close();
                dataInputStream.close();
                dataOutputStream.close();
                refresh_listener.notWork();  ////////////////////////////////////////////////////////////
                Intent intent = new Intent();
                intent.setAction("refresh");
                context.sendBroadcast(intent);
            }
*/
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("진입", "Exception");
            e.printStackTrace();
        }
    }
}

