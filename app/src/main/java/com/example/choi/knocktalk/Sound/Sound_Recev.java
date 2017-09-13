package com.example.choi.knocktalk.Sound;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by choi on 17. 8. 20.
 */

public class Sound_Recev extends Thread {
    private static final int AudioSampleRate = 44100;
    private static final int AudioChannel = AudioFormat.CHANNEL_OUT_STEREO;
    private static final int AudioBit = AudioFormat.ENCODING_PCM_16BIT;
    private static final int AudioMode = AudioTrack.MODE_STREAM;
    private AudioTrack track;
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    byte[] data = new byte[1200];
    private Boolean rere = true;

    public Sound_Recev() {
        Log.e("recev_create", "good");
        try {
            socket = new DatagramSocket(9001);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        track = new AudioTrack(AudioManager.STREAM_MUSIC, AudioSampleRate, AudioChannel, AudioBit, track.getMinBufferSize(AudioSampleRate, AudioChannel, AudioBit), AudioTrack.MODE_STREAM);
        track.play();
        Log.e("받음", "ㅆ");
    }

    @Override
    public void run() {
        super.run();
        try {
            Log.e("받음", "ㅆasdasd");
            while (rere) {
                packet = new DatagramPacket(data,data.length);
                socket.receive(packet);
                Log.e("받음", "ㅇㅇ");
                track.write(data, 0, data.length);
            }
            track.flush();
            track.play();
            //track.release();
            track = null;
        } catch (SocketException e) {
            e.printStackTrace();
            Log.e("받음", "ㄴㄴ");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("받음", "ㄴㄴ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
