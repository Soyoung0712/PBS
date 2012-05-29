package com.android;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class GroupSetting extends TabActivity
{

	EditText groupname;
	EditText groupnumber;

	EditText password;
	EditText passwordresult;
	
	Button numberUrl;
	Button plus;
	Button plus2;
	Button mResultOk;
	Button mCancel;

	CheckBox ch1;
	CheckBox ch2;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		
		
		TabHost tabHost = getTabHost();
		LayoutInflater inflater = LayoutInflater.from(this);
		inflater.inflate(R.layout.groupstting, tabHost.getTabContentView(), true);

		groupname = (EditText) findViewById(R.id.editText7);
		groupnumber = (EditText) findViewById(R.id.editText8);
		
		numberUrl = (Button)findViewById(R.id.button2);
		
		plus = (Button) findViewById(R.id.button3);
		plus2 = (Button) findViewById(R.id.button4);
		
		ch1 = (CheckBox) findViewById(R.id.checkBox1);
		ch2 = (CheckBox) findViewById(R.id.checkBox2);
		password = (EditText) findViewById(R.id.editText3);
		passwordresult = (EditText) findViewById(R.id.editText4);
		
		mResultOk = (Button)findViewById(R.id.button5);
		mCancel = (Button)findViewById(R.id.button7);
		
		TabSpec tabSpec1 = tabHost.newTabSpec("tab1").setIndicator("내 그룹");
		tabSpec1.setContent(R.id.tab2);
		tabHost.addTab(tabSpec1);

		TabSpec tabSpec2 = tabHost.newTabSpec("tab2").setIndicator("새 그룹 만들기");
		tabSpec2.setContent(R.id.tab1);
		tabHost.addTab(tabSpec2);

		password.setEnabled(false);
		passwordresult.setEnabled(false);
		groupname.setEnabled(false);
		groupnumber.setEnabled(false);
		plus.setEnabled(false);
		plus2.setEnabled(false);
	 
	}

	@Override
	public void onResume()
	{
		super.onResume();

	

		// 비밀번호 체크 박스 눌렸을때 //
		ch1.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton arg0, boolean arg1)
			{
				//체크 됫을때 입력 가능
				if (ch1.isChecked())
				{
					password.setEnabled(true);
					passwordresult.setEnabled(true);
				}
				//체크 안됫을때 입력불가능
				else
				{
					password.setEnabled(false);
					passwordresult.setEnabled(false);

				}

			}
		});

		// 관리자 번호 입력 체크박스 눌렸을때//
		ch2.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton arg0, boolean arg1)
			{
				// 체크 했을때 입력가능 하게 
				if (ch2.isChecked())
				{
					groupname.setEnabled(true);
					groupnumber.setEnabled(true);
					plus.setEnabled(true);
					plus2.setEnabled(true);
				}
				// 체크 안됫을때 입력 불가능
				else
				{
					groupname.setEnabled(false);
					groupnumber.setEnabled(false);
					plus.setEnabled(false);
					plus2.setEnabled(false);
				}
			}
		});
		
		// 그룹원 관리 에서 가져오기 버튼을 눌렀을때 전화번호 목록 가져오기//
		numberUrl.setOnClickListener(new OnClickListener()
		{			
			public void onClick(View arg0)
			{
				Intent intent = new Intent(GroupSetting.this,GetPhoneList.class)	;
				startActivity(intent);
			}
		});
		
		// 관리자 번호 입력 에서 가져오기 버튼을 눌렀을때 전화번호 목록 가져오기//
		plus2.setOnClickListener(new OnClickListener()
		{			
			public void onClick(View arg0)
			{			
				Intent intent1 = new Intent(GroupSetting.this,GetPhoneList.class)	;
				startActivity(intent1);
			}
		});
		
		
		
		//설정완료 버튼 누를시 설정 완료 하고 내그룹 화면으로 돌아가기
		mResultOk.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View arg0)
			{
				 
				Toast.makeText(GroupSetting.this, "설정 완료", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		
		mCancel.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0)
			{
				Toast.makeText(GroupSetting.this, "취소", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		

	}

 
}