package com.example.choi.knocktalk.File_Refresh;

import android.content.Context;
import android.util.Log;

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
    private int portnumber = 9004;
    private String ip = "192.168.0.2";
    private Socket socket = null;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Context context;
    private DBManager dbManager;

    public AllFileName_request(Context context) {
        this.context = context;
        Log.e("CREATE", "ok");
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

            int resultCount = dataInputStream.readInt();

            for (int i = 0; i < resultCount; i++) {
                String content = dataInputStream.readUTF();
                String[] contentSplit = content.split(",");
                dbManager.insert("insert into TOTAL values('" + contentSplit[1] + "','" + contentSplit[0] + "');");
                if (i == resultCount - 1) { //마지막 인덱스를 Preferce 에 저장
                    Preference.setPreferances(context.getApplicationContext(), "Last_index", Integer.parseInt(contentSplit[0]));
                }
            }
            dataOutputStream.writeUTF("END");
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("진입", "Exception");
            e.printStackTrace();
        }
    }
}

