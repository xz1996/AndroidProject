package com.example.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Coder-pig on 2015/8/28 0028.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    //UI Object
    private TextView txt_topbar;
    private TextView txt_message;
    private TextView txt_login;
    private TextView txt_reg;
    private FrameLayout ly_content;
    //Fragment Object
    private MyFragment fg1,fg2,fg3;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fManager = getFragmentManager();
        bindViews();
        txt_login.performClick();   		//默认login按钮被点击
    }

    private void bindViews() {
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        txt_message = (TextView) findViewById(R.id.txt_message);
        txt_login = (TextView) findViewById(R.id.txt_login);
        txt_reg = (TextView) findViewById(R.id.txt_reg);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);

        txt_message.setOnClickListener(this);
        txt_login.setOnClickListener(this);
        txt_reg.setOnClickListener(this);
    }
    
    private void setAllSelectedFalse(){
        txt_message.setSelected(false);
        txt_login.setSelected(false);
        txt_reg.setSelected(false);
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)
        	fragmentTransaction.hide(fg1);
        if(fg2 != null)
        	fragmentTransaction.hide(fg2);
        if(fg3 != null)
        	fragmentTransaction.hide(fg3);
    }

    //创建对话框，用于提示用户
  	public void showDialog(String title,String msg)
  	{
  		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
  		dialog.setTitle(title).setMessage(msg).setPositiveButton("确定", null).show();
  	}

    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);			//先隐藏所有的fragment
        switch (v.getId()){
            case R.id.txt_login:
            	setAllSelectedFalse();
                txt_login.setSelected(true);
                if(fg1 == null){
                    fg1 = new MyFragment(R.layout.activity_login);
                    fTransaction.add(R.id.ly_content,fg1);
                }
                else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.txt_message:
            	setAllSelectedFalse();
            	txt_message.setSelected(true);
                if(fg2 == null){
                    fg2 = new MyFragment(R.layout.activity_chat);
                    fTransaction.add(R.id.ly_content,fg2);
                }else{
                    fTransaction.show(fg2);
                }
            	
            	if(!fg1.getIsLogin()){
            		showDialog("Waring!","未开放，请从登录界面进入聊天界面");
            	}
                break;
            case R.id.txt_reg:
            	setAllSelectedFalse();
                txt_reg.setSelected(true);
                if(fg3 == null){
                    fg3 = new MyFragment(R.layout.activity_register);
                    fTransaction.add(R.id.ly_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;
        }
        fTransaction.commit();
        if((v.getId() == R.id.txt_message))
        {
        	if(!fg1.getIsLogin())
        		txt_login.performClick();
        	else{
        		Intent welcome_intent = new Intent(this, ChatActivity.class);
        		startActivity(welcome_intent);
        	}
        }
        if((v.getId() == R.id.txt_reg))
        {
        	if(fg3.getIsRegRight()){
        		showDialog("Notes!","您已注册");
        		txt_login.performClick();
        	}
        }
    }
}
