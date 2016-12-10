/**
 * Bilal Alfanous
 * N00994056
 * */
package com.autonomousvehicle;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;

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
//        canvas.drawLine(100, 100, 100, 200, paint);
        int[][] arr = new int[4][2];

        arr[0][0] = 100;
        arr[1][0] = 100;
        arr[0][1] = 100;
        arr[1][1] = 200;

        arr[2][0] = 50;
        arr[3][0] = 100;
        arr[2][1] = 100;
        arr[3][1] = 100;

        for(int i = 0; i < 3; i++)
            canvas.drawLine(arr[i][i], arr[i+1][i], arr[i][i+1], arr[i+1][i+1], paint);

    }

}