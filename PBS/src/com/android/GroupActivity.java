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

		TabSpec tabSpec1 = tabHost.newTabSpec("Tab1").setIndicator("�� �׷�");
		tabSpec1.setContent(R.id.tab1);
		tabHost.addTab(tabSpec1);

		TabSpec tabSpec2 = tabHost.newTabSpec("Tab2").setIndicator("�� �׷� �����");
		tabSpec2.setContent(R.id.tab2);
		tabHost.addTab(tabSpec2);
		
		tabHost.setCurrentTab(1);
		
		Button mNewGroupComplete = (Button)findViewById(R.id.button1);
		Button mMemberInvite = (Button)findViewById(R.id.button2);
		
		
		// �׷������ �Ϸ��ư ������ ������������
		mNewGroupComplete.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent intent = new Intent(GroupActivity.this,PBSActivity.class);
				startActivity(intent);
			}
		});
		
		//�׷������ �ʴ��ϱ� ��ư ������
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