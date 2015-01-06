package com.eflake.efframework.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.eflake.efframework.R;

public class BaseToolbarActivity extends ActionBarActivity {
    public static final int ANIM_TYPE_SLIDE_LEFT = 0;
    public static final int ANIM_TYPE_SLIDE_RIGHT = 1;
    public static final int ANIM_TYPE_FADE_IN = 2;
    public int current_fragment_index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Switch Activity
     *
     * @param target_activity
     */
    public void switchActivityByClass(Class<?> target_activity) {
        Intent intent = new Intent(this, target_activity);
        startActivity(intent);
    }

    /**
     * Switch to Back Activity or Top Activity
     *
     * @param target_activity
     */
    public void switchActivityByClass(Class<?> target_activity, boolean isBack) {
        Intent intent = new Intent(this, target_activity);
        if (isBack) {
            // this flag is using to avoid create new activity in the activity
            // stack when switch to Back Activity or Top Activity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(intent);
    }

    /**
     * Switch fragment by index
     *
     * @param content_id ID of target frameLayout
     * @param AnimTpye   Index of animation effect Type
     */
    public void switchFragmentByIndex(int content_id, Fragment fragment, int AnimTpye, int index) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        switch (AnimTpye) {
            case BaseToolbarActivity.ANIM_TYPE_SLIDE_LEFT:
                transaction.setCustomAnimations(R.anim.fade_in,
                        R.anim.fade_out);
                break;
            case BaseToolbarActivity.ANIM_TYPE_SLIDE_RIGHT:
                transaction.setCustomAnimations(R.anim.fade_in,
                        R.anim.fade_in);
                break;
            case BaseToolbarActivity.ANIM_TYPE_FADE_IN:
                transaction.setCustomAnimations(R.anim.fade_in,
                        R.anim.fade_in);
                break;
            default:
                break;
        }
        transaction.replace(content_id, fragment);
        transaction.commit();
        current_fragment_index = index;
    }

    /**
     * Switch fragment by index
     *
     * @param content_id ID of target frameLayout
     * @param AnimTpye   Index of animation effect Type
     */
    public void switchFragmentByIndex(int content_id, Fragment fragment, int AnimTpye, int index, boolean addbackstack) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        switch (AnimTpye) {
            case BaseToolbarActivity.ANIM_TYPE_SLIDE_LEFT:
                transaction.setCustomAnimations(R.anim.fade_in,
                        R.anim.fade_in);
                break;
            case BaseToolbarActivity.ANIM_TYPE_SLIDE_RIGHT:
                transaction.setCustomAnimations(R.anim.fade_in,
                        R.anim.fade_in);
                break;
            case BaseToolbarActivity.ANIM_TYPE_FADE_IN:
                transaction.setCustomAnimations(R.anim.fade_in,
                        R.anim.fade_in);
                break;
            default:
                break;
        }
        transaction.replace(content_id, fragment);
        if (addbackstack) {
            transaction.addToBackStack("add");
        }
        transaction.commit();
        current_fragment_index = index;
    }

    /**
     * Switch fragment by index ,which contains an bundle augment
     *
     * @param content_id ID of target frameLayout
     * @param index      Index of fragment
     * @param AnimTpye   Index of animation effect Type
     * @param augument   Bundle augment
     */
    public void switchFragmentByIndex(int content_id, Fragment fragment, int AnimTpye,
                                      int index, Bundle augument) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        switch (AnimTpye) {
            case BaseToolbarActivity.ANIM_TYPE_SLIDE_LEFT:
                transaction.setCustomAnimations(R.anim.fade_in,
                        R.anim.fade_in);
                break;
            case BaseToolbarActivity.ANIM_TYPE_SLIDE_RIGHT:
                transaction.setCustomAnimations(R.anim.fade_in,
                        R.anim.fade_in);
                break;
            case BaseToolbarActivity.ANIM_TYPE_FADE_IN:
                transaction.setCustomAnimations(R.anim.fade_in,
                        R.anim.fade_in);
                break;
            default:
                break;
        }
        transaction.replace(content_id,
                fragment);
        transaction.commit();
        current_fragment_index = index;
    }

    @Override
    public final void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar, boolean handleBackground) {
        setSupportActionBar(toolbar);
    }

}
