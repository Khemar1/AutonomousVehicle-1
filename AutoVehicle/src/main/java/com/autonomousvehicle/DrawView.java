/**
 * Bilal Alfanous
 * N00994056
 */
package com.autonomousvehicle;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

import static android.content.ContentValues.TAG;

public class DrawView extends View {
    HashMap<String, String> contact;
    LinkedList<String> cord = new LinkedList<>();
    String jsonStr;
    Paint paint = new Paint();
    String[] arr2;

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
    }


    @Override
    public void onDraw(Canvas canvas) {

//        canvas.drawLine(50, 100, 100, 100, paint);
        //canvas.drawLine(500, 500, 100, 100, paint);
        //SELECT `x`, `y` FROM `your_tables_name` WHERE `id` = 'id_number_of_coordinations'
        int[][] arr = new int[10][2];
        arr[0][0] = 100;
        arr[0][1] = 100;
        arr[1][0] = 100;
        arr[1][1] = 200;
        arr[2][0] = 100;
        arr[2][1] = 300;
        arr[3][0] = 100;
        arr[3][1] = 400;
        arr[4][0] = 200;
        arr[4][1] = 400;
        arr[5][0] = 300;
        arr[5][1] = 400;
        arr[6][0] = 400;
        arr[6][1] = 400;
        arr[7][0] = 400;
        arr[7][1] = 600;
        arr[8][0] = 400;
        arr[8][1] = 700;
        arr[9][0] = 400;
        arr[9][1] = 900;


        //String[] arr = result.split(".");
        //String[] arr = result.replaceFirst("^,", "").split(",");


        final String url = "http://myloginappsite.site88.net/getxy.php";

        new GetContacts().execute();

//        contact.put("x", x);
//        contact.put("y", y);

//        Log.e(TAG, "res:   : " + jsonStr);

//
//        for (int y =0; y< arr2.length; y++){
//            Log.e(TAG, ":ARRAY:::: " + arr2[y]);
//        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < 2; j++) {
                canvas.drawPoint(arr[i][0], arr[i][1], paint);
            }
        }
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getContext(), "Json Data is downloading", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://myloginappsite.site88.net/getxy.php";
            jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            cord.add(0, jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("response");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("success");
                        String x = c.getString("x");
                        String y = c.getString("y");

                        // tmp hash map for single contact
                        contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("x", x);
                        contact.put("y", y);


                        // adding contact to contact list
                        // contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG).show();
//                    }
//                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList,
//                    R.layout.list_item, new String[]{ "email","mobile"},
//                    new int[]{R.id.email, R.id.mobile});
//            lv.setAdapter(adapter);

        }
    }

}