package com.aad.drawingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PaintView extends View {

    private Paint paint;
    private Canvas mCanvas;
    private Bitmap mBitmap;

    public int brushColor = Color.BLACK;
    public int brushSize = 20;

    public PaintView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setXfermode(null);
        paint.setAlpha(0xff);

    }

    public void init(DisplayMetrics metrics){

        int height = (int) (metrics.heightPixels * 0.9);
        int width = metrics.widthPixels;

         mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

    }

    private ArrayList<MainActivity.FingerPath> paths = new ArrayList<>();
    private Paint bitmapPaint = new Paint(Paint.DITHER_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();
        mCanvas.drawColor(Color.WHITE);

        for(MainActivity.FingerPath fingerPath : paths){

            paint.setColor(fingerPath.color);
            paint.setStrokeWidth(fingerPath.strokeWidth);
            paint.setMaskFilter(null);

            mCanvas.drawPath(fingerPath.path, paint);

        }

        canvas.drawBitmap(mBitmap, 0, 0, bitmapPaint);
        canvas.restore();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:

                touchStart(x, y);
                invalidate();

                break;

            case MotionEvent.ACTION_MOVE:

                touchMove(x, y);
                invalidate();

                break;

            case MotionEvent.ACTION_UP:

                touchUp();
                invalidate();

                break;

        }

        return true;

    }

    private void touchUp(){

        mPath.lineTo(mX, mY);

    }

    private static final float TOUCH_TOLERANCE = 4;

    private void touchMove(float x, float y){

        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE){

            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;

        }

    }

    private Path mPath;
    private float mX, mY;

    private void touchStart(float x, float y){

        mPath = new Path();
        MainActivity.FingerPath fingerPath = new MainActivity.FingerPath(brushColor, brushSize, mPath);
        paths.add(fingerPath);

        mPath.reset();
        mPath.moveTo(x, y);

        mX = x;
        mY = y;

    }

    public void clear(){

        paths.clear();
        invalidate();

    }

}
