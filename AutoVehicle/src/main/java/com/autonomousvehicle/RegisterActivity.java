/**
 * JBK.17
 */
package com.autonomousvehicle;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle(getString(R.string.Register));

        final EditText etname = (EditText) findViewById(R.id.etName);
        final EditText etuserName = (EditText) findViewById(R.id.etUsername);
        final EditText etpassword = (EditText) findViewById(R.id.etPassword);
        final EditText etRePass = (EditText) findViewById(R.id.etRePass);
        Button register = (Button) findViewById(R.id.bRegister);

        final TextView nameErr = (TextView) findViewById(R.id.tvNameError);
        final TextView uNameErr = (TextView) findViewById(R.id.tvUnamError);
        final TextView passErr = (TextView) findViewById(R.id.tvPassError);
        final TextView rePassErr = (TextView) findViewById(R.id.tvReError);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = etname.getText().toString();
                final String user_name = etuserName.getText().toString();
                final String password = etpassword.getText().toString();
                final String rePass = etRePass.getText().toString();
                if (Objects.equals(password, rePass)) {

                        passErr.setText("");
//
                    if (name.isEmpty() || user_name.isEmpty() || password.isEmpty() || rePass.isEmpty()) {

                        if (name.isEmpty()) {
                            nameErr.setText(getString(R.string.missingname));
                        }else{
                            nameErr.setText("");
                        }if (user_name.isEmpty()) {
                            uNameErr.setText(getString(R.string.missinguser));
                        }else{
                            uNameErr.setText("");
                        }if (password.isEmpty()) {
                            passErr.setText(getString(R.string.missingpassword));
                        }else{
                            passErr.setText("");
                        }if (rePass.isEmpty()) {
                            rePassErr.setText(getString(R.string.missingreenter));
                        }else{
                            rePassErr.setText("");
                        }

                    } else {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                        builder.setMessage(getString(R.string.registerfail))
                                                .setNegativeButton(R.string.retry, null)
                                                .create()
                                                .show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        RegisterRequest registerRequest = new RegisterRequest(name, user_name, password, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                        queue.add(registerRequest);
                    }
                }else if (!Objects.equals(password, rePass)){
                    passErr.setText(getString(R.string.missingreenter));

                }

            }
        });

    }


}
