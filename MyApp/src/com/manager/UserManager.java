package com.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.adapter.DBAdapter;
import com.bean.User;

/**
 * Created by jxnkx on 2015/12/11.
 */
public class UserManager {
    private static final String TABLE_NAME = "Users";
    private SQLiteDatabase db;

    public UserManager(SQLiteDatabase db) {
    	this.db = db;
	}
	public boolean add(User user)
    {
        if(searchByName(user))
            return false;
        else
        {
        	db.beginTransaction();  //开始事务 
        	try{
	            String addUsers = "insert into " + TABLE_NAME + " values (null,'" + user.getUserName() +
	                   "','" + user.getPassword() + "','" + user.getSex() + "')";
	            db.execSQL(addUsers);
	            db.setTransactionSuccessful();	//设置事务成功标志
        	}
        	catch(SQLException e)
        	{
        		return false;
        	}
        	finally{
        		db.endTransaction();		//若事务成功则提交，否则回滚
        	}
            
            return true;
        }
    }
    public void delete(User user)
    {
        String delUsers = "delete from "+TABLE_NAME+" where Uname = '"+user.getUserName()+"'";
        db.execSQL(delUsers);
    }
    public void updatePassword(User user,String password)
    {
        String updateUsers = "update "+TABLE_NAME+" set Upassword = '"+password+"' where Uname = '"+user.getUserName()+"'";
        db.execSQL(updateUsers);
    }

    public boolean searchByName(User user)
    {
        String searchUsers = "select * from "+TABLE_NAME+" where Uname = '"+user.getUserName()+"'";
        Cursor result = null;
        result = db.rawQuery(searchUsers,null);
        if(result.moveToNext())
            return true;
        else
            return false;
    }
    
    public boolean search(User user)
    {
    	 String searchUsers = "select * from "+TABLE_NAME+" where Uname = '"+user.getUserName()+
    			 				"' and Upassword = '"+user.getPassword()+"'";
         Cursor result = null;
         result = db.rawQuery(searchUsers,null);
         if(result.moveToNext())
             return true;
         else
             return false;
    }
}

