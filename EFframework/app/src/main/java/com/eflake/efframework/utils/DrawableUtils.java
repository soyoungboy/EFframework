package com.eflake.efframework.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class DrawableUtils {
	private static final String TAG = "DrawableUtils";

	private DrawableUtils() { /* cannot be instantiated */
	}

	// ---------Drawable工具--------------
		/**
		 * 读取资源文件中的Drawable
		 */
		public static Drawable ReadDrawable(Context context, int resId) {
			return context.getResources().getDrawable(resId);
		}

		/**
		 * 二进制数组转换Drawble对象
		 */
		public static Drawable byteArrayToDrawable(byte[] bytes, Context context) {
			return null == bytes ? null : BitmapUtil.bitmapToDrawable(
					BitmapFactory.decodeByteArray(bytes, 0, bytes.length), context);
		}

		/**
		 * 缩放Drawable
		 */
		public static Drawable zoomDrawable(Drawable drawable, int w, int h,
				Resources res) {
			int width = drawable.getIntrinsicWidth();
			int height = drawable.getIntrinsicHeight();
			// drawable转换成bitmap
			Bitmap oldbmp = BitmapUtil.drawableToBitmap(drawable);
			// 创建操作图片用的Matrix对象
			Matrix matrix = new Matrix();
			// 计算缩放比例
			float sx = ((float) w / width);
			float sy = ((float) h / height);
			// 设置缩放比例
			matrix.postScale(sx, sy);
			// 建立新的bitmap，其内容是对原bitmap的缩放后的图
			Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
					matrix, true);
			BitmapDrawable newDrawable = new BitmapDrawable(res, newbmp);
			return newDrawable;
		}

		/**
		 * 旋转Drawable
		 */
		public static Drawable rotateDrawable(Drawable drawable, Context context) {
			int width = drawable.getIntrinsicWidth();
			int height = drawable.getIntrinsicHeight();
			// drawable转换成bitmap
			Bitmap oldbmp = BitmapUtil.drawableToBitmap(drawable);
			// 创建操作图片用的Matrix对象
			Matrix matrix = new Matrix();
			// 设置旋转角度
			matrix.postRotate(180);
			// 建立新的bitmap，其内容是对原bitmap的缩放后的图
			Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
					matrix, true);
			BitmapDrawable newDrawable = new BitmapDrawable(context.getResources(),
					newbmp);
			return newDrawable;
		}
}
