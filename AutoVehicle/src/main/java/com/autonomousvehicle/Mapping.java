/**
 * JBK.17
 * */
package com.autonomousvehicle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class Mapping extends AppCompatActivity {

    Chronometer mChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button button;

        mChronometer = (Chronometer) findViewById(R.id.chronometer);

        button = (Button) findViewById(R.id.start);
        button.setOnClickListener(mStartListener);

        button = (Button) findViewById(R.id.stop);
        button.setOnClickListener(mStopListener);

    }


    View.OnClickListener mStartListener = new View.OnClickListener() {
        public void onClick(View v) {
            mChronometer.start();
        }
    };

    View.OnClickListener mStopListener = new View.OnClickListener() {
        public void onClick(View v) {
            mChronometer.stop();
        }
    };

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
                Mapping.this.onBackPressed();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

}
