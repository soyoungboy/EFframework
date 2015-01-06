package com.lavadroid.eflake.eflibrary.db;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager {

	private static DBManager mInstance;
	private SQLiteDatabase mDatabase;

	public static DBManager getInstance(Context context) {
		if (null == mInstance) {
			mInstance = new DBManager(context);
		}
		return mInstance;
	}

	private DBManager(Context context) {
		SQLiteOpenHelper helper = new DBHelper(context);
		mDatabase = helper.getWritableDatabase();
	}

	@Override
	protected void finalize() throws Throwable {
		mDatabase.close();
		super.finalize();
	}

	public void insertPoiPosition(String name) {
		ContentValues cv = new ContentValues();
		cv.put(DBConstant.TABLE_POSITION_COLUMN_NAME, name);
		mDatabase.insert(DBConstant.TABLE_NAME_POSITION, null, cv);
	}

	public void deletePosition(String id) {
		String[] args = {id};
		mDatabase.delete(DBConstant.TABLE_NAME_POSITION, "_id=?", args);
	}
	
	public void deleteAllPosition() {
		mDatabase.delete(DBConstant.TABLE_NAME_POSITION, null, null);
	}
	
	public String queryPoiPosition(String where){
		String result = null;
		final String query_head = "select * from " + DBConstant.TABLE_NAME_POSITION + " ";
		Cursor cursor = mDatabase.rawQuery(query_head + "where "+where,null);
		if (null != cursor) {
			while (cursor.moveToNext()) {
				//Action here
				result = cursor.getString(cursor.getColumnIndex(DBConstant.TABLE_POSITION_COLUMN_NAME));
			}
		}
		return result;
	}

	public ArrayList<String> queryPoiPositionAll(){
		ArrayList<String> result_list = new ArrayList<String>();
		final String query_head = "select * from " + DBConstant.TABLE_NAME_POSITION + " ";
		Cursor cursor = mDatabase.rawQuery(query_head,null);
		if (null != cursor) {
			while (cursor.moveToNext()) {
				//Action here
			}
		}
		return result_list;
	}
}
