package com.skholingua.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteDbHelper extends SQLiteOpenHelper {

	// Table Name
	public static final String TABLE_NAME = "CONTACTS";

	// Table columns
	public static final String _ID = "_id";
	public static final String CONTACTS_NAME = "name";
	public static final String CONTACTS_PHONE_NO = "phone";

	// Database Information
	static final String DB_NAME = "conatcts.db";

	// database version
	static final int DB_VERSION = 1;

	// Creating table query
	private static final String CREATE_TABLE = "create table " + TABLE_NAME
			+ "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CONTACTS_NAME
			+ " TEXT NOT NULL, " + CONTACTS_PHONE_NO + " TEXT);";

	public SqlLiteDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}