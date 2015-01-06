package com.eflake.efframework.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class BaseSurfaceView extends SurfaceView implements Callback, Runnable {

    private static final long REFRESH_RATE = 50;
    private SurfaceHolder surfaceHolder;
    private int screenWidth;
    private int screenHeight;
    private Paint paint;
    private Thread drawThread;
    private Canvas canvas;
    private static boolean isRuning;

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenWidth = this.getWidth();
        screenHeight = this.getHeight();
        Log.d("eflake", "*********screen width:" + screenWidth
                + "screen height:" + screenHeight);
        Log.d("eflake", "create");
        paint = new Paint();
        paint.setAntiAlias(true);
        BaseSurfaceView.isRuning = true;
        drawThread = new Thread(this);
        drawThread.start();
    }

    @Override
    public void run() {
        while (isRuning) {
            long startTime = System.currentTimeMillis();
            synchronized (surfaceHolder) {
                draw();
            }
            long endTime = System.currentTimeMillis();
            try {
                Thread.sleep(REFRESH_RATE - (endTime - startTime));
            } catch (Exception e) {
            }
        }
    }

    private void draw() {
        try {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.GRAY);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(10);
            canvas.drawLine(0, 0, 1000, 1000, paint);
        } catch (Exception e) {
            Log.d("eflake", e.toString());
        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        BaseSurfaceView.isRuning = false;
        Log.d("eflake", "destory");
    }

}
