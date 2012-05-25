package com.android;

import android.app.TabActivity;
import android.os.Bundle;
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
		
		tabHost.setCurrentTab(0);
	}	
}