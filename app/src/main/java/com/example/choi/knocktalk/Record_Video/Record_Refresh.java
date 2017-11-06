package com.example.choi.knocktalk.Record_Video;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.example.choi.knocktalk.Dialog.DownLoad_Progress;
import com.example.choi.knocktalk.SQLite.DBManager;
import com.example.choi.knocktalk.SharedPreferences.Preference;
import com.example.choi.knocktalk.Util.Contact;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by choi on 17. 9. 7.
 */

public class Record_Refresh extends Thread {
    private int portnumber = 9004;
    private String ip = Contact.ip_address;
    private Socket socket = null;
    private FileOutputStream fileOutputStream;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Context context;
    private String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String abPath = sdPath += "/KNOCK_TALK";
    private DBManager dbManager;

    public Record_Refresh(Context context) {
        this.context = context;
        Log.e("CREATE", "ok");
        dbManager = new DBManager(context.getApplicationContext(), "KNOCK_TALK", null, 1);
    }

    @Override
    public void run() {
        super.run();
        Intent intent = new Intent(context,DownLoad_Progress.class);
        intent.putExtra("download","ten");
        context.startActivity(intent);
        try {

            socket = new Socket(ip, portnumber);
            Log.e("CREATE", "socket ok");

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            dataOutputStream.writeUTF("GETT");
            int lastindex = dataInputStream.readInt();
            Log.e("getLast",lastindex+"");
            Log.e("현재 마지막 인덱스", String.valueOf(Preference.getPreferences(context.getApplicationContext(),"Last_index")));
            int getTen = lastindex-9;
            List<Integer> tenList_REEAAALLL = dbManager.getTen__REEEEEEEEAL(lastindex,getTen);
            String send_index = new String();
            for(int i=0;i<tenList_REEAAALLL.size();i++){
                send_index+=tenList_REEAAALLL.get(i);
                if(i!=tenList_REEAAALLL.size()-1)
                    send_index+=",";
            }
            Log.e("send_index",send_index);
            dataOutputStream.writeUTF(send_index);
            int resultCount = tenList_REEAAALLL.size(); //서버에서 보낼 파일이 몇개인지 알려줌
            if (resultCount != 0) {
                Log.e("진입", "" + resultCount);
                for (int i = 1; i <= resultCount; i++) { //여기 for문부터 쭉 돌면 됨
                    String fileContent = dataInputStream.readUTF();

                    Log.e("file", fileContent);
                    String fileContentsplit[] = fileContent.split(",");

                    fileOutputStream = new FileOutputStream(abPath + "/" + fileContentsplit[1]);
                    long fileSize = Long.parseLong(fileContentsplit[2]); // 파일 사이즈

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

                dataInputStream.close();
                dataOutputStream.close();
                Log.e("Preference", String.valueOf(Preference.getPreferences(context.getApplicationContext(), "Last_index")));
            } else {
                Log.e("진입", "ㄴ");
                socket.close();
                dataInputStream.close();
                dataOutputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            Log.e("진입", "Exception");
            e.printStackTrace();
        }
    }

}

