/**
 * JBK.17
 */

package com.autonomousvehicle;

//import android.app.ActionBar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

public class RemoteControl extends AppCompatActivity {

    RelativeLayout layout_joystick;
    ImageView image_joystick, image_border;
    TextView textView1, textView2, textView3, textView4, textView5;

    JoyStick js;

    Sender command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        final EditText IPAdd = (EditText)findViewById(R.id.etIpAdd);
        Button close = (Button) findViewById(R.id.bClose);
        final Button connect = (Button) findViewById(R.id.bConnect);

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
                command.setIpAddress(IPAdd.getText().toString());
                command = new Sender();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                command.done();

            }
        });


        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
                    textView1.setText("X : " + String.valueOf(js.getX()));
                    textView2.setText("Y : " + String.valueOf(js.getY()));
                    textView3.setText("Angle : " + String.valueOf(js.getAngle()));
                    textView4.setText("Distance : " + String.valueOf(js.getDistance()));

                    int direction = js.get8Direction();
                    if (direction == JoyStick.STICK_UP) {
                           command.send("upp");
                        textView5.setText("Direction : Up");
                    } else if (direction == JoyStick.STICK_UPRIGHT) {
                        command.send("pivrr");
                        textView5.setText("Direction : Up Right");
                    } else if (direction == JoyStick.STICK_RIGHT) {
                           command.send("rightt");
                        textView5.setText("Direction : Right");
                    } else if (direction == JoyStick.STICK_DOWNRIGHT) {
                        textView5.setText("Direction : Down Right");
                    } else if (direction == JoyStick.STICK_DOWN) {
                           command.send("downn");
                        textView5.setText("Direction : Down");
                    } else if (direction == JoyStick.STICK_DOWNLEFT) {
                        textView5.setText("Direction : Down Left");
                    } else if (direction == JoyStick.STICK_LEFT) {
                        textView5.setText("Direction : Left");
                          command.send("leftt");
                    } else if (direction == JoyStick.STICK_UPLEFT) {
                        command.send("pivll");
                        textView5.setText("Direction : Up Left");
                    } else if (direction == JoyStick.STICK_NONE) {
                        textView5.setText("Direction : Center");
                    }
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    textView1.setText(R.string.x);
                    textView2.setText(R.string.y);
                    textView3.setText(R.string.angle);
                    textView4.setText(R.string.distance);
                    textView5.setText(R.string.direction);
                }
                return true;
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
