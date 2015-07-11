package com.skholingua.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SqlLiteManger {

	private SqlLiteDbHelper _dbHelper;

	private Context context;

	private SQLiteDatabase database;

	public SqlLiteManger(Context c) {
		context = c;
		_dbHelper = new SqlLiteDbHelper(context);
	}

	public SqlLiteManger open() throws SQLException {

		database = _dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		_dbHelper.close();
	}

	public void insert(String name, String desc) {
		database.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			contentValue.put(SqlLiteDbHelper.CONTACTS_NAME, name);
			contentValue.put(SqlLiteDbHelper.CONTACTS_PHONE_NO, desc);
			database.insert(SqlLiteDbHelper.TABLE_NAME, null, contentValue);
			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
		}
	}

	public Cursor fetch() {
		String[] columns = new String[] { SqlLiteDbHelper._ID,
				SqlLiteDbHelper.CONTACTS_NAME,
				SqlLiteDbHelper.CONTACTS_PHONE_NO };
		Cursor cursor = database.query(SqlLiteDbHelper.TABLE_NAME, columns,
				null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public int update(long _id, String name, String desc) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(SqlLiteDbHelper.CONTACTS_NAME, name);
		contentValues.put(SqlLiteDbHelper.CONTACTS_PHONE_NO, desc);
		int i = database.update(SqlLiteDbHelper.TABLE_NAME, contentValues,
				SqlLiteDbHelper._ID + " = " + _id, null);
		return i;
	}

	public void delete(long _id) {
		database.delete(SqlLiteDbHelper.TABLE_NAME, SqlLiteDbHelper._ID + "="
				+ _id, null);
	}

}
