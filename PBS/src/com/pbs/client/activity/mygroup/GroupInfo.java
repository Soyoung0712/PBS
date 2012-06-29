package com.pbs.client.activity.mygroup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.pbs.client.R;
import com.pbs.client.activity.edit.InviteSms;
import com.pbs.client.model.ActivityMap;
import com.pbs.client.model.TbGroup;
import com.pbs.client.util.DeviceManager;
import com.pbs.client.util.UserGson;

public class GroupInfo extends Activity {

	private String myPhoneNum = null;
	private UserGson userGson = new UserGson();

	// 그룹키
	private TextView tvGroupKeyVal;
	// 그룹패스워드
	private TextView tvGroupPasswordVal;
	// 그룹명
	private TextView tvGroupNameVal;
	// 그룹 공지사항
	private TextView tvGroupNoticeVal;

	// 그룹설정
	private Button bGroupUpdate;
	// 초대하기
	private Button bInvite;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 타이틀바 없애기
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.my_group_info);

		// 내 전화번호 가져오기
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);

		Intent intent = getIntent();
		final long pk_group = intent.getExtras().getLong("pk_group");

		// 그룹 정보 가져오기
		TbGroup tbGroup = userGson.getGroupInfo(pk_group, myPhoneNum);

		// 그룹명
		tvGroupNameVal = (TextView) findViewById(R.id.tvGroupNameVal);
		tvGroupNameVal.setText(">> " + tbGroup.getFd_group_name());
		// 그룹키
		tvGroupKeyVal = (TextView) findViewById(R.id.tvGroupKeyVal);
		tvGroupKeyVal.setText(">> " + tbGroup.getPk_group() + "");
		// 그룹패스워드
		tvGroupPasswordVal = (TextView) findViewById(R.id.tvGroupPasswordVal);
		tvGroupPasswordVal.setText(">> " + tbGroup.getFd_group_password() + "");		
		// 그룹 공지사항
		tvGroupNoticeVal = (TextView) findViewById(R.id.tvGroupNoticeVal);
		tvGroupNoticeVal.setText(">> " + tbGroup.getFd_group_notice());

		// 초대하기 버튼
		bInvite = (Button) findViewById(R.id.bInvite);
		// 생성완료 버튼
		bGroupUpdate = (Button) findViewById(R.id.bGroupUpdate);
		
		// "초대하기" 버튼 클릭
		bInvite.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {

				Intent intent = new Intent(GroupInfo.this, InviteSms.class);
				// intent.putExtra("groupMemberList", groupMemberList);
				intent.putExtra("pk_group", String.valueOf(pk_group));
				startActivity(intent);

			}
		});	
		
		// "그룹설정" 버튼 클릭
		bGroupUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {

				Intent intent = new Intent(GroupInfo.this, GroupModify.class);
				intent.putExtra("pk_group", pk_group);
				startActivityForResult(intent, ActivityMap.GROUP_MODIFY);
			}
		});	
		
		// 뒤로가기 버튼
		Button bBack = (Button) findViewById(R.id.bBack);
		bBack.setOnClickListener(new View.OnClickListener()	{
			public void onClick(View arg0)	{
				finish();				
			}
		});	
		
		// bold 처리
		// 제목
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);		
		tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// 생성완료				
		bGroupUpdate.setPaintFlags(bGroupUpdate.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// 초대하기				
		bInvite.setPaintFlags(bInvite.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);		
		
		TextView tvGroupKey = (TextView) findViewById(R.id.tvGroupKey);		
		tvGroupKey.setPaintFlags(tvGroupKey.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		TextView tvGroupPassword = (TextView) findViewById(R.id.tvGroupPassword);		
		tvGroupPassword.setPaintFlags(tvGroupPassword.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		TextView tvGroupName = (TextView) findViewById(R.id.tvGroupName);		
		tvGroupName.setPaintFlags(tvGroupName.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		TextView tvGroupNotice = (TextView) findViewById(R.id.tvGroupNotice);		
		tvGroupNotice.setPaintFlags(tvGroupNotice.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		
		tvGroupKeyVal.setPaintFlags(tvGroupKeyVal.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		tvGroupPasswordVal.setPaintFlags(tvGroupPasswordVal.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		tvGroupNameVal.setPaintFlags(tvGroupNameVal.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		tvGroupNoticeVal.setPaintFlags(tvGroupNoticeVal.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		switch (requestCode) {		
			// "그룹설정" 응답		
			case ActivityMap.GROUP_MODIFY:
				// "그룹설정"에서 "삭제", "설정완료" 버튼 클릭했을때
				if (resultCode == RESULT_OK) {	
					// 호출한 액티비티
					Intent groupInfoIntent = getIntent();					
					setResult(RESULT_OK, groupInfoIntent);
					finish();
					break;
				}
		}
		
	}
}
