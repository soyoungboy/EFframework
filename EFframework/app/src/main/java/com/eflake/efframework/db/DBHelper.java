package com.eflake.efframework.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String SQL_CREATE_LOG = "CREATE TABLE IF NOT EXISTS " + 
									DBConstant.TABLE_NAME_POSITION + " (" +
									DBConstant.TABLE_POSITION_COLUMN_MAIN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
									DBConstant.TABLE_POSITION_COLUMN_SEX + " TEXT, " +
									DBConstant.TABLE_POSITION_COLUMN_AGE + " INTEGER, " +
									DBConstant.TABLE_POSITION_COLUMN_NAME +  " TEXT);";

	public DBHelper(Context context) {
		super(context, DBConstant.DB_NAME, null, DBConstant.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_LOG);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
