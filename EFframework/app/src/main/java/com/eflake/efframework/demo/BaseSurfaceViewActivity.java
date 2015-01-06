package com.eflake.efframework.demo;

import android.app.Activity;
import android.os.Bundle;

import com.eflake.efframework.R;

/**
 * Activity which contains a simple base custom surface view
 *
 * @author Eflake
 * @version 1.0
 * @since 2014.03.04
 */
public class BaseSurfaceViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_surface_view);
    }
}
