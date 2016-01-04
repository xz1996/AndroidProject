package com.example.myapp;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.activity.forum.ForumActivity;
import com.adapter.DBAdapter;
import com.adapter.MsgAdapter;
import com.bean.ChatMessage;
import com.bean.Forum;
import com.bean.User;
import com.manager.UserManager;
import com.example.myapp.R;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;


/**
 * Created by Jay on 2015/8/28 0028.
 */
public class MyFragment extends Fragment {
	
	private UserManager um;
	private User user;
	public  ListView listview;
	public  List<ChatMessage> listMsg;
	
	private static final String HOST = "192.168.191.1";
    private static final int PORT = 12345;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    
    private String content = "";
    private StringBuilder sb = null;
	
    private EditText Uname;
    private EditText Pwd;
    private EditText sendMsg;
    private DBAdapter dbAdapter = null;
    private View view ;							//用于保存根据不同布局文件的ID所加载的视图
	private int rId;		//布局id
	
	private Handler handler;
	
	private boolean isLogin = false;
	private boolean isRegRight = false;
	
	
	/*定义访问模式*/
	public static int MODE = 0;
	/*定义一个SharedPreferences名。之后将以这个名字保存在Android文件系统中*/
	public static final String PREFERENCE_NAME = "SaveSetting";

    
    public MyFragment(int id) {
        this.rId = id;
    }
    
    public boolean getIsLogin()
    {
    	return isLogin;
    }
    public boolean getIsRegRight()
    {
    	return isRegRight;
    }
    
    public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public void setRegRight(boolean isRegRight) {
		this.isRegRight = isRegRight;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
    	
    	super.onActivityCreated(savedInstanceState);
    	dbAdapter = new DBAdapter(getActivity(),"MyApp.db");
		dbAdapter.open();
   }
    
   public void onDestroyView() {
    	super.onDestroyView();
    	dbAdapter.close();
    };
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	view = inflater.inflate(rId,container,false);
    	switch (rId)
    	{
    		case R.layout.activity_login:
    			Button buttonLoginF=(Button)view.findViewById(R.id.buttonlogin);
    			Button buttonLoginC=(Button)view.findViewById(R.id.buttonloginchat);
    			Button buttonQuit=(Button)view.findViewById(R.id.buttonquit);	//退出按钮

    			Uname = (EditText)view.findViewById(R.id.editLoginName);
    			Pwd   = (EditText)view.findViewById(R.id.editLoginPwd);
    			
    			CheckBox saveChb=(CheckBox)view.findViewById(R.id.RemMeChb);	//Remember me checkBox



    			//获取SharedPreferences实例
    			final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCE_NAME, MODE);
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

    			//为LoginChat按钮添加监听事件
    			buttonLoginC.setOnClickListener(new View.OnClickListener() {

    				@Override

    				public void onClick(View view) {
    					// TODO Auto-generated method stub

    					//得到所输入的用户名
    					String username = Uname.getText().toString();
    					String password = Pwd.getText().toString();

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
    							Intent welcome_intent = new Intent(getActivity(), ChatActivity.class);
    							isLogin = true;
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
    			
    			//为LoginForum按钮添加监听事件
    			buttonLoginF.setOnClickListener(new View.OnClickListener() {

    				@Override

    				public void onClick(View view) {
    					// TODO Auto-generated method stub

    					//得到所输入的用户名
    					String username = Uname.getText().toString();
    					String password = Pwd.getText().toString();

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
    							Intent welcome_intent = new Intent(getActivity(), ForumActivity.class);
    							Bundle bundle = new Bundle();
    							bundle.putString("UserName", username);
    							isLogin = true;
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

    			//退出按钮
    			buttonQuit.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					dbAdapter.close();
    					getActivity().finish();
    				}
    			});
    			break;
    		case R.layout.activity_register:
    			Spinner spEdu=(Spinner)view.findViewById(R.id.spinnerEducation);
    			Spinner spSchool=(Spinner)view.findViewById(R.id.spinnerschool);
    			Spinner spMajor=(Spinner)view.findViewById(R.id.spinnerSpecialty);

    			Button	regButtonYes = (Button)view.findViewById(R.id.buttonregYes);
    			final EditText regUname = (EditText)view.findViewById(R.id.regusername);
    			final EditText regPassword = (EditText)view.findViewById(R.id.regpassword);

    			final RadioGroup radioSex = (RadioGroup)view.findViewById(R.id.radioGroupSex);

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
    			ArrayAdapter<String> eduAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listEdu);
    			ArrayAdapter<String> schoolAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, schoolItems);
    			ArrayAdapter<String> majorAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, majorItems);
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
    					
    					if(uName == null||uPassword == null || uName.length()<=0||uPassword.length()<=0)
    					{
    						showDialog("Warning", "非法的用户名或密码");
    					}
    					else{
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
	    						Toast.makeText(getActivity(),"注册成功！",Toast.LENGTH_SHORT).show();
	    						isRegRight = true;
	    					}
	    					else
	    					{
	    						Toast.makeText(getActivity(),"注册失败，用户名已经存在！！",Toast.LENGTH_SHORT).show();
	    					}
    					}
    				}
    			});
    			
    			break;
    		case R.layout.activity_chat:
    			
    			
    			break;
    			
    	}
        return view;
    }
    
  //用来保存用户的用户名和密码
  	public boolean SaveUserInformation(SharedPreferences sharedPreferences)
  	{
  		SharedPreferences.Editor editor = sharedPreferences.edit();
  		editor.putString("UserName",Uname.getText().toString());
  		editor.putString("Password",Pwd.getText().toString());

  		//调用commit（）保存修改
  		if(editor.commit())
  			return true;
  		else
  			return false;
  	}

  //从SaveInformation文件中读取用户之前的信息
  	public void ReadInformation(SharedPreferences sharedPreferences)
  	{
  		String name = sharedPreferences.getString("UserName","");
  		String password = sharedPreferences.getString("Password","");

  		Uname.setText(name);
  		Pwd.setText(password);
  	}
  	
  //创建对话框，用于提示用户
  	public void showDialog(String title,String msg)
  	{
  		AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
  		dialog.setTitle(title).setMessage(msg).setPositiveButton("确定", null).show();
  	}
  	
}