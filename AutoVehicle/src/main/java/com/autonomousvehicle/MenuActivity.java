/**
 * JBK.17
 * */
package com.autonomousvehicle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);




        Button map = (Button) findViewById(R.id.bMapping);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMap = new Intent(MenuActivity.this, Mapping.class);
                startActivity(iMap);
            }
        });


        Button remote = (Button) findViewById(R.id.bRemote);

        remote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iAuto = new Intent(MenuActivity.this, RemoteControl.class);
                startActivity(iAuto);
            }
        });

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
            case R.id.logout:
                //Intent logout = new Intent(MenuActivity.this, LoginActivity.class);
                //startActivity(logout);
                MenuActivity.this.finish();
                return true;

            case R.id.settings:
                Intent setting = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(setting);
                return true;

            case R.id.about:

                LayoutInflater inflater2 = (LayoutInflater)getApplicationContext().getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout ll2 = (LinearLayout)inflater2.inflate(R.layout.activity_about, null, false);

                //EditText ed = (EditText)findViewById(R.id.etIpAddress1);
                //final CalendarView calendarView = (CalendarView)ll.findViewById(R.id.calendarID);
                AlertDialog   alertDate2 = new AlertDialog.Builder(MenuActivity.this)
                        // .setTitle("Event Calendar")
                        //.setMessage("Click to schedule or view events.")
                        .setView(ll2)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                }
                        ).show();

                //setContentView(R.layout.activity_settings);

                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
