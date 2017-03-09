/**
 * Bilal Alfanous
 * N00994056
 * JBK.17
 * */
package com.autonomousvehicle;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Mapping extends AppCompatActivity {
    DrawView drawView;
    Paint paint = new Paint();
    static Socket socket = null;
    static PrintWriter out = null;
    static BufferedReader remoteInput = null;
    public static String ipAddress = null;
    public static boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);


  //      final TextView IPAdd = (TextView) findViewById(R.id.IpAdd);
        SharedPreferences preferences2 = getSharedPreferences("ip", MODE_PRIVATE);
        String name = preferences2.getString("ip", "");

        if (!name.equalsIgnoreCase("")) {
    //        IPAdd.setText(name);  /* Edit the value here*/
        } else {
            // IPAdd.setText(R.string.noipsettings);
            String title = getString(R.string.noipsettings);
            String msg = getString(R.string.wouldyou);
            String yes = getString(R.string.yes);
            String no = getString(R.string.later);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // set title
            alertDialogBuilder.setTitle(title);
            // set dialog message
            alertDialogBuilder
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent in = new Intent(Mapping.this, SettingsActivity.class);
                            startActivity(in);
                        }
                    })

                    .setNegativeButton(no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
        //both on clicks dont work!!
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView = new DrawView(getBaseContext());
                drawView.setBackgroundColor(Color.YELLOW);
            }
        });
//        start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                drawView.clearAnimation();
//            }
//        });
    }
        private class Serv extends AsyncTask<Void, Void, Void> {



            protected Void doInBackground(Void...voids){

                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    socket = new Socket(getIpAddress(), 40093);

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


                return null;

            }
        }
    public static void setIpAddress(String ipAddress) {
        RemoteControl.ipAddress = ipAddress;
    }

    public static String getIpAddress() {
        return ipAddress;
    }

    void send(String msg) {
        out.println(msg);
    }

    public static void setExit(boolean exit){RemoteControl.exit = exit;}

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
            socket.close();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
