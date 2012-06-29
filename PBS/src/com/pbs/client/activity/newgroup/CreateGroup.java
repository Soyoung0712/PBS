package com.pbs.client.activity.newgroup;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
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
import com.pbs.client.activity.main.WaitDlg;
import com.pbs.client.model.ActivityMap;
import com.pbs.client.model.AddressUser;
import com.pbs.client.model.TbGroup;
import com.pbs.client.util.DeviceManager;
import com.pbs.client.util.UserGson;

public class CreateGroup extends Activity
{	
	private String myPhoneNum = null;
	private UserGson userGson = new UserGson();

	// 그룹명
	private EditText etGroupName;
	// 비밀번호
	private EditText etPassword;
	// 비밀번호 확인
	private EditText etPasswordConfirm;
	// 그룹 공지사항
	private EditText etGroupNotice;

	// 그룹원 관리
	private ArrayList<AddressUser> groupMemberList; // 그룹원 리스트
	private EditText etGroupMemberListInfo; // "홍길동 외3명" 텍스트 박스
	private Button bGroupGetAddressList; // "가져오기" 버튼

	// 관리자 관리
	private ArrayList<AddressUser> adminMemberList; // 그룹원 리스트
	private CheckBox chAdmin; // 관리자 관리 체크 박스
	private EditText etAdminMemberListInfo; // "임꺽정 외3명" 텍스트 박스
	private Button bAdminGetAddressList; // "가져오기" 버튼

	// 생성완료
	private Button bGroupResult;	 

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// 타이틀바 없애기
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.new_create_group);

		// 내 전화번호 가져오기
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);

		// 그룹명
		etGroupName = (EditText) findViewById(R.id.etGroupName);

		// 비밀번호
		etPassword = (EditText) findViewById(R.id.etPassword);
		// 비밀번호 확인
		etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);

		// 그룹 공지사항
		etGroupNotice = (EditText) findViewById(R.id.etGroupNotice);

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
		
		// bold 처리
		// 제목
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);		
		tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// 생성완료				
		bGroupResult.setPaintFlags(bGroupResult.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);			
		
		TextView tvGroupName = (TextView) findViewById(R.id.tvGroupName);
		tvGroupName.setPaintFlags(tvGroupName.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		TextView tvGroupNotice = (TextView) findViewById(R.id.tvGroupNotice);
		tvGroupNotice.setPaintFlags(tvGroupNotice.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
		TextView tbPassword = (TextView) findViewById(R.id.tbPassword);
		tbPassword.setPaintFlags(tbPassword.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
		TextView tvPasswordConfirm = (TextView) findViewById(R.id.tvPasswordConfirm);
		tvPasswordConfirm.setPaintFlags(tvPasswordConfirm.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
		TextView tvGroupMemberListInfo = (TextView) findViewById(R.id.tvGroupMemberListInfo);
		tvGroupMemberListInfo.setPaintFlags(tvGroupMemberListInfo.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		TextView chText = (TextView) findViewById(R.id.chText);
		chText.setPaintFlags(chText.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		TextView managerText = (TextView) findViewById(R.id.managerText);
		managerText.setPaintFlags(managerText.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
	}

	@Override
	public void onResume()
	{
		super.onResume();

		// "그룹원 관리" > "그룹원 추가" 텍스트 박스 클릭
		etGroupMemberListInfo.setOnTouchListener(new OnTouchListener()
		{
			public boolean onTouch(View arg0, MotionEvent event)
			{
				if (event.getAction() == KeyEvent.ACTION_UP)
				{
					Intent intent = new Intent(CreateGroup.this, GetMemberList.class);
					intent.putExtra("memberList", groupMemberList);
					startActivityForResult(intent, ActivityMap.MEMBER_GET_MEMBER_LIST);
				}
				return false;
			}
		});

		// "그룹원 관리" > "가져오기" 버튼
		bGroupGetAddressList.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0) {
				
				Intent intent = new Intent(CreateGroup.this, GetAddressList.class);
				startActivityForResult(intent, ActivityMap.MEMBER_GET_ADDRESS_LIST);				

			}
		});

		// "관리자 번호입력" 체크박스
		chAdmin.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton arg0, boolean arg1)
			{
				if (chAdmin.isChecked()) {
					etAdminMemberListInfo.setEnabled(true);
					etAdminMemberListInfo.setBackgroundResource(R.drawable.edit_text_bg);
					bAdminGetAddressList.setEnabled(true);
				}else{
					etAdminMemberListInfo.setEnabled(false);
					etAdminMemberListInfo.setBackgroundResource(R.drawable.edit_text_bg_grey);
					bAdminGetAddressList.setEnabled(false);
				}
			}
		});

		// "관리자 관리" > "관리자 추가" 텍스트 박스 클릭
		etAdminMemberListInfo.setOnTouchListener(new OnTouchListener()
		{
			public boolean onTouch(View arg0, MotionEvent event)
			{
				if (event.getAction() == KeyEvent.ACTION_UP)
				{
					Intent intent = new Intent(CreateGroup.this, GetMemberList.class);
					intent.putExtra("memberList", adminMemberList);
					startActivityForResult(intent, ActivityMap.ADMIN_GET_MEMBER_LIST);
				}
				return false;
			}
		});

		// 관리자 번호 "가져오기" 버튼
		bAdminGetAddressList.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0) {
				
				Intent intent = new Intent(CreateGroup.this, GetAddressList.class);
				startActivityForResult(intent, ActivityMap.ADMIN_GET_ADDRESS_LIST);

			}
		});

		// "생성완료" 버튼 클릭
		bGroupResult.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {

				// -- 입력값 유효성 체크
				String groupName = etGroupName.getText().toString();
				String password = etPassword.getText().toString();
				String passwordConfirm = etPasswordConfirm.getText().toString();
				String groupNotice = etGroupNotice.getText().toString();

				boolean bCheckInput = true;
				String checkErrorMsg = "";
				if (bCheckInput && groupName.length() <= 0)	{
					bCheckInput = false;
					checkErrorMsg = "그룹명을 입력해 주세요.";
				}
				
				if (bCheckInput && password.length() <= 0) {
					bCheckInput = false;
					checkErrorMsg = "비밀번호를 입력해 주세요.";
				}
				
				if (bCheckInput && !password.equals(passwordConfirm)) {
					bCheckInput = false;
					checkErrorMsg = "비밀번호을 확인해 주세요";
				}

				// 입력값 유효성 오류
				if (bCheckInput == false) {
					Toast.makeText(CreateGroup.this, checkErrorMsg, Toast.LENGTH_LONG).show();

				// 입력값 요효성 체크 통과
				}else {
					
					WaitDlg dlg = new WaitDlg(CreateGroup.this, "그룹 생성", "그룹을 생성하고 있습니다");
					dlg.start();
					
					try {
						Intent intent = new Intent(CreateGroup.this, CreateGroupComplete.class);
						intent.putExtra("groupMame", etGroupName.getText().toString());
	
						// 그룹원 정보를 "송영석:010123456" 형태로 배열로 저장
						String[] users = new String[groupMemberList.size()];
						for (int i = 0; i < groupMemberList.size(); i++) {
							String tmpData = groupMemberList.get(i).getName() + ":" + groupMemberList.get(i).getDial();
							users[i] = tmpData;
						}
	
						// 관리자 정보를 "송영석:010123456" 형태로 배열로 저장
						String[] admins = new String[adminMemberList.size()];
						for (int i = 0; i < adminMemberList.size(); i++) {
							String tmpData = adminMemberList.get(i).getName() + ":" + adminMemberList.get(i).getDial();
							admins[i] = tmpData;
						}
	
						Log.d("CreateGroup users: ", users.toString());
						Log.d("CreateGroup admins: ", admins.toString());
	
						TbGroup tbGroupResult = userGson.createGroup(etGroupName.getText().toString(), etPassword.getText().toString(), etGroupNotice.getText().toString(), myPhoneNum, users, admins);
	
						intent.putExtra("groupName", groupName);
						intent.putExtra("groupKey", tbGroupResult.getPk_group());
						intent.putExtra("groupPassword", password);
	
						startActivity(intent);
					}finally{
						dlg.stopLocal();
						finish();
					}
					
				}

				
				
			}
		});
		
		// 뒤로가기 버튼
		Button bBack = (Button) findViewById(R.id.bBack);
		bBack.setOnClickListener(new View.OnClickListener()	{
			public void onClick(View arg0)	{
				finish();				
			}
		});	

	}

	/**
	 * Intent결과
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		switch (requestCode) {

			// "그룹원 관리" > "그룹원 추가" 응답
			case ActivityMap.MEMBER_GET_MEMBER_LIST:
				if (resultCode == RESULT_OK) {

					// 수정된 그룹원 정보 가져오기
					ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>) intent.getSerializableExtra("addressUserList");
					// 수정된 그룹원 정보 저장
					groupMemberList = addressUserList;
					// 텍스트 문구 수정
					if (groupMemberList != null && groupMemberList.size() > 0) {
						String headerMemberName = groupMemberList.get(0).getName();
						etGroupMemberListInfo.setText(headerMemberName + " 외 " + (groupMemberList.size() - 1) + "명");
					}else {
						etGroupMemberListInfo.setText("그룹원 추가");
					}

				}
				break;

			// "그룹원 관리" > "가져오기" 응답
			case ActivityMap.MEMBER_GET_ADDRESS_LIST:
				if (resultCode == RESULT_OK) {

					// 선택한 그룹원 추가
					ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>) intent.getSerializableExtra("addressUserList");
					// 중복 번호를 제외하고 추가한다
					addAllListSubDuplication(groupMemberList, addressUserList);
					// 텍스트 문구 수정
					if (groupMemberList != null && groupMemberList.size() > 0) {
						String headerMemberName = groupMemberList.get(0).getName();
						etGroupMemberListInfo.setText(headerMemberName + " 외 " + (groupMemberList.size() - 1) + "명");
					}					
				}
				break;

			// "관리자 관리" > "관리자 추가" 응답
			case ActivityMap.ADMIN_GET_MEMBER_LIST:
				if (resultCode == RESULT_OK) {

					// 수정된 그룹원 정보 가져오기
					ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>) intent.getSerializableExtra("addressUserList");
					// 수정된 그룹원 정보 저장
					adminMemberList = addressUserList;
					// 텍스트 문구 수정
					if (adminMemberList != null && adminMemberList.size() > 0) {
						String headerMemberName = adminMemberList.get(0).getName();
						etAdminMemberListInfo.setText(headerMemberName + " 외 " + (adminMemberList.size() - 1) + "명");
					}else {						
						etAdminMemberListInfo.setText("관리자 추가");
					}

				}
				break;

			// "관리자 관리" > "가져오기" 응답
			case ActivityMap.ADMIN_GET_ADDRESS_LIST:
				if (resultCode == RESULT_OK) {

					// 선택한 관리자 추가
					ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>) intent.getSerializableExtra("addressUserList");
					// 중복 번호를 제외하고 추가한다
					addAllListSubDuplication(adminMemberList, addressUserList);
					// 텍스트 문구 수정
					if (adminMemberList != null && adminMemberList.size() > 0) {
						String headerMemberName = adminMemberList.get(0).getName();
						etAdminMemberListInfo.setText(headerMemberName + " 외 " + (adminMemberList.size() - 1) + "명");
					}					
				}
				break;

			default:
				break;
		}

	}

	/**
	 * userList1에 userList2을 저장한다. 단 중복 전화번호는 저장 하지 않는다.
	 * 
	 * @param userList1
	 * @param userList2
	 */
	private void addAllListSubDuplication(ArrayList<AddressUser> userList1, ArrayList<AddressUser> userList2)
	{

		if (userList1 != null && userList2 != null)
		{
			for (int i = 0; i < userList2.size(); i++)
			{
				String dial1 = userList2.get(i).getDial();

				boolean bDuplication = false;
				for (int j = 0; j < userList1.size(); j++)
				{
					// 중복 전화번호 사용자 있음
					if (dial1.equals(userList1.get(j).getDial()))
					{
						bDuplication = true;
						break;
					}
				}

				// 중복 전화번호가 없는사용자만 추가
				if (bDuplication == false)
				{
					userList1.add(userList2.get(i));
				}
			}
		}

	}

}
