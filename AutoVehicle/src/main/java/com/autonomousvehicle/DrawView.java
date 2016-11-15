/**
 * JBK.17
 * */
package com.autonomousvehicle;

/**
 * Created by Bilfnous on 11/15/2016.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.view.View;

public class DrawView extends View {
    Paint paint = new Paint();

    public DrawView(Context context) {
        super(context);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);

    }



    @Override
    public void onDraw(Canvas canvas) {


        canvas.drawLine(50, 100, 100, 100, paint);
        canvas.drawLine(100, 100, 100, 200, paint);
       // canvas.drawLine(50, 50, 0, 100, paint);
    }

}