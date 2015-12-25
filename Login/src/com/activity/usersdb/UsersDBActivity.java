package com.activity.usersdb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.activity.forum.DetailActivity;
import com.activity.forum.ForumActivity;
import com.activity.register.RegisterActivity;
import com.adapter.DBAdapter;
import com.adapter.UsersAdapter;
import com.bean.Forum;
import com.bean.User;
import com.manager.UserManager;
import com.test1.login.R;
import com.test1.login.R.id;
import com.test1.login.R.layout;
import com.test1.login.R.menu;
import com.test1.login.UpdateUdbActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UsersDBActivity extends Activity {
	
	private static final String DB_NAME = "MyApp.db";
	private UserManager um = null;
	public  ListView listview;
	public  List<User> listUser;
	public  int 	lsItemID;				//用于保存被选中的Item的序号
	private UsersAdapter useradapter = null;
	private DBAdapter dbAdapter = null;
	private SQLiteDatabase db = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users_db);
		
		dbAdapter = new DBAdapter(UsersDBActivity.this,DB_NAME);
		dbAdapter.open();
		
		db = dbAdapter.getDb();
		um = new  UserManager(db);
		
		listview = (ListView)findViewById(R.id.udb_listView);
		listUser = new ArrayList<User>();
		
		showUsersDB();
		registerForContextMenu(listview);		//为listview注册上下文菜单

		//listview里item的监听事件
	   listview.setOnItemClickListener(new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub

			Toast.makeText(parent.getContext(),"选中第"+position+"项", Toast.LENGTH_SHORT).show();
		
		}});
	
	}
	
	public void onDestroy()
	{
		super.onPause();
		dbAdapter.close();
	}
	
	public void showUsersDB()
	{
		String showUsers = "select * from Users";
		String uname = null;
		String upassword = null;
		String sex = "M";
		Cursor result = null;
        result = db.rawQuery(showUsers,null);
        
        //将SQLite里的Users表的数据导出，通过listview显示出来
        while(result.moveToNext())
        {
        	uname = result.getString(1);
        	upassword = result.getString(2);
        	sex = result.getString(3);
        	User user = new User(uname,upassword,sex);
        	user.setUid(result.getLong(0));
        	listUser.add(user);
        }
        
        useradapter = new UsersAdapter(UsersDBActivity.this,listUser);
        listview.setAdapter(useradapter);
        useradapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.users_db, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	final  static  int CONTEXT_MENU_DEL = Menu.FIRST;
	final  static  int CONTEXT_MENU_UPDATE = Menu.FIRST+1;
	
	@Override
	public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo)
	{
		menu.setHeaderTitle("操作");
		menu.add(0, CONTEXT_MENU_DEL, 0, "删除");
		menu.add(0,CONTEXT_MENU_UPDATE,0,"修改");

		//用于得到此时上下文菜单的信息
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		lsItemID = info.position;		//得到所选项的序号
	}
	
	 /**
     * 为了得到传回的数据，必须在前面的Activity中（指MainActivity类）重写onActivityResult方法
     * 
     * requestCode 请求码，即调用startActivityForResult()传递过去的值
     * resultCode 结果码，结果码用于标识返回数据来自哪个新Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String newPwd = data.getExtras().getString("newPwd");//得到新Activity 关闭后返回的数据
       if(newPwd == null ||newPwd =="")
       {
    	   
       }
       else
       {
	        um.updatePassword(listUser.get(lsItemID), newPwd);
	        listUser.get(lsItemID).setPassword(newPwd);
	        useradapter.notifyDataSetChanged();//动态刷新listview数据
       }
    }

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case CONTEXT_MENU_DEL:
				um.delete(listUser.get(lsItemID));
				listUser.remove(lsItemID);
				useradapter.notifyDataSetChanged();//动态刷新listview数据
				return true;
				
			case CONTEXT_MENU_UPDATE:
				
				Intent UpdateUdb=new Intent(UsersDBActivity.this,UpdateUdbActivity.class);
				startActivityForResult(UpdateUdb,1);
				return true;
		}
		return false;
	}
}
