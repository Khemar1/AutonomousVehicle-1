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

        canvas.drawLine(50, 100, 100, 100, paint);
        canvas.drawLine(100, 100, 100, 200, paint);
    }

}