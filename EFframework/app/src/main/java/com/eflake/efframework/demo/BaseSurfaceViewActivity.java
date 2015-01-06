package com.eflake.efframework.demo;

import android.app.Activity;
import android.os.Bundle;

import com.eflake.efframework.R;
import com.lavadroid.eflake.eflibrary.base.BaseToolbarActivity;
import com.lavadroid.eflake.eflibrary.utils.FileUtils;

import java.io.IOException;

/**
 * Activity which contains a simple base custom surface view
 *
 * @author Eflake
 * @version 1.0
 * @since 2014.03.04
 */
public class BaseSurfaceViewActivity extends BaseToolbarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_surface_view);
        FileUtils utils = new FileUtils();
        try {
            utils.createSDFile("eflaksaas.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
