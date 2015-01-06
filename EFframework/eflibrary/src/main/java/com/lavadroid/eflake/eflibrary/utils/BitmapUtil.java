package com.lavadroid.eflake.eflibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PathEffect;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtil {
	private static final String TAG = "BitmapUtil";

	private BitmapUtil() { /* cannot be instantiated */
	}

	// ----------Bitmap工具--------------
	/**
	 * 读取资源文件中的Bitmap
	 */
	public static Bitmap ReadBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 获取可用内存的最大值(App使用内存超出这个值会引起OutOfMemory异常)
	 */
	public static int getMaxMemoryForApp() {
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		return maxMemory;
	}

	/**
	 * 对图片进行压缩，主要是为了解决控件显示过大图片占用内存造成OOM问题。 一般压缩后的图片大小应该和用来展示它的控件大小相近。
	 */
	public static Bitmap compressBitmapFromResourse(Context context, int resId,
			int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		/*
		 * 第一次解析时，inJustDecodeBounds设置为true，
		 * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
		 */
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), resId, options);
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		options.inSampleSize = inSampleSize;
		// 使用计算得到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(context.getResources(), resId,
				options);
	}

	/**
	 * 按照大小裁剪图片的中间部分(常用于生成缩略图)
	 */
	public static Bitmap clipThumb(Bitmap bitmap, int thumbSize) {
		Bitmap thumbBitmap = null;
		if (thumbSize > 0 && bitmap != null) {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Rect src = new Rect();
			if (width < height) {
				src.set(0, (height - width) / 2, width, (height - width) / 2
						+ width);
			} else {
				src.set((width - height) / 2, 0, (width - height) / 2 + height,
						height);
			}
			if (thumbSize > src.width()) {
				thumbSize = src.width();
			}
			thumbBitmap = Bitmap.createBitmap(thumbSize, thumbSize,
					Config.RGB_565);
			Canvas canvas = new Canvas(thumbBitmap);
			canvas.setDrawFilter(new PaintFlagsDrawFilter(0,
					Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
			canvas.drawBitmap(bitmap, src,
					new Rect(0, 0, thumbSize, thumbSize), null);
		}
		return thumbBitmap;
	}

	/**
	 * 在Bitmap上画虚线框
	 */
	public static void drawTextBound(RectF rect, Bitmap bitmap) {
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(5);
		PathEffect effects = new DashPathEffect(new float[] { 5, 5, 5, 5 }, 1);
		paint.setPathEffect(effects);
		canvas.drawRect(rect, paint);
	}

	/**
	 * 将Bitmap保存到SD卡
	 */
	public static void saveImgToLocal(String imagePath, Bitmap bm) {
		if (bm == null || imagePath == null || "".equals(imagePath)) {
			return;
		}
		File f = new File(imagePath);
		if (f.exists()) {
			return;
		} else {
			try {
				File parentFile = f.getParentFile();
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				f.createNewFile();
				FileOutputStream fos;
				fos = new FileOutputStream(f);
				bm.compress(CompressFormat.PNG, 100, fos);
				fos.close();
			} catch (FileNotFoundException e) {
				f.delete();
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				f.delete();
			}
		}
	}

	/**
	 * 将Bitmap旋转
	 */
	public static Bitmap rotateBitmap(Bitmap src, int degrees) {
		Matrix matrix = new Matrix();
		matrix.reset();
		matrix.setRotate(degrees);
		Bitmap dstBitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(),
				src.getHeight(), matrix, true);
		src.recycle();
		return dstBitmap;
	}

	/**
	 * 从SD卡中获取Bitmap
	 */
	public static Bitmap getImageFromLocal(String imagePath) {
		File file = new File(imagePath);
		if (file.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			file.setLastModified(System.currentTimeMillis());
			return bitmap;
		}
		return null;
	}
	
	/**
	 * 根据限制参数输出图片，图片不会拉伸
	 * 
	 * @param bitmap
	 *            原始bitmap
	 * @param maxWidth
	 *            输出容器的最大宽度，-1为不限制
	 * @param maxHeight
	 *            输出容器的最大高度，-1为不限制
	 * @param scale_w_h
	 *            输出图片的宽高比例，-1为按图比例
	 * @param rotate
	 *            原始bitmap需要旋转的角度 0,90,180,270...
	 * @param config
	 *            像素类型
	 */
	public static Bitmap CreateBitmap(Bitmap bitmap, int maxWidth,
			int maxHeight, float scale_w_h, int rotate, Config config) {
		Bitmap outBmp = null;
		if (bitmap != null) {
			int inW = bitmap.getWidth();
			int inH = bitmap.getHeight();

			if (rotate % 180 != 0) {
				inW += inH;
				inH = inW - inH;
				inW -= inH;
			}

			MyRect clipRect;
			if (scale_w_h > 0) {
				clipRect = MakeRect(inW, inH, scale_w_h);
			} else {
				clipRect = new MyRect();
				clipRect.left = 0;
				clipRect.top = 0;
				clipRect.width = inW;
				clipRect.height = inH;
			}

			float scale = 1;
			if (maxWidth > 0
					&& maxHeight > 0
					&& (maxWidth < clipRect.width || maxHeight < clipRect.height)) {
				float scale1 = (float) maxWidth / clipRect.width;
				float scale2 = (float) maxHeight / clipRect.height;
				if (scale1 <= scale2) {
					scale = scale1;
				} else {
					scale = scale2;
				}
			} else if (maxWidth > 0) {
				if (maxWidth < clipRect.width) {
					scale = (float) maxWidth / clipRect.width;
				}
			} else if (maxHeight > 0) {
				if (maxHeight < clipRect.height) {
					scale = (float) maxHeight / clipRect.height;
				}
			}

			float bmpW = clipRect.width * scale;
			if (bmpW < 1) {
				bmpW = 1;
			}
			float bmpH = clipRect.height * scale;
			if (bmpH < 1) {
				bmpH = 1;
			}
			float centerX = bmpW / 2f;
			float centerY = bmpH / 2f;
			Matrix matrix = new Matrix();
			matrix.postTranslate((bmpW - bitmap.getWidth()) / 2f,
					(bmpH - bitmap.getHeight()) / 2f);
			matrix.postRotate(rotate, centerX, centerY);
			matrix.postScale(scale, scale, centerX, centerY);

			outBmp = Bitmap.createBitmap((int) bmpW, (int) bmpH, config);
			Canvas canvas = new Canvas(outBmp);
			canvas.setDrawFilter(new PaintFlagsDrawFilter(0,
					Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
			Paint pt = new Paint();
			pt.setAntiAlias(true);
			pt.setFilterBitmap(true);
			canvas.drawBitmap(bitmap, matrix, pt);
		}

		return outBmp;
	}
	
	public static MyRect MakeRect(float width, float height, float scale_w_h) {
		MyRect outRect = new MyRect();
		outRect.width = width;
		outRect.height = width / scale_w_h;
		if (outRect.height > height) {
			outRect.height = height;
			outRect.width = height * scale_w_h;
		}
		outRect.left = (width - outRect.width) / 2f;
		outRect.top = (height - outRect.height) / 2f;

		return outRect;
	}

	static class MyRect {
		float width;
		float height;
		float left;
		float top;
	}

	/**
	 * 将Bitmap转换成二进制数组
	 */
	public static Bitmap Bytes2Bimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * 将二进制数组转换成Bitmap
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 将Bitmap转化为Drawable
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap, Context context) {
		BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
		return (Drawable) bd;
	}

	/**
	 * 将Drawable转化为Bitmap
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		// 取 drawable 的长宽
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		// 取 drawable 的颜色格式
		Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
				: Config.RGB_565;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画布
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// 把 drawable 内容画到画布中
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 将Bitmap转换成字符串
	 */
	public static String bitmapToString(Bitmap bitmap) {
		String string = null;
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, bStream);
		byte[] bytes = bStream.toByteArray();
		string = Base64.encodeToString(bytes, Base64.DEFAULT);
		return string;
	}

	/**
	 * 将字符串转换成Bitmap类
	 */
	public static Bitmap stringToBitmap(String string) {
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 对图片进行缩放
	 */
	public static Bitmap getScaleImage(String url, int requireSize) {
		BitmapFactory.Options o = new BitmapFactory.Options();
		// 此属性表示图片不加载到内存，只是读取图片的属性，包括图片的高宽
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(url, o);
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < requireSize || height_tmp / 2 < requireSize)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		Bitmap bmp = BitmapFactory.decodeFile(url, o2);
		return bmp;
	}

	/**
	 * 返回老式图片效果
	 */
	public static Bitmap toGrayscale(Bitmap bitmap) {
		int width, height;
		height = bitmap.getHeight();
		width = bitmap.getWidth();
		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bitmap, 0, 0, paint);
		return bmpGrayscale;
	}

	/**
	 * 获取圆角图片效果
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 获取倒影图片效果
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
				h / 2, matrix, false);
		Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
				Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}
}
