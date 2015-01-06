package com.eflake.efframework.demo;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.eflake.efframework.R;

/**
 * Activity which contains a simple base custom view
 *
 * @author Eflake
 * @version 1.0
 * @since 2014.03.04
 */
public class BaseViewActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("eflake","baseview");
        setContentView(R.layout.activit_base_view);
    }
}
