package com.adapter;

import java.util.List;

import com.adapter.ForumAdapter.DataWrapper;
import com.bean.Forum;
import com.bean.User;
import com.example.myapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class UsersAdapter extends BaseAdapter{

	private List<User> mList;
	private Context mContext;
	
	public UsersAdapter(Context pContext,List<User>pList) {
		// TODO Auto-generated constructor stub
		this.mContext=pContext;
		this.mList = pList;
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
		DataWrapper dataWrapper;
		TextView uid;
		TextView uname;
		TextView upwd;
	    TextView sex;
		
		
	    if(convertView==null)
		{
			LayoutInflater inflater=LayoutInflater.from(mContext);
			convertView=inflater.inflate(R.layout.udblistviewitem, null);//将udblistview.xml文件绑定到convertView
			uid=(TextView)convertView.findViewById(R.id.textviewID);
			uname=(TextView)convertView.findViewById(R.id.textviewUname);
			upwd=(TextView)convertView.findViewById(R.id.textviewPwd);
			sex=(TextView)convertView.findViewById(R.id.textviewSex);
			
			dataWrapper=new DataWrapper(uid,uname,upwd,sex);
			convertView.setTag(dataWrapper);
		}
		else											//显示下一页的内容时
		{
			//利用之前的对象，不用重新new
			dataWrapper=(DataWrapper)convertView.getTag();
			uid=dataWrapper.uid;
			uname=dataWrapper.uname;
			upwd=dataWrapper.upwd;
			sex=dataWrapper.sex;
		}
	    
	    uid.setText(String.valueOf(mList.get(position).getUid()));
	    uname.setText(mList.get(position).getUserName());
	    upwd.setText(mList.get(position).getPassword());
	    sex.setText(String.valueOf(mList.get(position).getSex()));
	    
	    
	    return convertView;
	}
	
	public final class DataWrapper {
		public TextView uid;
		public TextView uname;
		public TextView upwd;
		public TextView sex;
		
		public DataWrapper(TextView uid,TextView uname,TextView upwd,TextView sex) {
			// TODO Auto-generated constructor stub
			this.uid=uid;
			this.uname=uname;
			this.upwd=upwd;
			this.sex=sex;
		}
	}
}
