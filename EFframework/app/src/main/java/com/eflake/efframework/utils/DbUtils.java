package com.eflake.efframework.utils;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DbUtils {
	private static final String TAG = "DbUtils";
	private SQLiteDatabase database;

	private DbUtils() { /* cannot be instantiated */
	}

	/**
	 * 打开SD卡数据库
	 */
	public boolean openDatabase(String dbfile) {
		boolean opened = true;
		try {
			Log.i("eflake", dbfile);
			database = SQLiteDatabase.openDatabase(dbfile, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			e.printStackTrace();
			opened = false;
		}
		return opened;
	}

	public void closeDatabase() {
		if (this.database != null)
			this.database.close();
	}
}
