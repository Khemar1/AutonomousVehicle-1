/**
 * Bilal Alfanous
 * N00994056
 */
package com.autonomousvehicle;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class DrawView extends View {
    Paint paint = new Paint();

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

        final String url = "http://myloginappsite.site88.net/getxy.php";

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < 2; j++) {
                canvas.drawPoint(arr[i][0], arr[i][1], paint);
            }
        }
    }

}