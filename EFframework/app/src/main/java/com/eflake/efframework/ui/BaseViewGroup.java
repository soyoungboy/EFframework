package com.eflake.efframework.ui;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import com.eflake.efframework.R;

public class BaseViewGroup extends ViewGroup {
    private Paint textpaint;
    private String name;

    public BaseViewGroup(Context context) {
        this(context, null);
    }

    public BaseViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // Load the styled attributes and set their properties
        final TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.BaseViewAttr, defStyle,
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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

}
