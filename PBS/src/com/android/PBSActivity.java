package com.android;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class PBSActivity extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {			
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); 

		// ��� Tab
		tab();  
		
		//�׷� �߰� Button
		Button add_group = (Button)findViewById(R.id.button1);
		add_group.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showGroupAdd();
			} 
		});
	}
	
	//�׷� �߰�
	private void showGroupAdd() {
		LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout add_group_layout = (LinearLayout)vi.inflate(R.layout.addgroup, null);
		
		final EditText key = (EditText)add_group_layout.findViewById(R.id.key);
		final EditText pwd = (EditText)add_group_layout.findViewById(R.id.pwd);
		
		new AlertDialog.Builder(this).setTitle("�׷� �߰�")
		.setView(add_group_layout).setNeutralButton("�߰�", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(PBSActivity.this, "�׷� KEY : "+key.getText().toString()+"\nPASSWORD : "+pwd.getText().toString(), Toast.LENGTH_LONG).show();
			}
		}).show();
	}

	private void tab() {
		TabHost tabHost = getTabHost();

		//Tab1(�� �׷�)
		TabSpec tabSpec1 = tabHost.newTabSpec("Tab1").setIndicator("�� �׷�");
		tabSpec1.setContent(R.id.tab1);
		tabSpec1.setContent(R.id.button1);
		tabHost.addTab(tabSpec1);

		//Tab2(�� �׷� �����)
		TabSpec tabSpec2 = tabHost.newTabSpec("Tab2").setIndicator("�� �׷� �����");
		tabSpec2.setContent(R.id.tab2);
		tabHost.addTab(tabSpec2);

		tabHost.setCurrentTab(0);
	}
}