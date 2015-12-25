package com.activity.login;

import com.activity.forum.ForumActivity;
import com.activity.register.RegisterActivity;
import com.adapter.DBAdapter;
import com.bean.User;
import com.manager.UserManager;
import com.test1.login.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

public class LoginActivity extends Activity {

	private static String tag="LifeCycle";
	DBAdapter dbAdapter = null;

	/*定义访问模式*/
	public static int MODE = MODE_PRIVATE;
	/*定义一个SharedPreferences名。之后将以这个名字保存在Android文件系统中*/
	public static final String PREFERENCE_NAME = "SaveSetting";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);		//用于与xml文件相关联

		//数据库连接
		dbAdapter = new DBAdapter(LoginActivity.this,"MyApp.db");
		dbAdapter.open();

		Log.d(tag, "onCreate");
		Handle();	//调用函数
	}

	public void onStart()
	{
		super.onStart();
		Log.d(tag, "onStart");
	}
	
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		Log.d(tag, "onRestoreInstanceState");
	}
	
	public void onResume()
	{
		super.onResume();
		Log.d(tag, "onResume");
	}
	
	public void onRestart()
	{
		super.onRestart();
		Log.d(tag, "onRestart");
	}
	
	public void onPause()
	{
		super.onPause();
		Log.d(tag, "onPause");
		
	}
	
	public void onStop()
	{
		super.onStop();
		Log.d(tag, "onStop");
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		Log.d(tag, "onDestroy");
		dbAdapter.close();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Toast.makeText(this,"Setting",Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//创建对话框，用于提示用户
	public void showDialog(String title,String msg)
	{
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle(title).setMessage(msg).setPositiveButton("确定", null).show();
	}


	//用来保存用户的用户名和密码
	public boolean SaveUserInformation(SharedPreferences sharedPreferences)
	{
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("UserName",((EditText)findViewById(R.id.UpdatePwd_edit1)).getText().toString());
		editor.putString("Password",((EditText)findViewById(R.id.UpdatePwd_edit2)).getText().toString());

		//调用commit（）保存修改
		if(editor.commit())
			return true;
		else
			return false;
	}

	//从SaveInformation文件中读取用户之前的信息
	public void ReadInformation(SharedPreferences sharedPreferences)
	{
		EditText Uname = (EditText)findViewById(R.id.UpdatePwd_edit1);
		EditText Pwd = (EditText)findViewById(R.id.UpdatePwd_edit2);

		String name = sharedPreferences.getString("UserName","");
		String password = sharedPreferences.getString("Password","");

		Uname.setText(name);
		Pwd.setText(password);
	}

	//事件处理
	protected void Handle()
	{
		Button buttonLogin=(Button)findViewById(R.id.buttonlogin);	//Login按钮
		Button buttonQuit=(Button)findViewById(R.id.buttonquit);	//退出按钮
		Button buttonReg=(Button)findViewById(R.id.buttonreg);	//注册按钮

		CheckBox saveChb=(CheckBox)findViewById(R.id.RemMeChb);	//Remember me checkBox



		//获取SharedPreferences实例
		final SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
		//将用户信息读取出来
		ReadInformation(sharedPreferences);

		//判断是否选中Remember me 选项框
		saveChb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (SaveUserInformation(sharedPreferences))
						Toast.makeText(buttonView.getContext(), "Save Successfully!", Toast.LENGTH_SHORT).show();
				} else {

				}
			}

		});

		//为Login按钮添加监听事件
		buttonLogin.setOnClickListener(new View.OnClickListener() {

			@Override

			public void onClick(View view) {
				// TODO Auto-generated method stub

				//得到所输入的用户名
				String username = ((EditText) findViewById(R.id.UpdatePwd_edit1)).getText().toString();
				String password = ((EditText) findViewById(R.id.UpdatePwd_edit2)).getText().toString();

				
				
				if (username == null || username.length() <= 0)    //如果用户名为空
				{
					String title = "Warning!";
					String msg = "用户名不能为空！";
					showDialog(title, msg);
				} else if (password == null || password.length() <= 0) {
					String title = "Warning!";
					String msg = "密码不能为空！";
					showDialog(title, msg);
				} 
				else                                        //跳转界面
				{
					User user = new User(username,password,"M");
					UserManager um = new UserManager(dbAdapter.getDb());
					if(um.search(user))
					{
						Intent welcome_intent = new Intent(LoginActivity.this, ForumActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("UserName", username);
						welcome_intent.putExtras(bundle);
						startActivity(welcome_intent);
					}
					else
					{
						String title = "Warning!";
						String msg = "用户名或密码错误！";
						showDialog(title, msg);
					}
				}
			}
		});

		//注册按钮
		buttonReg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent register_intent=new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(register_intent);
			}
		});

		//退出按钮
		buttonQuit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbAdapter.close();
				finish();
			}
		});
		

	}
}
