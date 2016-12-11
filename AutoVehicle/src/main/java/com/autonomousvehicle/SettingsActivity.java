package com.autonomousvehicle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText ipAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
     //   getSupportActionBar().setTitle(R.string.settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ipAddress = (EditText) findViewById(R.id.etIPAddress);
        preferences = getSharedPreferences("ip", MODE_PRIVATE);
        editor = preferences.edit();
    }
//
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
