/**
 * Bilal Alfanous
 * N00994056
 * JBK.17
 * */
package com.autonomousvehicle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static java.lang.System.exit;

public class Mapping extends AppCompatActivity {

    HashMap<String, String> contact;
    LinkedList<String> cord = new LinkedList<>();
    String jsonStr;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    String a,getmap;
    boolean connected;
    TextView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);
        map = (TextView) findViewById(R.id.mapview);
        String title = getString(R.string.noipsettings);
        String msg = getString(R.string.BTQuestion);
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
        final AlertDialog alertDialog = alertDialogBuilder.create();

        if (!isBluetoothAvailable())
            alertDialog.show();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = "raspberrypi";
                if (isBluetoothAvailable())
                {
                    try {
                        findBT();
                    } catch (IOException e) {
                        Toast.makeText(getBaseContext(), "Connection not established with the robot", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    Toast.makeText(getBaseContext(), "Mapping started", Toast.LENGTH_LONG).show();
                    connected = true;
                    sendMsg("go");
                }
                else
                    Toast.makeText(getBaseContext(), "Go to settings & turn on bluetooth", Toast.LENGTH_LONG).show();


            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connected){
                    Toast.makeText(getBaseContext(), "Stopping car", Toast.LENGTH_SHORT).show();
                    sendMsg("stop");
                    try{
                        getmap = mmInputStream.toString();
                        map.setText(getmap);
                        map.setVisibility(View.VISIBLE);

                        mmSocket.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    Toast.makeText(getBaseContext(), "Map retrieved!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getBaseContext(), R.string.noconnection,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



        new GetContacts().execute();

    }

    public static boolean isBluetoothAvailable() {
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (bluetoothAdapter != null && bluetoothAdapter.isEnabled());
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
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

        }

        //  beginListenForData();

        // myLabel.setText("Bluetooth Opened");
    }

    void sendMsg(String msg1){
        try{
            mmOutputStream.write(msg1.getBytes());
        }catch (IOException e){
            exit(0);
        }

    }



    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getBaseContext(), "Json Data is downloading", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://myloginappsite.site88.net/getxy.php";
            jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            cord.add(0, jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("response");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("success");
                        String x = c.getString("x");
                        String y = c.getString("y");

                        // tmp hash map for single contact
                        contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("x", x);
                        contact.put("y", y);


                        // adding contact to contact list
                        // contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG).show();
//                    }
//                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList,
//                    R.layout.list_item, new String[]{ "email","mobile"},
//                    new int[]{R.id.email, R.id.mobile});
//            lv.setAdapter(adapter);

        }
    }

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
                Intent setting = new Intent(Mapping.this, SettingsActivity.class);
                startActivity(setting);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
