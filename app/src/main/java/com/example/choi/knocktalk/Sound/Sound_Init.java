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

public class Sound_Init extends Thread{
    private String ip = "192.168.0.2";
    private int portnumber = 9000;
    /*private String ip = "203.132.186.167";*/
    @Override
    public void run() {
        super.run();
        Thread.currentThread().setName("INIT THREAD");
        DatagramSocket socket = null;
        DatagramPacket packet = null;
        InetAddress ServerAddress = null;
        String msg = "CONN";
        byte protocalBytes[] = new byte[4];
        protocalBytes = msg.getBytes();
        try {
            Log.e("init", "init ok");
            ServerAddress = InetAddress.getByName(ip);
            packet = new DatagramPacket(protocalBytes, protocalBytes.length, ServerAddress, portnumber);
            socket = new DatagramSocket();
            socket.send(packet);
            Log.e("init", "init dead");

        } catch (UnknownHostException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}