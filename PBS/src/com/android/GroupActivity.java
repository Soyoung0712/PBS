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
		
		
		// 그룹생성후 완료버튼 누를시 메인페이지로
		mNewGroupComplete.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent intent = new Intent(GroupActivity.this,PBSActivity.class);
				startActivity(intent);
			}
		});
		
		//그룹생성후 초대하기 버튼 누를떄
		mMemberInvite.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent intent1 = new Intent(GroupActivity.this,InviteMember.class);
				startActivity(intent1);
			}
		});
		
		// 전달받은 그룹명 텍스트 뿌려주기
		Intent intent = getIntent();
		
		String GroupName = intent.getExtras().get("groupname").toString();
		mGroupname.setText(GroupName);
	}	
}