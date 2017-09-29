package com.example.choi.knocktalk.Door_open;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by choi on 17. 9. 28.
 */

public class Door_open extends Thread {
    private String ip = "192.168.0.2";
    private int portnumber = 9000;
    @Override
    public void run() {
        super.run();
        DatagramSocket socket = null;
        DatagramPacket packet = null;
        InetAddress ServerAddress = null;
        String msg = "OPEN";
        byte protocalBytes[] = new byte[4];
        protocalBytes = msg.getBytes();

        try {
            ServerAddress = InetAddress.getByName(ip);
            packet = new DatagramPacket(protocalBytes, protocalBytes.length, ServerAddress, portnumber);
            socket = new DatagramSocket();
            socket.send(packet);

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
