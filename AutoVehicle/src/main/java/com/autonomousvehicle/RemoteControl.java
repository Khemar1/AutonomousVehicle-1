/**
 * JBK.17
 * */

package com.autonomousvehicle;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RemoteControl extends AppCompatActivity {

   // ActionBar actionBar = getActionBar();

  //  private JoystickView joyStickCanvas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // joyStickCanvas = (JoystickView) findViewById(R.id.joystick);
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

            case android.R.id.home:
                RemoteControl.this.onBackPressed();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


}
