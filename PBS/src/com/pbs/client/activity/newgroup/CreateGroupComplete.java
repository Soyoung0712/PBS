package com.pbs.client.activity.newgroup;

import com.android.R;
import com.android.R.id;
import com.android.R.layout;
import com.pbs.client.activity.main.PbsStart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CreateGroupComplete extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_create_group_complete);		
		
		Intent intent = getIntent();		
		String groupName 	 = intent.getExtras().get("groupName").toString();
		String groupKey 	 = intent.getExtras().get("groupKey").toString();
		String groupPassword = intent.getExtras().get("groupPassword").toString();		
		 
		// 생성 그룹명
		TextView tvGroupName = (TextView)findViewById(R.id.tvGroupName);		
		tvGroupName.setText(groupName);
		// 생성 그룹키
		TextView tvGroupKey = (TextView)findViewById(R.id.tvGroupKey);		
		tvGroupKey.setText(groupKey);
		// 생성 그룹 패스워드
		TextView tvGroupPassword = (TextView)findViewById(R.id.tvGroupPassword);		
		tvGroupPassword.setText(groupPassword);
		
		
		Button mNewGroupComplete = (Button)findViewById(R.id.button1);
		Button mMemberInvite = (Button)findViewById(R.id.button2);
		
		// 그룹생성후 완료버튼 누를시 메인페이지로
		mNewGroupComplete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(CreateGroupComplete.this, PbsStart.class);
				startActivity(intent);
			}
		});

		// 그룹생성후 초대하기 버튼 누를떄
		mMemberInvite.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent1 = new Intent(CreateGroupComplete.this, InviteSms.class);
				startActivity(intent1);
			}
		});

	}
}