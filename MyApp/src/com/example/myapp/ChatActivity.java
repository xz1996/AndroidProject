package com.example.myapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.adapter.MsgAdapter;
import com.bean.ChatMessage;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity implements Runnable{

	
	
//	private static final String HOST = "192.168.191.1";
	private static final String HOST = "104.129.0.167";
    private static final int PORT = 12345;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    
    private String content = "";
    
    private EditText sendMsg;
    private Button sendBt;
    
    private  ListView listview;
    private  List<ChatMessage> listMsg;
    private MsgAdapter msgAdapter;
    
    private Thread linkThread = null;


  
	
//	private MyGestureListener mgListener;
//    private GestureDetector mDetector;
//    private final static int MIN_MOVE = 20;   //手势移动最小距离
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chat);
		
		listview = (ListView)findViewById(R.id.chatlistView);
		listMsg = new ArrayList<ChatMessage>();
		
		sendMsg = (EditText)findViewById(R.id.sendMsgEditText);
		sendBt = (Button)findViewById(R.id.sendbt);
		
		
		//手势
//		mgListener = new MyGestureListener();
//      mDetector = new GestureDetector(this, mgListener);


		//另外开辟一个线程与服务端连接
		Thread linkThread = new Thread() {

            public void run() {
	                try {
	                		socket = new Socket(HOST, PORT);
	                		if(socket != null){
			                	in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
			                            socket.getOutputStream(),"UTF-8")), true);
		                  
		                    	handler.sendEmptyMessage(0x01);
		                    	
		                    	try {
									sleep(10000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                		}
	                	
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
        };
        linkThread.start();
        
        
        msgAdapter = new MsgAdapter(this,listMsg);
		listview.setAdapter(msgAdapter);
        
        sendBt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(socket != null){
					if(socket.isConnected()&&!socket.isClosed()){
						String msg = sendMsg.getText().toString();
	                    if (!socket.isOutputShutdown()) {
	                        out.println(msg);
	                        listMsg.add(new ChatMessage(msg, 0));
	                        msgAdapter.notifyDataSetChanged();
	                        sendMsg.setText("");		//清空发送区
	                    }
					}
					else
						Toast.makeText(ChatActivity.this, "Off Line!", Toast.LENGTH_SHORT).show();
				}
				else 
					Toast.makeText(ChatActivity.this, "Off Line!", Toast.LENGTH_SHORT).show();
					
			}
		});
 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			if(linkThread != null){
				if(!linkThread.isAlive())
					linkThread.start();
			}
		}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try {
			if(socket !=null){
				//socket.isConnected()返回的并不是当前连接状态，而是曾经是否连接过
				//socket.isClosed()可返回当前是否关闭
				if(socket.isConnected()&&!socket.isClosed())
				{
					out.println("bye");
					socket.close();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			if(socket !=null){
				if(socket.isConnected()&&!socket.isClosed())
				{
					out.println("bye");
					socket.close();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	//读取服务端发过来的信息
	@Override
	public void run() {
		// TODO Auto-generated method stub
		 try {
			 if(socket != null){
	             while (!socket.isClosed()) {
	                 if (socket.isConnected()) {
	                     if (!socket.isInputShutdown()) {
	                    	 if(in != null){
	    
		                         if ((content = in.readLine()) != null) {
		                             handler.sendEmptyMessage(0x02);
		                         }
	                    	 }
	                     }
	                 }
	             }
			 }
             
             handler.sendEmptyMessage(0x00);	//发送异常信号
             
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	}
	
	private Handler handler	= new Handler() {	 
		   public void handleMessage(Message msg) {
			   switch (msg.what)
			   {
			   	   case 0x00:
				   		Toast.makeText(ChatActivity.this, "Off Line!", Toast.LENGTH_SHORT).show();
				   		break;
			   		
				   case 0x01:
					    new Thread(ChatActivity.this).start();
					    break;
					   
				   case 0x02: 
		             	 listMsg.add(new ChatMessage(content,1));
		                 msgAdapter.notifyDataSetChanged();
						  break;
			   
			   }
	       }
	   };
	
/*	public class MyGestureListener implements GestureDetector.OnGestureListener {

  		@Override
  	    public boolean onDown(MotionEvent motionEvent) {
  	        return false;
  	    }

  	    @Override
  	    public void onShowPress(MotionEvent motionEvent) {
  	    	
  	    }

  	    @Override
  	    public boolean onSingleTapUp(MotionEvent motionEvent) {

  	        return false;
  	    }

  	    @Override
  	    public boolean onScroll(MotionEvent e1, MotionEvent e2, float v, float v1) {
  	    	
  	        return false;
  	    }

  	    @Override
  	    public void onLongPress(MotionEvent motionEvent) {

  	    }

  	    @Override
  	    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
  	   
  	    	//上滑发送信息
  	    	 if(e1.getY() - e2.getY() > MIN_MOVE){
  	    		String msg = sendMsg.getText().toString();
                 if (socket.isConnected()) {
                     if (!socket.isOutputShutdown()) {
                         out.println(msg);
                         sendMsg.setText("");		//清空发送区
                     }
                 }
  	         }
  	    	 else if(e1.getY() - e2.getY()  < MIN_MOVE){
  	       
  	             Toast.makeText(ChatActivity.this,"下滑",Toast.LENGTH_SHORT).show();
  	         }
  	        return true;
  	    }
  	}*/
	
	
}
