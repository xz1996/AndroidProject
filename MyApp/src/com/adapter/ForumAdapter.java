package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;

import java.util.*;

import com.bean.Forum;
import com.example.myapp.R;

public class ForumAdapter extends BaseAdapter{

	private List<Forum> mList;
	private Context mContext;
	
	public ForumAdapter(Context pContext,List<Forum>pList){
		this.mContext=pContext;
		this.mList=pList;
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
		ImageView image;
		final TextView title;
		TextView msg;
		CheckBox chb;
		
		if(convertView==null)
		{
			LayoutInflater inflater=LayoutInflater.from(mContext);
			convertView=inflater.inflate(R.layout.items, null);//将items.xml文件绑定到convertView
			image=(ImageView)convertView.findViewById(R.id.imageViewitem1);
			title=(TextView)convertView.findViewById(R.id.textviewTitle);
			msg=(TextView)convertView.findViewById(R.id.textviewMsg);
			chb=(CheckBox)convertView.findViewById(R.id.checkBoxitem);
			
			dataWrapper=new DataWrapper(image,title,msg,chb);
			convertView.setTag(dataWrapper);
		}
		else											//显示下一页的内容时
		{
			//利用之前的对象，不用重新new
			dataWrapper=(DataWrapper)convertView.getTag();
			image=dataWrapper.image;
			title=dataWrapper.title;
			msg=dataWrapper.msg;
			chb=dataWrapper.chb;
		}
		
		image.setImageResource(mList.get(position).getImageid());	//设置图片
		title.setText(mList.get(position).getTitle());
		msg.setText(mList.get(position).getMsg());
		chb.setChecked(mList.get(position).isCheckStatus());
		
		title.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(v.getContext(),title.getText().toString(),Toast.LENGTH_SHORT).show();
			}
		});
		
		chb.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					Toast.makeText(mContext,"选中"+title.getText().toString(), Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(mContext,"取消选中"+title.getText().toString(), Toast.LENGTH_SHORT).show();
				}
			}
			
		});
		
		return convertView;
		
	}
	
	public final class DataWrapper {
		public ImageView image;
		public TextView title;
		public TextView msg;
		public CheckBox chb;
		
		public DataWrapper(ImageView image,TextView title,TextView msg,CheckBox chb) {
			// TODO Auto-generated constructor stub
			this.image=image;
			this.title=title;
			this.msg=msg;
			this.chb=chb;
		}

	} 
}


