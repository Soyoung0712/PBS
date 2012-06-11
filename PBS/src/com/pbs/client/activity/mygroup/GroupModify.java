package com.pbs.client.activity.mygroup;

import java.util.ArrayList;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.R;
import com.pbs.client.activity.edit.GetAddressList;
import com.pbs.client.activity.edit.GetMemberList;
import com.pbs.client.activity.newgroup.InviteSms;
import com.pbs.client.model.AddressUser;
import com.pbs.client.model.TbAccessUser;
import com.pbs.client.model.TbGroup;
import com.pbs.client.model.TbMember;
import com.pbs.client.util.DeviceManager;
import com.pbs.client.util.UserGson;
 

public class GroupModify extends Activity {

	private final int MEMBER_GET_MEMBER_LIST_ACTIVITY 	= 1;
	private final int MEMBER_GET_ADDRESS_LIST_ACTIVITY 	= 2;
	private final int ADMIN_GET_MEMBER_LIST_ACTIVITY 	= 3;
	private final int ADMIN_GET_ADDRESS_LIST_ACTIVITY 	= 4;
	
	private String myPhoneNum = null;
	private UserGson userGson = new UserGson();
	
	// 그룹명
	private EditText etGroupName;	
	// 그룹키
	private TextView tvGroupKey;
	
	// 비밀번호 관리 체크 박스
	private CheckBox chPassword;						
	// 비밀번호	
	private EditText etPassword;
	// 비밀번호 확인
	private EditText etPasswordConfirm;
	// 그룹 공지사항	
	private EditText etGroupNotice;
	
	// 그룹원 관리	
	private ArrayList<AddressUser> groupMemberList; // 그룹원 리스트	
	private EditText etGroupMemberListInfo;  		// "홍길동 외3명" 텍스트 박스
	private Button bGroupGetAddressList;     		// "가져오기" 버튼
	
	// 관리자 관리
	private ArrayList<AddressUser> adminMemberList; // 그룹원 리스트
	private CheckBox chAdmin;						// 관리자 관리 체크 박스
	private EditText etAdminMemberListInfo;  		// "임꺽정 외3명" 텍스트 박스
	private Button bAdminGetAddressList;     		// "가져오기" 버튼
	
	// 설정완료
	private Button bGroupUpdate;
	// SMS알림
	private Button bSmsNoti;
	// 취소
	private Button bCancel;	
	// 삭제
	private Button bGroupDelete;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_group_modify);
		
		// 내 전화번호 가져오기
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);
		
		Intent intent = getIntent();
		final long pk_group = intent.getExtras().getLong("pk_group");
		
		// 그룹 정보 가져오기
		TbGroup tbGroup	= userGson.getGroupInfo(pk_group, myPhoneNum);
		
		// 그룹명
		etGroupName = (EditText) findViewById(R.id.etGroupName);
		etGroupName.setText(tbGroup.getFd_group_name());
		// 그룹키
		tvGroupKey = (TextView) findViewById(R.id.tvGroupKey);
		tvGroupKey.setText(tbGroup.getPk_group() + "");
		
		// 비밀번호 체크박스
		chPassword = (CheckBox) findViewById(R.id.chPassword);
		// 비밀번호
		etPassword = (EditText) findViewById(R.id.etPassword);
		etPassword.setEnabled(false);
		// 비밀번호 확인
		etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
		etPasswordConfirm.setEnabled(false);
		
		// 그룹 공지사항 
		etGroupNotice = (EditText) findViewById(R.id.etGroupNotice);
		etGroupNotice.setText(tbGroup.getFd_group_notice());
		
		// 그룹원 관리
		List<TbMember> tmpGroupMemberList 	= userGson.getMemeberList(pk_group, myPhoneNum); // 그룹원 정보 가져오기
		groupMemberList = new ArrayList<AddressUser>();
		for(int i=0; i<tmpGroupMemberList.size(); i++) {
			TbMember tbMember = tmpGroupMemberList.get(i);
			AddressUser addressUser = new AddressUser();
			addressUser.setName(tbMember.getFd_member_name());
			addressUser.setDial(tbMember.getFd_member_phone());
			groupMemberList.add(addressUser);
		}
		etGroupMemberListInfo = (EditText) findViewById(R.id.etGroupMemberListInfo);
		bGroupGetAddressList = (Button) findViewById(R.id.bGroupGetAddressList);
		// 그룹원이 있는경우 "그룹원 추가" 텍스트 수정
    	if( groupMemberList != null && groupMemberList.size() > 0 ) {	            		
    		String headerMemberName = groupMemberList.get(0).getName();
    		etGroupMemberListInfo.setText(headerMemberName + " 외 " + (groupMemberList.size()-1) + "명");
    	}
		
		// 관리자 관리				
		List<TbAccessUser> tmpAccessUserList = userGson.getAdminList(pk_group, myPhoneNum); // 관리자 정보 가져오기
		adminMemberList = new ArrayList<AddressUser>();
		for(int i=0; i<tmpAccessUserList.size(); i++) {	
			TbAccessUser tbAccessUser = tmpAccessUserList.get(i);
			AddressUser addressUser = new AddressUser();
			addressUser.setName(tbAccessUser.getFd_member_name());
			addressUser.setDial(tbAccessUser.getFd_access_phone());
			adminMemberList.add(addressUser);
		}		
		
		chAdmin = (CheckBox) findViewById(R.id.chAdmin);
		etAdminMemberListInfo = (EditText) findViewById(R.id.etAdminMemberListInfo);
		// 관리자이 있는경우 "관리자 추가" 텍스트 수정
    	if( adminMemberList != null && adminMemberList.size() > 0 ) {	            		
    		String headerMemberName = adminMemberList.get(0).getName();
    		etAdminMemberListInfo.setText(headerMemberName + " 외 " + (adminMemberList.size()-1) + "명");
    	}
		etAdminMemberListInfo.setEnabled(false);
		bAdminGetAddressList = (Button) findViewById(R.id.bAdminGetAddressList);
		bAdminGetAddressList.setEnabled(false);
		
		// 생성완료 버튼
		bGroupUpdate = (Button) findViewById(R.id.bGroupUpdate);
		// Sms알림 버튼
		bSmsNoti 	 = (Button) findViewById(R.id.bSmsNoti);
		// 취소 버튼
		bCancel 	 = (Button) findViewById(R.id.bCancel);
		// 삭제 버튼
		bGroupDelete = (Button) findViewById(R.id.bGroupDelete);
		
		//-- 이벤트 등록 --//
		//
		// "비밀번호 수정" 체크박스
		chPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (chPassword.isChecked()) {
					etPassword.setEnabled(true);
					etPasswordConfirm.setEnabled(true);					
				} else {									
					etPassword.setEnabled(false);
					etPasswordConfirm.setEnabled(false);
				}
			}
		});	

		// "그룹원 관리" > "그룹원 추가" 텍스트 박스 클릭
		etGroupMemberListInfo.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent event) {				
				if (event.getAction() == KeyEvent.ACTION_UP) {					
					Intent intent = new Intent(GroupModify.this, GetMemberList.class);
					intent.putExtra("memberList", groupMemberList);
					startActivityForResult(intent, MEMBER_GET_MEMBER_LIST_ACTIVITY);					
				}
				return false;
			}
			
		});
		
		// "그룹원 관리" > "가져오기" 버튼
		bGroupGetAddressList.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(GroupModify.this, GetAddressList.class);				
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
					Intent intent = new Intent(GroupModify.this, GetMemberList.class);
					intent.putExtra("memberList", adminMemberList);
					startActivityForResult(intent, ADMIN_GET_MEMBER_LIST_ACTIVITY);
				}
				return false;
			}
		});
		
		
		// 관리자 번호 "가져오기" 버튼
		bAdminGetAddressList.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(GroupModify.this, GetAddressList.class);				
				startActivityForResult(intent, ADMIN_GET_ADDRESS_LIST_ACTIVITY);
			}
		});	
		
		// "설정완료" 버튼 클릭
		bGroupUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				
				//-- 입력값 유효성 체크
				String groupName 		= etGroupName.getText().toString();
				String password 		= etPassword.getText().toString();
				String passwordConfirm 	= etPasswordConfirm.getText().toString();
				String groupNotice 		= etGroupNotice.getText().toString();
				
				boolean bCheckInput = true;
				String checkErrorMsg = "";
				if( bCheckInput && groupName.length() <= 0 ) {
					bCheckInput = false;
					checkErrorMsg = "그룹명을 입력해 주세요.";
				}
				if( chPassword.isChecked() ) {
					if( bCheckInput && password.length() <= 0 ) {
						bCheckInput = false;
						checkErrorMsg = "비밀번호를 입력해 주세요.";
					}				
					if( bCheckInput && !password.equals(passwordConfirm) ) {
						bCheckInput = false;				
						checkErrorMsg = "비밀번호을 확인해 주세요";
					}
				}
				
				// 입력값 유효성 오류
				if( bCheckInput == false ) {
					Toast.makeText(GroupModify.this, checkErrorMsg,Toast.LENGTH_LONG).show();
					
				// 입력값 요효성 체크 통과
				}else {							
					
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
					
					Log.d("GroupModify users: " , users.toString());
					Log.d("GroupModify admins: " , admins.toString());					
					
					// 비밀번호를 변경하지 않으면, 비밀번호는 공백으로 치환
					if( !chPassword.isChecked() ) {
						etPassword.setText("");
					}
					// 그룹 업데이트
					userGson.updateGroup(pk_group, 
										 etGroupName.getText().toString(), 
										 etPassword.getText().toString(),
										 etGroupNotice.getText().toString(), 
										 myPhoneNum,
										 users, 
										 admins);
					
					finish();
				}
				
			}
		});
		
		// "SMS 알림" 버튼 클릭
		bSmsNoti.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				
				Intent intent = new Intent(GroupModify.this,InviteSms.class);
				//intent.putExtra("groupMemberList", groupMemberList);
				intent.putExtra("pk_group", String.valueOf(pk_group));
				startActivity(intent);
				
			}
		});
		
		// "취소" 버튼 클릭
		bCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				finish();
			}
		});
		
		// "삭제 " 버튼 클릭
		bGroupDelete.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// 그룹 삭제
				userGson.deleteGroup(pk_group, myPhoneNum);
				finish();
			}
		});
	}	
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	/**
	 * Intent결과
	 */	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		switch (requestCode) {
		
			// "그룹원 관리" > "그룹원 추가" 응답
			case MEMBER_GET_MEMBER_LIST_ACTIVITY:				
				if (resultCode == RESULT_OK) {           	
	            	
					// 수정된 그룹원 정보 가져오기
	            	ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>)intent.getSerializableExtra("addressUserList");
	            	// 수정된 그룹원 정보 저장
	            	groupMemberList = addressUserList;
	            	// 텍스트 문구 수정
	            	if( groupMemberList != null && groupMemberList.size() > 0 ) {	            		
	            		String headerMemberName = groupMemberList.get(0).getName();
	            		etGroupMemberListInfo.setText(headerMemberName + " 외 " + (groupMemberList.size()-1) + "명");
	            	}
	            	
	            }
				break;
						
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
				
			// "관리자 관리" > "관리자 추가" 응답
			case ADMIN_GET_MEMBER_LIST_ACTIVITY:				
				if (resultCode == RESULT_OK) {           	
	            	
					// 수정된 그룹원 정보 가져오기
	            	ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>)intent.getSerializableExtra("addressUserList");
	            	// 수정된 그룹원 정보 저장
	            	adminMemberList = addressUserList;
	            	// 텍스트 문구 수정
	            	if( adminMemberList != null && adminMemberList.size() > 0 ) {	            		
	            		String headerMemberName = adminMemberList.get(0).getName();
	            		etAdminMemberListInfo.setText(headerMemberName + " 외 " + (adminMemberList.size()-1) + "명");
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
