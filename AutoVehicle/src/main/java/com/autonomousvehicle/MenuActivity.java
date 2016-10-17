/**
 * JBK.17
 * */
package com.autonomousvehicle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button auto = (Button) findViewById(R.id.bAuto);

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iAuto = new Intent(MenuActivity.this, Auto.class);
                startActivity(iAuto);
            }
        });

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
                Intent logout = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(logout);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
