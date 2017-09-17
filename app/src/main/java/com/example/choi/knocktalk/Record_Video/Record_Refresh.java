package com.example.choi.knocktalk.Record_Video;

import android.os.Environment;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by choi on 17. 9. 7.
 */

public class Record_Refresh extends Thread {
    private int portnumber = 10001;
    private String ip = "223.194.157.175";
    private Socket socket = null;
    private FileOutputStream fileOutputStream;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String abPath = sdPath += "/KNOCK_TALK";

    public Record_Refresh() {
        Log.e("CREATE", "ok");
    }

    @Override
    public void run() {
        super.run();
        try {
            socket = new Socket(ip, portnumber);
            Log.e("CREATE", "socket ok");

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            //dataOutputStream.writeInt(0); //인덱스 몇번부터 필요한지 서버에 요청
            //String fileName = dataInputStream.readLine(); // 파일 이름

            dataOutputStream.writeInt(1); //마지막 인텍스를 서버에 요청함
            int resultCount = dataInputStream.readInt(); //서버에서 보낼 파일이 몇개인지 알려줌

            //for(int i=0;i<resultCount;i++){ //여기 for문부터 쭉 돌면 됨
            String fileContent = dataInputStream.toString(); //전체 파일 내용 가져옴

            String fileContentsplit[] = fileContent.split(",");

            int index = Integer.parseInt(fileContentsplit[0]);
            fileOutputStream = new FileOutputStream(abPath += "/" + fileContentsplit[1] + ".mp4");
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
            Log.e("전송완료", "완료됨........................");

            String resultMsg = dataInputStream.readUTF();
            if (resultMsg.equals("전송완료")) {
                socket.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fileSetting() {

    }
}


















