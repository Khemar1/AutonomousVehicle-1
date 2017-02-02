/**
 * JBK.17
 */

package com.autonomousvehicle;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class RemoteControl extends AppCompatActivity {

    RelativeLayout layout_joystick;
    boolean connected = false;
    JoyStick js;
    TextView directiontv;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    Sender command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        directiontv = (TextView) findViewById(R.id.directiontv);

        final TextView IPAdd = (TextView) findViewById(R.id.IpAdd);
        Button close = (Button) findViewById(R.id.bClose);
        final Button connect = (Button) findViewById(R.id.bConnect);

        SharedPreferences preferences2 = getSharedPreferences("ip", MODE_PRIVATE);
        String name = preferences2.getString("ip", "");

        if (!name.equalsIgnoreCase("")) {
            IPAdd.setText(name);  /* Edit the value here*/
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
                            Intent in = new Intent(RemoteControl.this, SettingsActivity.class);
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


        layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);

        js = new JoyStick(getApplicationContext()
                , layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);


        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(IPAdd.getText().equals(getString(R.string.IPAddress))){
                    Toast.makeText(getBaseContext(), R.string.enterip,
                            Toast.LENGTH_SHORT).show();
                    connected = false;
                } else {
                    command.setIpAddress(IPAdd.getText().toString());

                    command = new Sender();
                    if (!command.getExit() )
                        connected = true;
                    else {
                        Toast.makeText(getBaseContext(), R.string.noconnection,
                                Toast.LENGTH_SHORT).show();
                        connected = false;
                    }
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connected != false){
                    command.done();
                }else {
                    Toast.makeText(getBaseContext(), R.string.noconnection,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {


                    int direction = js.get8Direction();
                    if (direction == JoyStick.STICK_UP) {

                       directiontv.setText(R.string.up);
                    } else if (direction == JoyStick.STICK_UPRIGHT) {

                        directiontv.setText(R.string.upright);
                    } else if (direction == JoyStick.STICK_RIGHT) {

                        directiontv.setText(R.string.right);
                    } else if (direction == JoyStick.STICK_DOWNRIGHT) {
                        directiontv.setText(R.string.downright);
                    } else if (direction == JoyStick.STICK_DOWN) {

                        directiontv.setText(R.string.down);
                    } else if (direction == JoyStick.STICK_DOWNLEFT) {
                        directiontv.setText(R.string.downleft);
                    } else if (direction == JoyStick.STICK_LEFT) {
                        directiontv.setText(R.string.left);

                    } else if (direction == JoyStick.STICK_UPLEFT) {

                        directiontv.setText(R.string.upleft);
                    } else if (direction == JoyStick.STICK_NONE) {
                        directiontv.setText("");
                    }
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {

                }
                return true;
            }
        });

    }

/*
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
               // Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                  //  Log.e(TAG, "Could not close the client socket", closeException);
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
           // manageMyConnectedSocket(mmSocket);
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                //Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                // User chose the "Settings" item, show the app settings UI...
                return true;



            case R.id.settings:
                Intent setting = new Intent(RemoteControl.this, SettingsActivity.class);
                startActivity(setting);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


}
