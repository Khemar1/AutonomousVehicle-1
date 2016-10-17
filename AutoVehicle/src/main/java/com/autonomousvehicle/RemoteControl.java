package com.autonomousvehicle;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RemoteControl extends AppCompatActivity {

    ActionBar actionBar = getActionBar();

    private JoystickView joyStickCanvas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);

        actionBar.setDisplayHomeAsUpEnabled(true);
        //joyStickCanvas = (JoystickView) findViewById(R.id.joystick);
    }
}
