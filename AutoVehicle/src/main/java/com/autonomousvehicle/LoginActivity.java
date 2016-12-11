/**
 * JBK.17
 * */
package com.autonomousvehicle;



import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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

                if (isChecked) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", etuserName.getText().toString());
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

            }
        });

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
                boolean good = true;

                if(username.isEmpty() && passsword.isEmpty()){
                    Toast.makeText(getBaseContext(), R.string.bothreq,
                            Toast.LENGTH_LONG).show();
                    good = false;
                }else if(username.isEmpty()){
                    Toast.makeText(getBaseContext(), R.string.usernamereq,
                            Toast.LENGTH_LONG).show();
                    good = false;
                }else if(passsword.isEmpty()){
                    Toast.makeText(getBaseContext(), R.string.passwordreq,
                            Toast.LENGTH_LONG).show();
                    good = false;
                }else if (good){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                    startActivity(intent);

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setTitle(getString(R.string.recordmatch))
                                            .setMessage(R.string.doublecheck)
                                            .setNegativeButton(R.string.retry, null)
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
            }
        });
    }

    @Override
    // Handle the Back Key
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.exitquestion)
                .setMessage(R.string.sure)
                .setNegativeButton(android.R.string.no, null)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        LoginActivity.super.onBackPressed();
                    }
                }).create().show();
    }




}
