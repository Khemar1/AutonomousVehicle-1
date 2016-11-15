/**
 * JBK.17
 * */
package com.autonomousvehicle;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText etuserName = (EditText)findViewById(R.id.etUsername);
        final EditText etpassword = (EditText)findViewById(R.id.etPassword);
        Button signIn = (Button) findViewById(R.id.bSignIn);
        TextView signUp = (TextView) findViewById(R.id.tvRegister);
        Switch rememMe = (Switch)findViewById(R.id.sRemember);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);



        if( saveLogin == true ){
            etuserName.setText(loginPreferences.getString("username", ""));
            rememMe.setChecked(true);
        }


        rememMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loginPrefsEditor.putBoolean("saveLogin", true);
                loginPrefsEditor.putString("username", etuserName.getText().toString());
                loginPrefsEditor.commit();
            }
        });
        //still doestn work!
        if(rememMe.isEnabled() == false){
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etuserName.getText().toString();
                final String passsword = etpassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                //  AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                //  builder.setMessage("Login GOOD!!")
                                //          .setNegativeButton("Retry", null)
                                //          .create()
                                //          .show();
                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, passsword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
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
            case R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                String title = getResources().getString(R.string.abTitle);
                String msg = getResources().getString(R.string.abMsg);
                String yes = getResources().getString(R.string.yes);
                String no = getResources().getString(R.string.no);


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                // set title
                alertDialogBuilder.setTitle(title);
                // set dialog message
                alertDialogBuilder
                        .setMessage(msg)
                        .setCancelable(false)
                        .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                LoginActivity.this.finish();

                                //display in short period of time
                                //Toast.makeText(getApplicationContext(), "Yes is clicked",
                                //      Toast.LENGTH_LONG).show();
                            }
                        })

                        .setNegativeButton(no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();

                                //display in short period of time
                                //Toast.makeText(getApplicationContext(), "No is clicked",
                                //   Toast.LENGTH_LONG).show();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
