package com.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

	private final Context context;
	private SQLiteDatabase db;
	private DBopenHelper dbOpenHelper;
	private String dbName;

	public DBAdapter(Context _context,String _dbName) {
		// TODO Auto-generated constructor stub
		context = _context;
		dbName = _dbName;
	}
	
	public SQLiteDatabase getDb() {
		return db;
	}
	
	public void open()
	{
		dbOpenHelper = new DBopenHelper(context,dbName,null,1);
		try{
			db = dbOpenHelper.getWritableDatabase();
		}
		catch(SQLiteException e)
		{
			db = dbOpenHelper.getReadableDatabase();
		}
	}
	
	public void close()
	{
		if(db!=null)
		{
			db.close();
			db=null;
		}
	}
	
	private static class DBopenHelper extends SQLiteOpenHelper{

		public static final String CREATE_TABLE = "Create table Users"+
				"(Uid integer primary key autoincrement,Uname text not null,"+
				"Upassword  text not null,sex text not null)";

		public DBopenHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}


		//建表
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(CREATE_TABLE);
		}

		//更新表的结构
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			// 调用时间：如果DATABASE_VERSION值被改为别的数,系统发现现有数据库版本不同,即会调用onUpgrade
			// onUpgrade方法的三个参数，一个 SQLiteDatabase对象，一个旧的版本号和一个新的版本号
			// 这样就可以把一个数据库从旧的模型转变到新的模型
			// 这个方法中主要完成更改数据库版本的操作
			db.execSQL("Drop table if exists Users");
			onCreate(db);
		}
	}
	
}

