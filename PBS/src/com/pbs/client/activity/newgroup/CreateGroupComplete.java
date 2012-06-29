package com.pbs.client.activity.newgroup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.R;
import com.pbs.client.activity.edit.InviteSms;
import com.pbs.client.activity.mygroup.GroupList;

public class CreateGroupComplete extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// 타이틀바 없애기
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.new_create_group_complete);

		Intent intent = getIntent();
		String groupName = intent.getExtras().get("groupName").toString();
		final String groupKey = intent.getExtras().get("groupKey").toString();
		final String groupPassword = intent.getExtras().get("groupPassword").toString();

		// 생성 그룹명
		TextView tvGroupNameVal = (TextView) findViewById(R.id.tvGroupNameVal);
		tvGroupNameVal.setText(groupName);
		// 생성 그룹키
		TextView tvGroupKeyVal = (TextView) findViewById(R.id.tvGroupKeyVal);
		tvGroupKeyVal.setText(groupKey);
		// 생성 그룹 패스워드
		TextView tvGroupPasswordVal = (TextView) findViewById(R.id.tvGroupPasswordVal);
		tvGroupPasswordVal.setText(groupPassword);
		
		// 그룹생성후 초대하기 버튼 누를떄
		Button bInvite = (Button) findViewById(R.id.bInvite);		
		bInvite.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {				
												
				Intent intent1 = new Intent(CreateGroupComplete.this, InviteSms.class);
				intent1.putExtra("pk_group", groupKey);
				intent1.putExtra("fd_group_password", groupPassword);				
				startActivity(intent1);
				
			}
		});		
		
		// "확인" 버튼 누를떄		
		Button bOk = (Button) findViewById(R.id.bOk);		
		bOk.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {	
				finish();
			}
		});		
		
		
		// bold 처리
		// 제목
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);		
		tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		
		tvGroupNameVal.setPaintFlags(tvGroupNameVal.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		tvGroupKeyVal.setPaintFlags(tvGroupKeyVal.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		tvGroupPasswordVal.setPaintFlags(tvGroupPasswordVal.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		
		bInvite.setPaintFlags(bInvite.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		bOk.setPaintFlags(bOk.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		
		TextView tvGroupName = (TextView) findViewById(R.id.tvGroupName);		
		tvGroupName.setPaintFlags(tvGroupName.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		TextView tvGroupKey = (TextView) findViewById(R.id.tvGroupKey);		
		tvGroupKey.setPaintFlags(tvGroupKey.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		TextView tvGroupPassword = (TextView) findViewById(R.id.tvGroupPassword);		
		tvGroupPassword.setPaintFlags(tvGroupPassword.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		
	}
}