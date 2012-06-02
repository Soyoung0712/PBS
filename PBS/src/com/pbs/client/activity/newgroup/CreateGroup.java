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

	private final int MEMBER_GET_MEMBER_LIST_ACTIVITY 	= 1;
	private final int MEMBER_GET_ADDRESS_LIST_ACTIVITY 	= 2;
	private final int ADMIN_GET_MEMBER_LIST_ACTIVITY 	= 3;
	private final int ADMIN_GET_ADDRESS_LIST_ACTIVITY 	= 4;
	
	private EditText groupname;	

	private EditText password;
	private EditText passwordresult;

	
	private Button plus3;
	

	private EditText groupNameFix;
	
	private Button mGroupResult;
	private Button mGroupCanclel;
	
	// 그룹원 관리
	private ArrayList<AddressUser> groupMemberList; // 그룹원 리스트
	private EditText etGroupMemberListInfo;  // "홍길동 외3명" 텍스트 박스
	private Button bGroupGetAddressList;     // "가져오기" 버튼
	
	
	// 관리자 관리
	private ArrayList<AddressUser> adminMemberList; // 그룹원 리스트
	private EditText etAdminMemberListInfo;  // "임꺽정 외3명" 텍스트 박스
	private Button bAdminGetAddressList;     // "가져오기" 버튼

	private CheckBox ch1;
	private CheckBox ch2;

	
	Editable etable;
	String result;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_create_group);
		
		groupMemberList = new ArrayList<AddressUser>();
		adminMemberList = new ArrayList<AddressUser>();
 
		groupNameFix = (EditText)findViewById(R.id.editText1);
		    etable = groupNameFix.getText();
		    result = etable.toString();
		  
		groupname = (EditText) findViewById(R.id.editText7);		
		bGroupGetAddressList = (Button) findViewById(R.id.button2);
		plus3 = (Button) findViewById(R.id.button3);
		bAdminGetAddressList = (Button) findViewById(R.id.button4);

		ch2 = (CheckBox) findViewById(R.id.checkBox2);
		password = (EditText) findViewById(R.id.editText3);
		passwordresult = (EditText) findViewById(R.id.editText4);
		mGroupResult = (Button) findViewById(R.id.button5);
		etGroupMemberListInfo = (EditText) findViewById(R.id.editText5);
		etAdminMemberListInfo = (EditText) findViewById(R.id.editText7);

		
		groupname.setEnabled(false);		

		plus3.setEnabled(false);
		bAdminGetAddressList.setEnabled(false);
		
	 
		 
	}
	@Override
	public void onResume() {
		super.onResume();

		ch2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (ch2.isChecked()) {
					groupname.setEnabled(true);					
					bGroupGetAddressList.setEnabled(true);
					plus3.setEnabled(true);
					bAdminGetAddressList.setEnabled(true);
				} else {
					groupname.setEnabled(false);
					plus3.setEnabled(false);
					bAdminGetAddressList.setEnabled(false);
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

		// "그룹원 관리" > "임꺽정 외3명" 텍스트 박스 클릭
		etGroupMemberListInfo.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent event) {			
				if (event.getAction() == KeyEvent.ACTION_UP) {

					Intent intent = new Intent(CreateGroup.this, GetMemberList.class);
					startActivity(intent);

				}
				return false;
			}
		});
		
		// "그룹원 관리" > "가져오기" 버튼
		bGroupGetAddressList.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(CreateGroup.this, GetAddressList.class);				
				startActivityForResult(intent, MEMBER_GET_ADDRESS_LIST_ACTIVITY);				
			}
		});		
		
		// "관리자 관리" > "임꺽정 외3명" 텍스트 박스 클릭 
		etAdminMemberListInfo.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent event) {
				if (event.getAction() == KeyEvent.ACTION_UP) {					
					// 일단 EditMember 클래스 로 해둠.;;
					Intent intent = new Intent(CreateGroup.this, GetMemberList.class); 
					startActivity(intent);
				}
				return false;
			}
		});
		
		// 관리자 번호 "가져오기" 버튼
		bAdminGetAddressList.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(CreateGroup.this, GetAddressList.class);				
				startActivityForResult(intent, ADMIN_GET_ADDRESS_LIST_ACTIVITY);
			}
		});	

	}
	
	/**
	 * Intent결과
	 */	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		switch (requestCode) {
		
			// "그룹원 관리" > "가져오기" 응답
			case MEMBER_GET_ADDRESS_LIST_ACTIVITY:				
				if (resultCode == RESULT_OK) {           	
	            	
					// 선택한 그룹원 추가
	            	ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>)intent.getSerializableExtra("addressUserList");
	            	// 중복 번호를 제외하고 추가한다
	            	addAllListSubDuplication(groupMemberList, addressUserList);
	            	// 텍스트 문구 수정
	            	if( groupMemberList != null && groupMemberList.size() > 0 ) {	            		
	            		String headerMemberName = groupMemberList.get(0).getName();
	            		etGroupMemberListInfo.setText(headerMemberName + " 외 " + (groupMemberList.size()-1) + "명");
	            	}
	            	
	            }
				break;
				
			// "관리자 관리" > "가져오기" 응답
			case ADMIN_GET_ADDRESS_LIST_ACTIVITY:				
				if (resultCode == RESULT_OK) {           	
	            	
					// 선택한 관리자 추가
	            	ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>)intent.getSerializableExtra("addressUserList");	
	            	// 중복 번호를 제외하고 추가한다
	            	addAllListSubDuplication(adminMemberList, addressUserList);
	            	// 텍스트 문구 수정
	            	if( adminMemberList != null && adminMemberList.size() > 0 ) {	            		
	            		String headerMemberName = adminMemberList.get(0).getName();
	            		etAdminMemberListInfo.setText(headerMemberName + " 외 " + (adminMemberList.size()-1) + "명");
	            	}
	            	
	            }
				break;
	
			default:
				break;
		}
        
    }   
	
	
	/**
	 * userList1에 userList2을 저장한다.
	 * 단 중복 전화번호는 저장 하지 않는다.
	 * @param userList1
	 * @param userList2
	 */
	private void addAllListSubDuplication(ArrayList<AddressUser> userList1, ArrayList<AddressUser> userList2 ) {
		
		if (userList1 != null && userList2 != null) {
			for( int i=0; i<userList2.size(); i++) {
				String dial1 = userList2.get(i).getDial();
				
				boolean bDuplication = false;
				for( int j=0; j<userList1.size(); j++) {
					// 중복 전화번호 사용자 있음
					if( dial1.equals(userList1.get(j).getDial())) {
						bDuplication = true;
						break;
					}
				}
				
				// 중복 전화번호가 없는사용자만 추가
				if( bDuplication == false) {
					userList1.add(userList2.get(i));
				}
			}
		}
		
	}

}
