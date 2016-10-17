/**
 * JBK.17
 * */

package com.autonomousvehicle;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class RemoteControl extends AppCompatActivity {

    ActionBar actionBar = getActionBar();

    private JoystickView joyStickCanvas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);

        actionBar.setDisplayHomeAsUpEnabled(true);
        joyStickCanvas = (JoystickView) findViewById(R.id.joystick);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }


}
