package com.pbs.client.activity.newgroup;

import android.app.Activity;
import android.content.Intent;
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
		TextView tvGroupName = (TextView) findViewById(R.id.tvGroupName);
		tvGroupName.setText(groupName);
		// 생성 그룹키
		TextView tvGroupKey = (TextView) findViewById(R.id.tvGroupKey);
		tvGroupKey.setText(groupKey);
		// 생성 그룹 패스워드
		TextView tvGroupPassword = (TextView) findViewById(R.id.tvGroupPassword);
		tvGroupPassword.setText(groupPassword);
		
		Button mMemberInvite = (Button) findViewById(R.id.button2);
		// 그룹생성후 초대하기 버튼 누를떄
		mMemberInvite.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				
				finish();
								
				Intent intent1 = new Intent(CreateGroupComplete.this, InviteSms.class);
				intent1.putExtra("pk_group", groupKey);
				intent1.putExtra("fd_group_password", groupPassword);				
				startActivity(intent1);
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
}