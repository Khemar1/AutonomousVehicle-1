/**
 * JBK.17
 * */
package com.autonomousvehicle;

/**
 * Created by John Yalda on 2016-11-10.
 */



import android.os.StrictMode;

import java.io.*;
import java.net.*;

class Sender extends Socket {
    static Socket socket = null;
    static PrintWriter out = null;
    static BufferedReader remoteInput = null;
    static String ipAddress = null;
    public static boolean exit = false;

    public Sender() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

//            InetAddress add = new InetAddress.getByName();

            socket = new Socket(getIpAddress(), 40093);
           // out = new PrintWriter(socket.getOutputStream(), true);
         //   remoteInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));



        } catch (IOException e) {

            e.printStackTrace();
            System.exit(-1);

        }
       finally{

            if (socket!=null){
                try{
                    socket.close();
                }catch (Exception e) {
                    exit = true;
                    setExit(exit);
                }
            }
        }

    }



    public static void setIpAddress(String ipAddress) {
        Sender.ipAddress = ipAddress;
    }



    public static String getIpAddress() {
        return ipAddress;
    }

    void send(String msg) {
        out.println(msg);

    }

    public static void setExit(boolean exit){Sender.exit = exit;}

    public boolean getExit(){ return exit;}

    /*method to get the msg from server
     * @return String msg
     * */
    String getit() {
        String msg = null;
        try {
            msg = remoteInput.readLine();
            return msg;

        } catch (Exception e) {
            // TODO: handle exception
        }
        return msg = "nothing received";
    }

    /*method to close the socket*/
    void done() {
        try {
            Sender.socket.close();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public static void main(String argv[]) throws IOException {


    }
}
