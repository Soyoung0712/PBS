package com.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GroupActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_create_ok);

	 
		TextView mGroupname = (TextView)findViewById(R.id.textView1);
		Button mNewGroupComplete = (Button)findViewById(R.id.button1);
		Button mMemberInvite = (Button)findViewById(R.id.button2);
		
		
		// �׷������ �Ϸ��ư ������ ������������
		mNewGroupComplete.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent intent = new Intent(GroupActivity.this,PBSActivity.class);
				startActivity(intent);
			}
		});
		
		//�׷������ �ʴ��ϱ� ��ư ������
		mMemberInvite.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent intent1 = new Intent(GroupActivity.this,InviteMember.class);
				startActivity(intent1);
			}
		});
		
		// ���޹��� �׷�� �ؽ�Ʈ �ѷ��ֱ�
		Intent intent = getIntent();
		
		String GroupName = intent.getExtras().get("groupname").toString();
		mGroupname.setText(GroupName);
	}	
}