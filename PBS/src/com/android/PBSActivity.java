///////////////////////////////
//타이틀바 만들어야함
///////////////////////////////

package com.android;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class PBSActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
              
        // 상단 탭
        TabHost tabHost = getTabHost();
        
        tabHost.addTab(tabHost.newTabSpec("Tab1")
        		.setIndicator("내 그룹")
        		.setContent(new Intent(this, MyGroupList.class)));
        tabHost.addTab(tabHost.newTabSpec("Tab2")
        		.setIndicator("새 그룹 만들기")
        		.setContent(new Intent(this, MakeNewGroup.class)));
    }
}