package com.eflake.efframework.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class DimensionUtils {
	private static final String TAG = "DimensionUtils";
	private static float testWidth = 1280;

	private DimensionUtils() { /* cannot be instantiated */
	}

	/**
	 * 获取屏幕尺寸及密度信息
	 */
	public String getScreenDemensionInfo(Activity activity) {
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		int height = metric.heightPixels; // 屏幕高度（像素）
		float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5/ 2.0/ 3.0）
		int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240 / 320 /
											// 480）
		StringBuilder builder = new StringBuilder();
		builder.append("获取屏幕信息：");
		builder.append("，屏幕宽度=").append(width);
		builder.append("，屏幕高度=").append(height);
		builder.append("，屏幕密度=").append(density);
		builder.append("，屏幕密度DPI=").append(densityDpi);
		return builder.toString();
	}
	
	/**
	 * 获取View对象高度
	 */
	public static int getHeight(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return view.getMeasuredHeight();
	}
	
	/**
	 * dp转px
	 */
	public static int dip2px(Context context, int dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px转dp
	 */
	public static int px2dip(Context context, int pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		Log.d("eflake", "density = " + scale + "");
		return (int) (pxValue / scale + 0.5f) - 15;
	}

	/**
	 * sp转px
	 */
	public static int sp2px(Context context, float spValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * px转sp
	 */
	public static int px2sp(Context context, float pxValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * Canvas中，依据一个标准屏幕宽度下文字大小，计算其余屏幕文字大小，获得一样的显示效果
	 * 
	 * @param activity
	 * @param reference_text_size
	 *            目标屏幕宽度（像素）下文字大小
	 * @return
	 */
	public static int calculateTextSize(Activity activity,
			float reference_text_size) {
		int font_size = 0;
		font_size = (int) (activity.getWindowManager().getDefaultDisplay()
				.getWidth()
				/ DimensionUtils.testWidth * reference_text_size);
		Log.d("eflake", "**************font_size=" + font_size);
		return font_size;
	}

}
