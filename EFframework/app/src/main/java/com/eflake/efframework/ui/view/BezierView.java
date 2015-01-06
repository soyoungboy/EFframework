package com.eflake.efframework.ui.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.eflake.efframework.R;

public class BezierView extends View {

    private String name;
    private float mX;
    private float mY;

    private final Paint mGesturePaint = new Paint();
    private final Path mPath = new Path();

    public BezierView(Context context) {
        this(context, null);
        mGesturePaint.setAntiAlias(true);
        mGesturePaint.setStyle(Style.STROKE);
        mGesturePaint.setStrokeWidth(5);
        mGesturePaint.setColor(Color.BLUE);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // Load the styled attributes and set their properties
        final TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.BaseViewAttr, defStyleAttr,
                0);
        float width = attributes
                .getDimension(R.styleable.BaseViewAttr_base_width, 0);
        float height = attributes.getDimension(R.styleable.BaseViewAttr_base_height,
                0);
        float alpha = attributes.getFloat(R.styleable.BaseViewAttr_base_alpha, 0);
        boolean visible = attributes.getBoolean(
                R.styleable.BaseViewAttr_base_visible, true);
        int size = attributes.getInteger(R.styleable.BaseViewAttr_base_size, 0);
        int color = attributes.getColor(R.styleable.BaseViewAttr_base_color,
                0xffffffff);
        float complete = attributes.getFraction(
                R.styleable.BaseViewAttr_base_complete, 10, 1, 10);
        name = attributes.getString(R.styleable.BaseViewAttr_base_name);
        Log.d("eflake", "width = " + width + ", height = " + height
                + ", alpha = " + alpha + ", visible = " + visible + ", size = "
                + size + ", color = " + color + ", complete = " + complete
                + ", name = " + name);
        // Release the attributes memory
        attributes.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("eflake", "width=" + MeasureSpec.getSize(widthMeasureSpec)
                + ",height=" + MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(event);
        }
        //���»���
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //ͨ����ƶ���γɵ�ͼ��
        canvas.drawPath(mPath, mGesturePaint);
    }

    //��ָ������Ļʱ����
    private void touchDown(MotionEvent event) {

        //mPath.rewind();
        //���û���·�ߣ�������֮ǰ���ƵĹ켣
        mPath.reset();
        float x = event.getX();
        float y = event.getY();

        mX = x;
        mY = y;
        //mPath���ƵĻ������
        mPath.moveTo(x, y);
    }

    //��ָ����Ļ�ϻ���ʱ����
    private void touchMove(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();

        final float previousX = mX;
        final float previousY = mY;

        final float dx = Math.abs(x - previousX);
        final float dy = Math.abs(y - previousY);

        //����֮��ľ�����ڵ���3ʱ����ɱ�����������
        if (dx >= 3 || dy >= 3) {
            //���ñ�������ߵĲ�����Ϊ�����յ��һ��
            float cX = (x + previousX) / 2;
            float cY = (y + previousY) / 2;

            //���α����ʵ��ƽ�����ߣ�previousX, previousYΪ�����㣬cX, cYΪ�յ�
            mPath.quadTo(previousX, previousY, cX, cY);

            //�ڶ���ִ��ʱ����һ�ν�����õ����ֵ����Ϊ�ڶ��ε��õĳ�ʼ���ֵ
            mX = x;
            mY = y;
        }
    }
}
