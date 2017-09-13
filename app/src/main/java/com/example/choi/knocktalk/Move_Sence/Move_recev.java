package com.example.choi.knocktalk.Move_Sence;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by choi on 17. 9. 13.
 */

public class Move_recev extends Thread {
    private DatagramPacket packet =null;
    private DatagramSocket socket = null;
    byte[] data = new byte[4];
    public Move_recev() {
        try {
            socket = new DatagramSocket(9006);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();

        try {
            packet = new DatagramPacket(data,data.length);
            socket.receive(packet);
            String msg = new String(packet.getData(),0,packet.getLength());
            Log.e("MOVE_RECEV",msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
