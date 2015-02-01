package com.family.detective.message;


import com.example.family__detective.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationView extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification);
		
		TextView tv = (TextView) findViewById(R.id.tv_sms_sent);
		Bundle data = getIntent().getExtras();
		tv.setText(data.getString("number"));		
	}
}
