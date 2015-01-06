package com.eflake.efframework.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Context.getExternalFilesDir()-SDCard/Android/data/你的应用的包名/files/</p>
 * Context.getExternalCacheDir()-SDCard/Android/data/你的应用包名/cache/</p>
 * 如果使用上面的方法，当你的应用在被用户卸载后，SDCard/Android/data/你的应用的包名/
 * 这个目录下的所有文件都会被删除，不会留下垃圾信息。</p> 而且上面二个目录分别对应 设置->应用->应用详情里面的”清除数据“与”清除缓存“选项</p>
 * 在SDCard中创建与删除文件权限。
 * <uses-permission
 * android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/> ，
 * 
 * 往SDCard写入数据权限
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 * 
 * @author eflake
 * 
 */
public class SdcardUtils {
	private static final String TAG = "SdcardUtil";

	private SdcardUtils() { /* cannot be instantiated */
	}

	/**
	 * SD卡是否有效
	 */
	public static boolean sdCardIsAvailable() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	/**
	 * 是否有SD卡缓存目录
	 */
	public static boolean hasExternalCacheDir() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	/**
	 * 获得程序在SD卡上的CACHE目录
	 */
	public static String getExternalCacheDir(Context context) {
		// After Froyo
		if (hasExternalCacheDir()) {
			return context.getExternalCacheDir().getPath() + File.separator
					+ "gesture";
		}
		// Before Froyo we need to construct the external cache dir ourselves
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/gesture/";
		return Environment.getExternalStorageDirectory().getPath() + cacheDir;
	}

	/**
	 * 获取SD卡真实大小
	 */
	@SuppressWarnings("deprecation")
	public static long getRealSizeOnSdcard() {
		File path = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * 检测Sdcard是否有足够的空间
	 */
	public static boolean enoughSpaceOnSdCard(long updateSize) {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return false;
		return (updateSize < getRealSizeOnSdcard());
	}

	/**
	 * 获取手机的存储大小
	 */
	@SuppressWarnings("deprecation")
	public static long getRealSizeOnPhone() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		long realSize = blockSize * availableBlocks;
		return realSize;
	}

	/**
	 * 检测手机存储是否有足够的空间
	 */
	public static boolean enoughSpaceOnPhone(long updateSize) {
		return getRealSizeOnPhone() > updateSize;
	}

}
