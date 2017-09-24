package com.example.choi.knocktalk.Sound;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by choi on 17. 8. 20.
 */

public class Sound_Exit extends Thread {
    private String ip = "192.168.0.2";
    private int portnumber = 9000;
    /*private String ip = "203.132.186.167";*/
    @Override
    public void run() {
        super.run();
        Thread.currentThread().setName("EXIT THREAD");
        DatagramSocket socket = null;
        DatagramPacket packet = null;
        InetAddress ServerAddress = null;
        String msg = "EXIT";
        byte protocalBytes[] = new byte[4];
        protocalBytes = msg.getBytes();

        try {
            Log.e("exit", "exit send");
            ServerAddress = InetAddress.getByName(ip);
            packet = new DatagramPacket(protocalBytes, protocalBytes.length, ServerAddress, portnumber);
            socket = new DatagramSocket();
            socket.send(packet);
            Log.e("exit", "exit dead");

        } catch (UnknownHostException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            Log.i("send", "destory no");
        } catch (SocketException e) {
            e.printStackTrace();
            Log.i("send", "destory no");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("send", "destory no");
        }
    }
}
