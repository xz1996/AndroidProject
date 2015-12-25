package com.activity.register;

import java.util.*;

import com.activity.login.LoginActivity;
import com.adapter.DBAdapter;
import com.bean.User;
import com.manager.UserManager;
import com.test1.login.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class RegisterActivity extends Activity {

	private static final String DB_NAME = "MyApp.db";
	private UserManager um;
	private User user;
	
	DBAdapter dbAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		dbAdapter = new DBAdapter(RegisterActivity.this,DB_NAME);
		dbAdapter.open();
		
		Spinner spEdu=(Spinner)findViewById(R.id.spinnerEducation);
		Spinner spSchool=(Spinner)findViewById(R.id.spinnerschool);
		Spinner spMajor=(Spinner)findViewById(R.id.spinnerSpecialty);

		Button	regButtonYes = (Button)findViewById(R.id.buttonregYes);
		final EditText regUname = (EditText)findViewById(R.id.regusername);
		final EditText regPassword = (EditText)findViewById(R.id.regpassword);

		final RadioGroup radioSex = (RadioGroup)findViewById(R.id.radioGroupSex);

		//得到arrays.xml里的items
		String[] schoolItems = getResources().getStringArray(R.array.graduateSchool);
		
		String[] majorItems = getResources().getStringArray(R.array.speciality);
		
		
		//建一个字符串数组列表
		List<String> listEdu=new ArrayList<String>();
		listEdu.add("小学");
		listEdu.add("初中");
		listEdu.add("高中");
		listEdu.add("本科");
		listEdu.add("硕士");
		listEdu.add("博士");
		
		//建数组适配器
		ArrayAdapter<String> eduAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,listEdu);
		ArrayAdapter<String> schoolAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, schoolItems);
		ArrayAdapter<String> majorAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, majorItems);
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//将Spinner控件和适配器绑定
		spEdu.setAdapter(eduAdapter);
		spSchool.setAdapter(schoolAdapter);
		spMajor.setAdapter(majorAdapter);

		//为Spinner设置监听器
		spEdu.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(parent.getContext(),"已选中"+parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}});
		
		spSchool.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(parent.getContext(),"已选中"+parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}});
		
		spMajor.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(parent.getContext(), "已选中" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		//注册确认按钮的点击事件
		regButtonYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String uName = regUname.getText().toString();
				String uPassword = regPassword.getText().toString();
				String sex = "M";
				switch (radioSex.getCheckedRadioButtonId())
				{
					case R.id.radioMale:sex = "M";break;
					case R.id.radioFemale: sex = "W";break;
				}

				user = new User(uName,uPassword,sex);
				um = new UserManager(dbAdapter.getDb());
				if(um.add(user))
				{
					Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
					finish();
				}
				else
				{
					Toast.makeText(RegisterActivity.this,"注册失败，用户名已经存在！！",Toast.LENGTH_SHORT).show();
				}
			}
		});

		
	}
	
	public void onDestroy()
	{
		super.onPause();
		dbAdapter.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
}
