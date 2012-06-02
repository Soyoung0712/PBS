package com.pbs.client.activity.newgroup;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
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

import com.android.R;
import com.android.R.id;
import com.android.R.layout;
import com.pbs.client.activity.edit.GetMemberList;
import com.pbs.client.activity.edit.GetAddressList;
import com.pbs.client.model.AddressUser;

public class CreateGroup extends Activity {

	private final int CALLER_REQUEST = 1;
	
	EditText groupname;
	EditText groupnumber;

	EditText password;
	EditText passwordresult;

	Button plus;
	Button plus2;

	EditText groupNameFix;
	
	Button mGroupResult;
	Button mGroupCanclel;
	EditText GroupList;    // 일반 그룹원 편집
	EditText mGroupList; //관리자 그룹원 편집

	CheckBox ch1;
	CheckBox ch2;

	
	Editable etable;
	String result;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_create_group);
 
		groupNameFix = (EditText)findViewById(R.id.editText1);
		    etable = groupNameFix.getText();
		    result = etable.toString();
		  
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
				Intent intent = new Intent(CreateGroup.this,
						CreateGroupComplete.class);
				intent.putExtra("groupname", groupNameFix.getText());
			
				
				 
					startActivity(intent);
			 
			}
		});

		// 그룹원 관리 > "가져오기" 버튼
		plus.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(CreateGroup.this, GetAddressList.class);				
				startActivityForResult(intent, CALLER_REQUEST);
				
			}
		});

		// 관리자 번호 "가져오기" 버튼
		plus2.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent1 = new Intent(CreateGroup.this,GetAddressList.class);
				startActivity(intent1);
			}
		});

		// 그룹원 관리  명단 눌렀을때 편집 기능 ..
		GroupList.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent event) {
				// 그룬원 EditText 를 눌렀을때 발생되는 이벤트 처리 // 이벤트 발생후 수신인 편집 화면으로 이동
				if (event.getAction() == KeyEvent.ACTION_UP) {

					Intent intent = new Intent(CreateGroup.this, GetMemberList.class);
					startActivity(intent);

				}
				return false;
			}
		});
		
		mGroupList.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View arg0, MotionEvent event) {
				// 그룹원 관리자 EditText 를 눌렀을때 발생되는 이벤트 처리 // 이벤트 발생후 수신인 편집 화면으로 이동
				if (event.getAction() == KeyEvent.ACTION_UP) {
					
					// 일단 EditMember 클래스 로 해둠.;;
					Intent intent = new Intent(CreateGroup.this, GetMemberList.class); 
					startActivity(intent);

				}
				return false;
			}
		});

	}
	
	/**
	 * 응답
	 */	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
        if (requestCode == CALLER_REQUEST) {
        	
            if (resultCode == RESULT_OK) {           	
            	
            	ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>)intent.getSerializableExtra("addressUserList");
            	for( int i=0; i<addressUserList.size(); i++ ) {
            		Log.d("addressUserList", addressUserList.get(i).getName() );
            	}
            	
            }
        }
    }
   

}
