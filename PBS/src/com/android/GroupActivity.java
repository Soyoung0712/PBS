package com.android;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class GroupActivity extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_create_ok);

		TabHost tabHost = getTabHost();

		TabSpec tabSpec1 = tabHost.newTabSpec("Tab1").setIndicator("내 그룹");
		tabSpec1.setContent(R.id.tab1);
		tabHost.addTab(tabSpec1);

		TabSpec tabSpec2 = tabHost.newTabSpec("Tab2").setIndicator("새 그룹 만들기");
		tabSpec2.setContent(R.id.tab2);
		tabHost.addTab(tabSpec2);
		
		tabHost.setCurrentTab(1);
		
		Button mNewGroupComplete = (Button)findViewById(R.id.button1);
		Button mMemberInvite = (Button)findViewById(R.id.button2);
		
		
		// 그룹생성후 완료버튼 누를시 메인페이지로
		mNewGroupComplete.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent intent = new Intent(GroupActivity.this,PBSActivity.class);
				startActivity(intent);
			}
		});
		
		//그룹생성후 초대하기 버튼 누를떄
		mMemberInvite.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent intent1 = new Intent(GroupActivity.this,InviteMember.class);
				startActivity(intent1);
			}
		});
	}	
}