package com.activity.forum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bean.Forum;
import com.test1.login.R;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView D_title=(TextView)findViewById(R.id.D_title);
        TextView D_msg=(TextView)findViewById(R.id.D_msg);

        Intent D_intent=getIntent();
        Bundle bundle=D_intent.getExtras();
        Forum inform =(Forum)bundle.getSerializable("item");//得到序列化的数据

        D_title.setText(inform.getTitle());
        D_msg.setText(inform.getMsg());
    }
}
