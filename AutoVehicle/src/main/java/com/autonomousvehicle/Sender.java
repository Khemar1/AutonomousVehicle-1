package com.autonomousvehicle;

/**
 * Created by John Yalda on 2016-11-10.
 */

/*
 * Jan Yalda, N01037094
 * CENG320 - Lab3
 * October 23, 2016
 * client.java
 * */

import android.os.StrictMode;

import java.io.*;
import java.net.*;

class Sender extends Socket {
    static Socket socket = null;
    static PrintWriter out = null;
    static BufferedReader remoteInput = null;

    public Sender() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //socket = new Socket("192.168.15.113", 40093);
            socket = new Socket("169.254.239.23", 40093);
            out = new PrintWriter(socket.getOutputStream(), true);
            remoteInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            //System.out.println("Could not send to " + machineName + ":"+ port);
            e.printStackTrace();
            System.exit(-1);
        }

    }


    void send(String msg) {
        out.println(msg);
        //out.write(msg);
    }

    /*method to get the msg from server
     * @return String msg
     * */
    String getit() {
        String msg = null;
        try {
            msg = remoteInput.readLine();
            return msg;
            //System.out.println("Msg from server: "+msg);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return msg = "nothing gotten";
    }

    /*method to close the socket*/
    void done() {
        try {
            socket.close();
            //System.out.println("Msg from server: "+msg);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public static void main(String argv[]) throws IOException {


    }
}
