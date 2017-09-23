package com.example.choi.knocktalk.Record_Video;

import android.content.Context;
import android.content.Intent;
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
 * Created by choi on 17. 9. 7.
 */

public class Record_Refresh extends Thread {
    private Refresh_listener refresh_listener;
    private int portnumber = 9004;
    private String ip = "223.194.159.87";
    private Socket socket = null;
    private FileOutputStream fileOutputStream;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Context context;
    private String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String abPath = sdPath += "/KNOCK_TALK";
    private DBManager dbManager;

    public Record_Refresh(Context context, Refresh_listener refresh_listener) {
        this.context = context;
        this.refresh_listener = refresh_listener;
        Log.e("CREATE", "ok");
        dbManager = new DBManager(context.getApplicationContext(), "KNOCK_TALK", null, 1);
        dbManager.insert("CREATE TABLE IF NOT EXISTS TOTAL (name TEXT NOT NULL, index_number INTEGER NOT NULL);");
    }

    @Override
    public void run() {
        super.run();
        /*dbManager.TOTAL_CLEAN();*/
        Log.e("asd",dbManager.getTotal()+" ");
        try {
            Preference.setPreferances(context.getApplicationContext(), "Last_index", 0);
            Log.e("asd", "" + Preference.getPreferences(context.getApplicationContext(), "Last_index"));

            socket = new Socket(ip, portnumber);
            Log.e("CREATE", "socket ok");

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            //dataOutputStream.writeInt(dbManager.getLast_Index()); //인덱스 몇번부터 필요한지 서버에 요청
            //String fileName = dataInputStream.readLine(); // 파일 이름

            dataOutputStream.writeInt(Preference.getPreferences(context.getApplicationContext(), "Last_index")); //마지막 인텍스를 서버에 보냄

            int resultCount = dataInputStream.readInt(); //서버에서 보낼 파일이 몇개인지 알려줌
            if (resultCount != 0) {
                Log.e("진입", "" + resultCount);
                for (int i = 1; i <= resultCount; i++) { //여기 for문부터 쭉 돌면 됨
                   String fileContent = dataInputStream.readUTF();

                    Log.e("file", fileContent + "log");
                    String fileContentsplit[] = fileContent.split(",");

                    int index = Integer.parseInt(fileContentsplit[0]);
                    Log.e("file", index + "" +fileContentsplit[0]+fileContentsplit[1]+fileContentsplit[2] );
                    fileOutputStream = new FileOutputStream(abPath + "/" + fileContentsplit[1]);
                    long fileSize = Long.parseLong(fileContentsplit[2]); // 파일 사이즈

                    if (i == resultCount) { //마지막 인덱스를 Preferce 에 저장
                        Preference.setPreferances(context.getApplicationContext(), "Last_index", index);
                    }
                    dbManager.insert("insert into TOTAL values('"+fileContentsplit[1]+ "','"+fileContentsplit[0]+"');");

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
                }
                Log.e("전송완료", "완료됨........................");

                dataOutputStream.writeUTF("END");

                refresh_listener.dowork();
                dataInputStream.close();
                dataOutputStream.close();
                Log.e("Preference", String.valueOf(Preference.getPreferences(context.getApplicationContext(),"Last_index")));
            } else {
                Log.e("진입", "ㄴ");
                socket.close();
                dataInputStream.close();
                dataOutputStream.close();
                refresh_listener.notWork();
                Intent intent = new Intent();
                intent.setAction("refresh");
                context.sendBroadcast(intent);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("진입","Exception");
            e.printStackTrace();
        }
    }

}



  /*dbManager.insert("insert into TOTAL values('"+"aaa"+"','"+0+"');");
            dbManager.insert("insert into TOTAL values('"+"aaa"+"','"+50+"');");
            dbManager.insert("insert into TOTAL values('"+"aaa"+"','"+91+"');");
            dbManager.insert("insert into TOTAL values('"+"aaa"+"','"+88+"');");
            dbManager.insert("insert into TOTAL values('"+"aaa"+"','"+70+"');");
            dbManager.insert("insert into TOTAL values('"+"aaa"+"','"+5+"');");
            dbManager.insert("insert into TOTAL values('"+"aaa"+"','"+150+"');");
            dbManager.insert("insert into TOTAL values('"+"aaa"+"','"+26+"');");
            dbManager.insert("insert into TOTAL values('"+"aaa"+"','"+94+"');");
            ArrayList<Integer> integers = dbManager.getTotal_Index();
            for(int i=0;i<integers.size();i++){
                Log.e("asdasdsd",""+integers.get(i));
            }*/

 /*       while (-1 != dataInputStream.read(buffer)) {
                Log.e("파일받는중", "받는중..................");
                n = dataInputStream.read(buffer);
                fileOutputStream.write(buffer, 0, n);
                check += n;
                count += n;
                num++;
                Log.e("count", "n = " + n + " count : " + count + "  num : " + num);
            }
            while ((n = dataInputStream.read(buffer)) != -1) {
                Log.e("파일받는중", "받는중..................");

                fileOutputStream.write(buffer, 0, n);
                count += n;
                Log.e("count", "n = " + n + " count : " + count);
            }*/












