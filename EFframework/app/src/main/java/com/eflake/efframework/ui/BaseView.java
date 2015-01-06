package com.eflake.efframework.ui;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.eflake.efframework.R;

public class BaseView extends View {

    private Paint textpaint;
    private String name;

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        // Cusomt init
        textpaint = new Paint();
        textpaint.setTextSize(25);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("eflake", "width=" + MeasureSpec.getSize(widthMeasureSpec) + ",height=" + MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(0, 0, (float) canvas.getWidth(), textpaint);
        Log.d("eflake", "width=" + (float) canvas.getWidth());
    }
}
