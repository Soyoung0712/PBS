package com.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class MakeNewGroup extends Activity {

	EditText groupname;
	EditText groupnumber;

	EditText password;
	EditText passwordresult;

	Button plus;
	Button plus2;

	Button mGroupResult;
	Button mGroupCanclel;
	EditText GroupList;    // 일반 그룹원 편집
	EditText mGroupList; //관리자 그룹원 편집

	CheckBox ch1;
	CheckBox ch2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.makenewgroup);

		groupname = (EditText) findViewById(R.id.editText7);
		groupnumber = (EditText) findViewById(R.id.editText8);
		plus = (Button) findViewById(R.id.button2);
		plus2 = (Button) findViewById(R.id.button4);

		ch2 = (CheckBox) findViewById(R.id.checkBox2);
		password = (EditText) findViewById(R.id.editText3);
		passwordresult = (EditText) findViewById(R.id.editText4);
		mGroupResult = (Button) findViewById(R.id.button5);
		GroupList = (EditText) findViewById(R.id.editText5);
		mGroupList = (EditText) findViewById(R.id.editText7);

		
		groupname.setEnabled(false);
		groupnumber.setEnabled(false);

		plus2.setEnabled(false);

	}

	@Override
	public void onResume() {
		super.onResume();

		ch2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (ch2.isChecked()) {
					groupname.setEnabled(true);
					groupnumber.setEnabled(true);
					plus.setEnabled(true);
					plus2.setEnabled(true);
				} else {
					groupname.setEnabled(false);
					groupnumber.setEnabled(false);
					plus2.setEnabled(false);
				}
			}
		});

		// 그룹생성 완료버튼 눌렀을떄
		mGroupResult.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(MakeNewGroup.this,
						GroupActivity.class);
				startActivity(intent);
			}
		});

		// 그룹원 관리 에서 가져오기 버튼을 눌렀을때 전화번호 목록 가져오기//
		plus.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(MakeNewGroup.this,
						GetPhoneList.class);
				startActivity(intent);
			}
		});

		// 관리자 번호 입력 에서 가져오기 버튼을 눌렀을때 전화번호 목록 가져오기//
		plus2.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent1 = new Intent(MakeNewGroup.this,
						GetPhoneList.class);
				startActivity(intent1);
			}
		});

		// 그룹원 관리  명단 눌렀을때 편집 기능 ..
		GroupList.setOnTouchListener(new OnTouchListener()
		{
			
			
			public boolean onTouch(View arg0, MotionEvent event)
			{
				// 그룬원   EditText 를 눌렀을때 발생되는 이벤트 처리 // 이벤트 발생후 수신인 편집 화면으로 이동
				if(event.getAction() == KeyEvent.ACTION_UP)
				{
					 
					Intent intent = new Intent(MakeNewGroup.this, EditMember.class);
					startActivity(intent);
					 
				}
				 return false;
			}
		});
		
		
		
		
		mGroupList.setOnTouchListener(new OnTouchListener()
		{
			
			
			public boolean onTouch(View arg0, MotionEvent event)
			{
				// 그룹원 관리자  EditText 를 눌렀을때 발생되는 이벤트 처리 // 이벤트 발생후 수신인 편집 화면으로 이동
				if(event.getAction() == KeyEvent.ACTION_UP)
				{
					 
					Intent intent = new Intent(MakeNewGroup.this, EditMember.class);  //일단 EditMember 클래스 로 해둠.;;
					startActivity(intent);
					 
				}
				 return false;
			}
		});
		
		
		
		

	}

}