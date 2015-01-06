package com.lavadroid.eflake.eflibrary.utils;


import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class IntentUtil {
    /**
     * android获取一个用于打开HTML文件的intent
     *
     * @return
     * @filename filename
     */
    public static Intent getHtmlFileIntent(String filename) {
        Uri uri = Uri.parse(filename).buildUpon()
                .encodedAuthority("com.android.htmlfileprovider")
                .scheme("content").encodedPath(filename).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    /**
     * android获取一个用于打开图片文件的intent
     *
     * @return
     * @filename filename
     */
    public static Intent getImageFileIntent(String filename) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(filename));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    /**
     * android获取一个用于打开PDF文件的intent
     *
     * @return
     * @filename filename
     */
    public static Intent getPdfFileIntent(String filename) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(filename));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    /**
     * android获取一个用于打开文本文件的intent
     *
     * @filename filename
     * @filename filenameBoolean
     * .     * @return
     */
    public static Intent getTextFileIntent(String filename, boolean filenameBoolean) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (filenameBoolean) {
            Uri uri1 = Uri.parse(filename);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(filename));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

    /**
     * android获取一个用于打开音频文件的intent
     *
     * @return
     * @filename filename
     */
    public static Intent getAudioFileIntent(String filename) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(filename));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    /**
     * android获取一个用于打开视频文件的intent
     *
     * @return
     * @filename filename
     */
    public static Intent getVideoFileIntent(String filename) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(filename));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    /**
     * android获取一个用于打开CHM文件的intent
     *
     * @return
     * @filename filename
     */
    public static Intent getChmFileIntent(String filename) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(filename));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    /**
     * android获取一个用于打开Word文件的intent
     *
     * @return
     * @filename filename
     */
    public static Intent getWordFileIntent(String filename) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(filename));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    /**
     * android获取一个用于打开Excel文件的intent
     *
     * @return
     * @filename filename
     */
    public static Intent getExcelFileIntent(String filename) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(filename));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    /**
     * android获取一个用于打开PPT文件的intent
     *
     * @return
     * @filename filename
     */
    public static Intent getPptFileIntent(String filename) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(filename));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;

    }

    public static Intent pickImage() {
        Intent intent_img = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return intent_img;
    }

    public static Intent pickFile() {
        Intent intent_file = new Intent(Intent.ACTION_GET_CONTENT);
        intent_file.setType("*/*");
        intent_file.addCategory(Intent.CATEGORY_OPENABLE);
        return intent_file;
    }

    /**
     * 使用举例
     */
    public static void Test() {
        //Intent mIntent = getHtmlFileIntent("/mnt/sdcard/tutorial.html");//SD卡主目录
        //Intent mIntent = getHtmlFileIntent("/sdcard/tutorial.html");//SD卡主目录,这样也可以
        //Intent mIntent= getHtmlFileIntent("/system/etc/tutorial.html");//系统内部的etc目录
        //Intent mIntent = getPdfFileIntent("/system/etc/helphelp.pdf");
        //Intent mIntent = getWordFileIntent("/system/etc/help.doc");
        //Intent mIntent = getExcelFileIntent("/mnt/sdcard/Book1.xls")
        //Intent mIntent = getPptFileIntent("/mnt/sdcard/download/Android_PPT.ppt");//SD卡的download目录下
        //Intent mIntent = getVideoFileIntent("/mnt/sdcard/ice.avi");
        //Intent mIntent = getAudioFileIntent("/mnt/sdcard/ren.mp3");
        //Intent mIntent = getImageFileIntent("/mnt/sdcard/images/001041580.jpg");
        //Intent mIntent = getTextFileIntent("/mnt/sdcard/hello.txt",false);
        //startActivity(mIntent);
    }

}
