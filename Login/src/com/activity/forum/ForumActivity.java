package com.activity.forum;

import java.io.*;
import java.util.*;

import com.ResideMenu.ResideMenu;
import com.ResideMenu.ResideMenuItem;
import com.activity.login.LoginActivity;
import com.activity.usersdb.UsersDBActivity;
import com.adapter.ForumAdapter;
import com.bean.Forum;
import com.test1.login.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class ForumActivity extends Activity {

	public  ListView listview;
	public  List<Forum> listForum;
	public  List<ResideMenuItem> listRMI;
	public  int 	lsItemID;				//用于保存被选中的Item的序号
	ResideMenu resideMenu = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forum);
		
		 listview = (ListView)findViewById(R.id.listViewForum);
		 listForum = new ArrayList<Forum>();
		 listForum.add(new Forum(R.drawable.p7,"中国成全球最大M2M市场","author:jason"));
		 listForum.add(new Forum(R.drawable.p8,"物联网是什么","author:Frank"));

		registerForContextMenu(listview);		//为listview注册上下文菜单
		
		
		
		//resideMenu 的创建
		resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        listRMI = new ArrayList<ResideMenuItem>();
        // create menu items;
        String titles[] = { "UsersDB", "Profile", "Calendar", "Settings" };
        int icon[] = { R.drawable.icon_home, R.drawable.icon_profile, R.drawable.icon_calendar, R.drawable.icon_settings };

        for ( int i = 0; i < titles.length; i++){
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            listRMI.add(item);
            
            resideMenu.addMenuItem(item,ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        }
        
        listRMI.get(0).setOnClickListener(new View.OnClickListener(){

			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent userDB_intent = new Intent(ForumActivity.this, UsersDBActivity.class);
				startActivity(userDB_intent);
			}});
        /*ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
            @Override
            public void openMenu() {
                Toast.makeText(ForumActivity.this, "Menu is opened!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void closeMenu() {
                Toast.makeText(ForumActivity.this, "Menu is closed!", Toast.LENGTH_SHORT).show();
            }
        };
        
        resideMenu.setMenuListener(menuListener);*/
      
        
        
		 //创建自定义ForumAdapter
		 ForumAdapter forumadapter=new ForumAdapter(this,listForum);
		 //绑定ForumAdapter到listView
		 listview.setAdapter(forumadapter);

	 	//listview里item的监听事件
	   listview.setOnItemClickListener(new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub

			Toast.makeText(parent.getContext(),"选中第"+position+"项", Toast.LENGTH_SHORT).show();
			Intent Detail=new Intent(ForumActivity.this,DetailActivity.class);
			Bundle bundle=new Bundle();
			bundle.putSerializable("item", listForum.get(position));//序列化数据
			Detail.putExtras(bundle);
			startActivity(Detail);

		}});
	}
	
	 //使用手势滑动开启/关闭菜单
    @Override
      public boolean dispatchTouchEvent(MotionEvent ev) {
          return resideMenu.dispatchTouchEvent(ev);
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
			Toast.makeText(this,"关注设置",Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	final  static  int CONTEXT_MENU_1=Menu.FIRST;
	final  static  int CONTEXT_MENU_2=Menu.FIRST+1;
	final  static  int CONTEXT_MENU_3=Menu.FIRST+2;
	final  static  int CONTEXT_MENU_4=Menu.FIRST+3;
	final  static  int CONTEXT_MENU_5=Menu.FIRST+4;
	
	@Override
	public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo)
	{
		menu.setHeaderTitle("快捷菜单");
		menu.add(0, CONTEXT_MENU_1, 0, "赞");
		menu.add(0,CONTEXT_MENU_2,0,"关注");
		menu.add(0,CONTEXT_MENU_3,0,"举报");
		menu.add(0,CONTEXT_MENU_4,0,"保存页面（内部存储）");
		menu.add(0,CONTEXT_MENU_5,0,"保存页面（SD Card）");

		//用于得到此时上下文菜单的信息
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		lsItemID = info.position;		//得到所选项的序号
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case CONTEXT_MENU_1:
				Toast.makeText(this,"选中第"+CONTEXT_MENU_1+"项",Toast.LENGTH_SHORT).show();
				return true;
			case CONTEXT_MENU_2:
				Toast.makeText(this,"选中第"+CONTEXT_MENU_2+"项",Toast.LENGTH_SHORT).show();
				return true;
			case CONTEXT_MENU_3:
				Toast.makeText(this,"选中第"+CONTEXT_MENU_3+"项",Toast.LENGTH_SHORT).show();
				return true;
			case CONTEXT_MENU_4:
				FileSaved("com.forum.test1.login");
				Toast.makeText(this,FileRead("com.forum.test1.login"),Toast.LENGTH_SHORT).show();
				return true;
				
			case CONTEXT_MENU_5:
				SDCardSaved();
				return true;
		}
		return false;
	}

	//文件存储
	void FileSaved(String FILE_NAME)
	{
		FileOutputStream fos = null;
		try {
			fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
			fos.write(listForum.get(lsItemID).getTitle().getBytes());
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if (fos != null){
				try {
					fos.flush();
					fos.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//读取文件
	String FileRead(String FileName) {
		FileInputStream fis = null;
		try {
			fis = openFileInput(FileName);
			if (fis.available() == 0) {
				return null;
			}
			byte[] readBytes = new byte[fis.available()];
			while (fis.read(readBytes) != -1) ;
			String text = new String(readBytes);
			return text;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	return null;
	}

	//SD卡存储
	void SDCardSaved()
	{
		File newFile = null;
		String fileName = "SdFile-"+System.currentTimeMillis()+".txt";
		File dir = new File("/mnt/sdcard/");
		if(dir.exists()&&dir.canWrite())
		{
			 newFile = new File(dir.getAbsolutePath()+"/"+fileName);
		}
		else if(!dir.exists()){
			Toast.makeText(this, "目录不存在", Toast.LENGTH_SHORT).show();
		}
		else if(!dir.canWrite())
		{
			Toast.makeText(this, "目录不能写入", Toast.LENGTH_SHORT).show();
		}
		try {
			newFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(newFile.exists() && newFile.canWrite())
		{
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(newFile);
				fos.write(listForum.get(lsItemID).getTitle().getBytes());
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			finally
			{
				if (fos != null){
					try {
						fos.flush();
						fos.close();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			Toast.makeText(this, "Write SD Card", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(this, "文件写入失败", Toast.LENGTH_SHORT).show();
		}
	}
}
