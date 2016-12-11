/**
 * Bilal Alfanous
 * N00994056
 * */
package com.autonomousvehicle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

public class Mapping extends AppCompatActivity {
    DrawView drawView;
    Paint paint = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);

        //both on clicks dont work!!
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView = new DrawView(getBaseContext());
                drawView.setBackgroundColor(Color.YELLOW);
            }
        });
//        start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                drawView.clearAnimation();
//            }
//        });
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
                Mapping.this.onBackPressed();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

}
