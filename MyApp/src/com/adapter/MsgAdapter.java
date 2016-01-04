package com.adapter;

import java.util.List;

import com.adapter.UsersAdapter.DataWrapper;
import com.bean.ChatMessage;
import com.bean.User;
import com.example.myapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MsgAdapter extends BaseAdapter{

	private List<ChatMessage> mList;
	private Context mContext;
	private static final int VIEW_TYPE_COUNT = 2;	//总共有两种类型的布局文件
	
	public MsgAdapter(Context pContext,List<ChatMessage> pList)  {
		// TODO Auto-generated constructor stub
		this.mList=pList;
		this.mContext=pContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		DataWrapper dataWrapper1,dataWrapper2;
		TextView msg = null;
		int type = getItemViewType(position);
		if(convertView == null)
		{
			LayoutInflater inflater=LayoutInflater.from(mContext);
			
			switch (type)
			{
				case 0:
					convertView=inflater.inflate(R.layout.chatboxitem, null);//将chatboxitem.xml文件绑定到convertView
					msg = (TextView)convertView.findViewById(R.id.chattextview);
					dataWrapper1=new DataWrapper(msg);
					convertView.setTag(dataWrapper1);
					break;
				case 1:
					convertView=inflater.inflate(R.layout.chatboxitem2, null);//将chatboxitem2.xml文件绑定到convertView
					msg = (TextView)convertView.findViewById(R.id.chattextview2);
					dataWrapper2=new DataWrapper(msg);
					convertView.setTag(dataWrapper2);
					break;
				default:break;
			}
			
		}
		else											//显示下一页的内容时
		{
			//利用之前的对象，不用重新new
			switch (type)
			{
				case 0:
					dataWrapper1=(DataWrapper)convertView.getTag();
					msg=dataWrapper1.msg;
					break;
				case 1:
					dataWrapper2=(DataWrapper)convertView.getTag();
					msg=dataWrapper2.msg;
					break;
				default:break;
			}
			
		}
	    
	    msg.setText(mList.get(position).getMsg());
		    
	    return convertView;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		//注意，返回值是从0开始算的
		return mList.get(position).getWho();
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return VIEW_TYPE_COUNT;
	}

	public final class DataWrapper {
		public TextView msg;
		
		public DataWrapper(TextView msg) {
			// TODO Auto-generated constructor stub
			this.msg = msg;
		}
	}
}
