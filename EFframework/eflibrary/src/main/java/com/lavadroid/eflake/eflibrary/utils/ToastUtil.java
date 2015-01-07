
package com.lavadroid.eflake.eflibrary.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lavadroid.eflake.eflibrary.R;

public class ToastUtil {
    private Toast mToastInstance;
    private Context mContext;

    public ToastUtil(Context context) {
        mContext = context;
    }

    public void showToast(String message) {
        makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String message, int showTime) {
        makeText(mContext, message, showTime).show();
    }

    public void showNoNetworkToast() {

        makeText(mContext, mContext.getString(R.string.network_unavailable),
                Toast.LENGTH_SHORT).show();
    }

    public void showNoNetworkToast(int showTime) {

        makeText(mContext, mContext.getString(R.string.network_unavailable),
                showTime).show();
    }

    public void showNoSDcardToast() {
        makeText(mContext, mContext.getString(R.string.sdcard_invalid),
                Toast.LENGTH_SHORT).show();
    }

    public void showNoSDcardToast(int showTime) {
        makeText(mContext, mContext.getString(R.string.sdcard_invalid),
                showTime).show();
    }

    private Toast makeText(Context context, String message, int showTime) {
        if (mToastInstance == null) {

            mToastInstance = new Toast(context);

            LayoutInflater inflate = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View v = inflate.inflate(R.layout.toast_content_view, null);
            mToastInstance.setView(v);

        }

        ((TextView) mToastInstance.getView().findViewById(R.id.message))
                .setText(message);

        mToastInstance.setDuration(showTime);

        return mToastInstance;
    }

}
