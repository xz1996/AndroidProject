package com.activity.register;

import java.util.*;

import com.test1.login.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		Spinner spEdu=(Spinner)findViewById(R.id.spinnerEducation);
		Spinner spSchool=(Spinner)findViewById(R.id.spinnerschool);
		Spinner spMajor=(Spinner)findViewById(R.id.spinnerSpecialty);
		
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
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,listEdu);
		ArrayAdapter<String> schooladapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, schoolItems);
		ArrayAdapter<String> majoradapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, majorItems);
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//将Spinner控件和适配器绑定
		spEdu.setAdapter(adapter);
		
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
		
		spMajor.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(parent.getContext(),"已选中"+parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}});
		
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
