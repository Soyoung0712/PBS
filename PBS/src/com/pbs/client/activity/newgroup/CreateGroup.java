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
import com.pbs.client.util.UserGson;

public class CreateGroup extends Activity {

	private final int MEMBER_GET_MEMBER_LIST_ACTIVITY 	= 1;
	private final int MEMBER_GET_ADDRESS_LIST_ACTIVITY 	= 2;
	private final int ADMIN_GET_MEMBER_LIST_ACTIVITY 	= 3;
	private final int ADMIN_GET_ADDRESS_LIST_ACTIVITY 	= 4;
	
	private String myPhoneNum = "01077778888";
	private UserGson userGson = new UserGson();
	
	// 그룹명
	private EditText groupname;	
	// 비밀번호	
	private EditText password;
	// 비밀번호 확인
	private EditText passwordConfirm;
	// 그룹 공지사항	
	private EditText groupNotice;
	
	// 그룹원 관리	
	private ArrayList<AddressUser> groupMemberList; // 그룹원 리스트	
	private EditText etGroupMemberListInfo;  		// "홍길동 외3명" 텍스트 박스
	private Button bGroupGetAddressList;     		// "가져오기" 버튼
	
	// 관리자 관리
	private ArrayList<AddressUser> adminMemberList; // 그룹원 리스트
	private CheckBox chAdmin;						// 관리자 관리 체크 박스
	private EditText etAdminMemberListInfo;  		// "임꺽정 외3명" 텍스트 박스
	private Button bAdminGetAddressList;     		// "가져오기" 버튼
	
	// 생성완료
	private Button bGroupResult;
	// 취소
	private Button bGroupCancel;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_create_group);
		
		// 그룹명
		groupname = (EditText) findViewById(R.id.etGroupName);				
		
		// 비밀번호
		password = (EditText) findViewById(R.id.etPassword);
		// 비밀번호 확인
		passwordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
		
		// 그룹 공지사항 
		groupNotice = (EditText) findViewById(R.id.etGroupNotice);
		
		// 그룹원 관리
		groupMemberList = new ArrayList<AddressUser>();
		etGroupMemberListInfo = (EditText) findViewById(R.id.etGroupMemberListInfo);
		bGroupGetAddressList = (Button) findViewById(R.id.bGroupGetAddressList);
		
		// 관리자 관리
		adminMemberList = new ArrayList<AddressUser>();
		chAdmin = (CheckBox) findViewById(R.id.chAdmin);
		etAdminMemberListInfo = (EditText) findViewById(R.id.etAdminMemberListInfo);
		etAdminMemberListInfo.setEnabled(false);
		bAdminGetAddressList = (Button) findViewById(R.id.bAdminGetAddressList);
		bAdminGetAddressList.setEnabled(false);
		
		// 생성완료
		bGroupResult = (Button) findViewById(R.id.bGroupResult);
		bGroupCancel = (Button) findViewById(R.id.bGroupCancel);		 
		 
	}
	@Override
	public void onResume() {
		super.onResume();	

		// "그룹원 관리" > "그룹원 추가" 텍스트 박스 클릭
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
		
		// "관리자 번호입력" 체크박스
		chAdmin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (chAdmin.isChecked()) {
					etAdminMemberListInfo.setEnabled(true);
					bAdminGetAddressList.setEnabled(true);					
				} else {									
					etAdminMemberListInfo.setEnabled(false);
					bAdminGetAddressList.setEnabled(false);
				}
			}
		});	
		
		// "관리자 관리" > "관리자 추가" 텍스트 박스 클릭 
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
		
		// "생성완료" 버튼 클릭
		bGroupResult.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(CreateGroup.this,CreateGroupComplete.class);
				intent.putExtra("groupname", groupname.getText().toString());				
				
				// 그룹원 정보를 "송영석:010123456" 형태로 배열로 저장
				String[] users  = new String[groupMemberList.size()];				
				for( int i=0; i<groupMemberList.size(); i++ ) {
					String tmpData = groupMemberList.get(i).getName() + ":" + groupMemberList.get(i).getDial();
					users[i] = tmpData;
				}
				
				// 관리자 정보를  "송영석:010123456" 형태로 배열로 저장
				String[] admins = new String[adminMemberList.size()];
				for( int i=0; i<adminMemberList.size(); i++ ) {
					String tmpData = adminMemberList.get(i).getName() + ":" + adminMemberList.get(i).getDial();
					admins[i] = tmpData;
				}
				
				Log.d("CreateGroup users: " , users.toString());
				Log.d("CreateGroup admins: " , admins.toString());
				
				userGson.createGroup(	groupname.getText().toString(), 
										password.getText().toString(), 
										groupNotice.getText().toString(), 
										myPhoneNum, users, admins);
				startActivity(intent);
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
