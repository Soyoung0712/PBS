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
		 
		// ���� �׷��
		TextView tvGroupName = (TextView)findViewById(R.id.tvGroupName);		
		tvGroupName.setText(groupName);
		// ���� �׷�Ű
		TextView tvGroupKey = (TextView)findViewById(R.id.tvGroupKey);		
		tvGroupKey.setText(groupKey);
		// ���� �׷� �н�����
		TextView tvGroupPassword = (TextView)findViewById(R.id.tvGroupPassword);		
		tvGroupPassword.setText(groupPassword);
		
		
		Button mNewGroupComplete = (Button)findViewById(R.id.button1);
		Button mMemberInvite = (Button)findViewById(R.id.button2);
		
		// �׷������ �Ϸ��ư ������ ������������
		mNewGroupComplete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(CreateGroupComplete.this, PbsStart.class);
				startActivity(intent);
			}
		});

		// �׷������ �ʴ��ϱ� ��ư ������
		mMemberInvite.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent1 = new Intent(CreateGroupComplete.this, InviteSms.class);
				startActivity(intent1);
			}
		});

	}
}