package com.eflake.efframework.demo;


import android.app.Activity;
import android.os.Bundle;

import com.eflake.efframework.ui.view.BezierView;

public class BezierActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BezierView(this));
    }
}
