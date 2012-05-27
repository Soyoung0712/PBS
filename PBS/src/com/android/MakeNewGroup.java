package com.android;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TabHost;

public class MakeNewGroup extends TabActivity
{

	EditText groupname;
	EditText groupnumber;

	EditText password;
	EditText passwordresult;

	Button plus;
	Button plus2;
	
	Button mGroupResult;
	Button mGroupCanclel;
	
	CheckBox ch1;
	CheckBox ch2;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		TabHost tabHost = getTabHost();
		LayoutInflater inflater = LayoutInflater.from(this);
		inflater.inflate(R.layout.makenewgroup, tabHost.getTabContentView(), true);

		groupname = (EditText) findViewById(R.id.editText7);
		groupnumber = (EditText) findViewById(R.id.editText8);
		plus = (Button) findViewById(R.id.button2);
		plus2 = (Button) findViewById(R.id.button4);
	 
		ch2 = (CheckBox) findViewById(R.id.checkBox2);
		password = (EditText) findViewById(R.id.editText3);
		passwordresult = (EditText) findViewById(R.id.editText4);
		mGroupResult = (Button)findViewById(R.id.button5);
 
		groupname.setEnabled(false);
		groupnumber.setEnabled(false);
	 
		plus2.setEnabled(false);

		
		
	}

	@Override
	public void onResume()
	{
		super.onResume();

	  
		ch2.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton arg0, boolean arg1)
			{
				if (ch2.isChecked())
				{
					groupname.setEnabled(true);
					groupnumber.setEnabled(true);
					plus.setEnabled(true);
					plus2.setEnabled(true);
				}
				else
				{
					groupname.setEnabled(false);
					groupnumber.setEnabled(false);
					plus2.setEnabled(false);
				}
			}
		});
		
		mGroupResult.setOnClickListener(new OnClickListener()
		{			
			public void onClick(View arg0)
			{
				Intent intent = new Intent(MakeNewGroup.this,GroupActivity.class);
				startActivity(intent);
			}
		});
  
		// 그룹원 관리 에서 가져오기 버튼을 눌렀을때 전화번호 목록 가져오기//
		plus.setOnClickListener(new OnClickListener()
		{			
			public void onClick(View arg0)
			{
				Intent intent = new Intent(MakeNewGroup.this,GetPhoneList.class)	;
				startActivity(intent);
			}
		});
		
		// 관리자 번호 입력 에서 가져오기 버튼을 눌렀을때 전화번호 목록 가져오기//
		plus2.setOnClickListener(new OnClickListener()
		{			
			public void onClick(View arg0)
			{			
				Intent intent1 = new Intent(MakeNewGroup.this,GetPhoneList.class)	;
				startActivity(intent1);
			}
		});
		
		
		
		
	}
	
  
}