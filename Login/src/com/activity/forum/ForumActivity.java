package com.activity.forum;

import java.util.*;

import com.adapter.ForumAdapter;
import com.bean.Forum;
import com.test1.login.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

public class ForumActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forum);
		
		 ListView listview = (ListView)findViewById(R.id.listViewForum);
		 List<Forum> listForum = new ArrayList<Forum>();
		 listForum.add(new Forum(R.drawable.p7,"中国成全球最大M2M市场","author:jason"));
		 listForum.add(new Forum(R.drawable.p8,"物联网是什么","author:Frank"));
		
		 //创建自定义ForumAdapter
		 ForumAdapter forumadapter=new ForumAdapter(this,listForum);
		 
		 //绑定ForumAdapter到listview
		 listview.setAdapter(forumadapter);
		 
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forum, menu);
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
