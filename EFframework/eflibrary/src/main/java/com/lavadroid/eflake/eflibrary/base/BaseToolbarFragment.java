package com.lavadroid.eflake.eflibrary.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Fragment which using toolbar should extends from this class
 */
public abstract class BaseToolbarFragment extends Fragment {
    private Toolbar mToolbar;
    private MaterialMenuDrawable materialMenu;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    protected void setSupportActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar, true);
    }

    protected final void setSupportActionBar(Toolbar toolbar, boolean handleBackground) {
        ((BaseToolbarActivity) getActivity()).setSupportActionBar(toolbar, handleBackground);
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }
}
