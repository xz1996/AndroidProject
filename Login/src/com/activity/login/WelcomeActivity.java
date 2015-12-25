package com.activity.login;

import java.util.*;

import com.test1.login.R;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.*;


public class WelcomeActivity extends Activity {

	static int i=0;;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		handle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
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
	
	public void handle()
	{
		
	    //获得前一个Activity的消息
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		TextView username=(TextView)findViewById(R.id.password);
		username.setText(bundle.getString("UserName"));
		
		
		//ImageView
		
		final int []imageId={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6};
		final ImageView image=(ImageView)findViewById(R.id.imageView1);
	    image.setImageResource(imageId[i]);
	    if(i<5)
	    	i++;
	    else
	    	i=0;
	
		//Back按钮
		
		Button back=(Button)findViewById(R.id.buttonlogin);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
