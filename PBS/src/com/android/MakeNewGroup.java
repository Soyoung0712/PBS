package com.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.pbs.client.model.AddressUser;

public class MakeNewGroup extends Activity {

	private final int CALLER_REQUEST = 1;
	
	EditText groupname;
	EditText groupnumber;

	EditText password;
	EditText passwordresult;

	Button plus;
	Button plus2;

	Button mGroupResult;
	Button mGroupCanclel;
	Button GroupList;

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
		GroupList = (Button) findViewById(R.id.button7);

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

		// 그룹원 관리 > "가져오기" 버튼
		plus.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(MakeNewGroup.this, GetPhoneList.class);				
				startActivityForResult(intent, CALLER_REQUEST);
				
			}
		});

		// 관리자 번호 "가져오기" 버튼
		plus2.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent1 = new Intent(MakeNewGroup.this,GetPhoneList.class);
				startActivity(intent1);
			}
		});

		// 추가된 그룹원 명단 눌렀을때
		GroupList.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(MakeNewGroup.this, EditMember.class);
				startActivity(intent);
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