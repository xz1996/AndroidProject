package com.activity.login;

import com.activity.forum.ForumActivity;
import com.activity.register.RegisterActivity;
import com.test1.login.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

public class LoginActivity extends Activity {

	private static String tag="LifeCycle";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);		//������xml�ļ������
		Log.d(tag, "onCreate");
		Handle();	//���ú���
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//�����Ի���������ʾ�û�
	public void showDialog(String title,String msg)
	{
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle(title).setMessage(msg).setPositiveButton("ȷ��", null).show();
	}
	//�¼�����
	protected void Handle()
	{
		Button button1=(Button)findViewById(R.id.buttonlogin);	//Login��ť
		Button button2=(Button)findViewById(R.id.buttonquit);	//�˳���ť
		Button button3=(Button)findViewById(R.id.buttonreg);	//ע�ᰴť
		
		//ΪLogin��ť��Ӽ����¼�
		button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
				
				//�õ���������û���
				String username=((EditText)findViewById(R.id.edit_password)).getText().toString();
				
				if(username==null||username.length()<=0)	//����û���Ϊ��
				{
					String title="Warning!";
					String msg="�û�������Ϊ�գ�";
					showDialog(title,msg);
				}
				else										//��ת����ӭ����
				{
					Intent welcome_intent=new Intent(LoginActivity.this,ForumActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString("UserName",username);
					welcome_intent.putExtras(bundle);
					
					startActivity(welcome_intent);
				}
			}
		});
		
		//�˳���ť
		button2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//ע�ᰴť
		button3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent register_intent=new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(register_intent);
			}
		});
	}
}
