package com.autonomousvehicle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Set;
import java.util.UUID;

import static java.lang.System.exit;

public class RemoteBTActivity extends AppCompatActivity {

    RelativeLayout layout_joystick;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    PrintWriter mmOutputStream;
    InputStream mmInputStream;
    JoyStick js;
    TextView directiontv;
    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_bt);

        directiontv = (TextView) findViewById(R.id.directiontv);

        final Button connect = (Button) findViewById(R.id.bConnect);
        Button close = (Button) findViewById(R.id.bClose);

        layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick1);

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

                a = "raspberrypi";
                try {
                    findBT();
                } catch (IOException e) {
                    Toast.makeText(getBaseContext(), "Connection not established with the robot", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }




            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                   mmSocket.close();
               }
               catch (IOException e){
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
                        //     mmOutputStream.println("upp");
                        directiontv.setText(R.string.up);
                    } else if (direction == JoyStick.STICK_UPRIGHT) {

                        directiontv.setText(R.string.upright);
                    } else if (direction == JoyStick.STICK_RIGHT) {
                        // mmOutputStream.println("rightt");
                        directiontv.setText(R.string.right);
                    } else if (direction == JoyStick.STICK_DOWNRIGHT) {
                        directiontv.setText(R.string.downright);
                    } else if (direction == JoyStick.STICK_DOWN) {
                        //   mmOutputStream.println("downn");
                        directiontv.setText(R.string.down);
                    } else if (direction == JoyStick.STICK_DOWNLEFT) {
                        directiontv.setText(R.string.downleft);
                    } else if (direction == JoyStick.STICK_LEFT) {
                        directiontv.setText(R.string.left);
                        //  mmOutputStream.println("downn");
                    } else if (direction == JoyStick.STICK_UPLEFT) {

                        directiontv.setText(R.string.upleft);
                    } else if (direction == JoyStick.STICK_NONE) {
                        directiontv.setText("");
                    }
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    directiontv.setText(null);
                }
                return true;
            }
        });




    }

    void findBT() throws IOException
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
         if(mBluetoothAdapter == null)
        {
            exit(0);
        //  myLabel.setText("No bluetooth adapter available");
        }

        if(!mBluetoothAdapter.isEnabled())
        {
           // Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
           // startActivityForResult(enableBluetooth, 1);

        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals(a))//FireFly-B1A7Change to the name of your bluetooth module (Case sensitive)
                {
                    mmDevice = device;
                    break;
                }
            }
        }
        // myLabel.setText("Bluetooth Device Found");


        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard //SerialPortService ID

        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        try{
            mmSocket.connect();

        }catch(IOException e){
           // Toast.makeText(getBaseContext(), "Connection not established with the robot", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            exit(0);


        }
        finally {
            mmOutputStream = new PrintWriter(mmSocket.getOutputStream());
            mmInputStream = mmSocket.getInputStream();

        }

        //  beginListenForData();

        // myLabel.setText("Bluetooth Opened");
    }





}
