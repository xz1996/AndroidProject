package com.test1.login;

import com.activity.usersdb.UsersDBActivity;

import com.manager.UserManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateUdbActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_udb);
		
		final EditText pwd1 = (EditText)findViewById(R.id.UpdatePwd_edit1);
		final EditText pwd2 = (EditText)findViewById(R.id.UpdatePwd_edit2);
		Button bt = (Button)findViewById(R.id.UpdatePwdbt);
		

		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pwd = pwd1.getText().toString();
				
				if(pwd.equals(pwd2.getText().toString()))
				{
					Intent intent= new Intent();
					intent.putExtra("newPwd", pwd1.getText().toString());
					
					//设置返回数据
					UpdateUdbActivity.this.setResult(RESULT_OK, intent);
					
					//关闭Activity
					UpdateUdbActivity.this.finish();
				}
				else
					showDialog("Waring!","两次密码不一样");
			}
		});
		
	}
	
	//创建对话框，用于提示用户
	public void showDialog(String title,String msg)
	{
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle(title).setMessage(msg).setPositiveButton("确定", null).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_udb, menu);
		return true;
	}

	public void onPause()
	{
		super.onPause();
		UpdateUdbActivity.this.setResult(RESULT_OK,getIntent());
		
	}
	
	public void onStop()
	{
		super.onStop();
		UpdateUdbActivity.this.setResult(RESULT_OK,getIntent());
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		UpdateUdbActivity.this.setResult(RESULT_OK,getIntent());
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
}
