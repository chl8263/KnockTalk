package com.example.choi.knocktalk.Sound;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
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

public class Sound_Send extends Thread{
    private String ip = "223.194.159.151";
    /*private String ip = "203.132.186.167";*/
    private static final int AudioSampleRate = 44100;
    private static final int AudioChannel = AudioFormat.CHANNEL_OUT_STEREO;
    private static final int AudioBit = AudioFormat.ENCODING_PCM_16BIT;
    private static final int AudioMode = AudioTrack.MODE_STREAM;
    private int portnumber = 9002;
    @Override
    public void run() {
        super.run();
        Thread.currentThread().setName("SEND THREAD");
        boolean mic = true;
        Log.e("보냄시작", "ㅇㅇ" + Thread.currentThread().getName());
        AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC, AudioSampleRate, AudioChannel, AudioBit, AudioRecord.getMinBufferSize(AudioSampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT) * 5);
        int bytes_read = 0;
        int bytes_sent = 0;
        byte buffer[] = new byte[3528 * 5];
        try {
            Log.e("보냄성공!", "오짐");
            DatagramSocket socket = new DatagramSocket();
            if (record.getState() == AudioRecord.STATE_INITIALIZED) {
                record.startRecording();
            } else {
                Log.e("보내기실패!", "실패!!");
            }

            while (mic) {
                bytes_read = record.read(buffer, 0, buffer.length);
                DatagramPacket packet = new DatagramPacket(buffer, bytes_read, InetAddress.getByName(ip), portnumber);
                socket.send(packet);
                bytes_sent += bytes_read;
                Log.e("총보낸 byte 양", String.valueOf(bytes_sent));
                sleep(20, 0);
            }
            record.stop();
            record.release();
            socket.disconnect();
            socket.close();
            mic = false;
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
            mic = false;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            mic = false;
        } catch (SocketException e) {
            e.printStackTrace();
            mic = false;
        } catch (IOException e) {
            e.printStackTrace();
            mic = false;
        }
    }
}

