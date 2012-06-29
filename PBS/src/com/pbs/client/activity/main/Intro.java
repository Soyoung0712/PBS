package com.pbs.client.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.pbs.R;
import com.pbs.client.activity.mygroup.GroupList;

public class Intro extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_main);// 로딩 이미지 있는곳
		initialize();
	}

	private void initialize() {

		Handler handler = new Handler() {
			
			public void handleMessage(Message msg) {
				finish(); // 액티비티 종료
				startActivity(new Intent(Intro.this, GroupList.class));// 이동될 클래스
			}
		};

		handler.sendEmptyMessageDelayed(0, 1500); // ms, 3초후 종료시킴
	}
}