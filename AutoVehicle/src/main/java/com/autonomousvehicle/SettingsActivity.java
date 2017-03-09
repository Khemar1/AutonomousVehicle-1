/**
 * JBK.17
 * */
package com.autonomousvehicle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText ipAddress;
    Button b1,b2,b3;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        b1 = (Button) findViewById(R.id.buttonon);
        b2= (Button)findViewById(R.id.buttonoff);
        b3 = (Button)findViewById(R.id.visible);

        BA = BluetoothAdapter.getDefaultAdapter();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BA.isEnabled()) {
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, 0);
                    Toast.makeText(getApplicationContext(), "Turned on",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
                }
            }
        });

       b2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               BA.disable();
               Toast.makeText(getApplicationContext(), "Turned off" ,Toast.LENGTH_LONG).show();
           }
       });

       b3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
               startActivityForResult(getVisible, 0);
           }
       });
        ipAddress = (EditText) findViewById(R.id.etIPAddress);
        preferences = getSharedPreferences("ip", MODE_PRIVATE);
        editor = preferences.edit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
         /* hiding the overflow in this activity*/
        MenuItem item = menu.findItem(R.id.settings);
        MenuItem item2 = menu.findItem(R.id.logout);
        MenuItem item3 = menu.findItem(R.id.about);
        item.setVisible(false);
        item2.setVisible(false);
        item3.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                editor.putString("ip",ipAddress.getText().toString());
                //editor.apply();
                editor.commit();
                SettingsActivity.this.finish();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
