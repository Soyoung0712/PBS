///////////////////////////////
//Ÿ��Ʋ�� ��������
///////////////////////////////

package com.pbs.client.activity.main;

import com.pbs.client.activity.mygroup.GroupList;
import com.pbs.client.activity.newgroup.CreateGroup;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class PbsStart extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        // ��� ��
        TabHost tabHost = getTabHost();
        
        tabHost.addTab(tabHost.newTabSpec("Tab1")
        		.setIndicator("�� �׷�")
        		.setContent(new Intent(this, GroupList.class)));
        tabHost.addTab(tabHost.newTabSpec("Tab2")
        		.setIndicator("�� �׷� �����")
        		.setContent(new Intent(this, CreateGroup.class)));
        
     
    }
}