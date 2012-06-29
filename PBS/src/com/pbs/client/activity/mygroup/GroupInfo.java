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

	// �׷�Ű
	private TextView tvGroupKeyVal;
	// �׷��н�����
	private TextView tvGroupPasswordVal;
	// �׷��
	private TextView tvGroupNameVal;
	// �׷� ��������
	private TextView tvGroupNoticeVal;

	// �׷켳��
	private Button bGroupUpdate;
	// �ʴ��ϱ�
	private Button bInvite;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Ÿ��Ʋ�� ���ֱ�
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.my_group_info);

		// �� ��ȭ��ȣ ��������
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);

		Intent intent = getIntent();
		final long pk_group = intent.getExtras().getLong("pk_group");

		// �׷� ���� ��������
		TbGroup tbGroup = userGson.getGroupInfo(pk_group, myPhoneNum);

		// �׷��
		tvGroupNameVal = (TextView) findViewById(R.id.tvGroupNameVal);
		tvGroupNameVal.setText(">> " + tbGroup.getFd_group_name());
		// �׷�Ű
		tvGroupKeyVal = (TextView) findViewById(R.id.tvGroupKeyVal);
		tvGroupKeyVal.setText(">> " + tbGroup.getPk_group() + "");
		// �׷��н�����
		tvGroupPasswordVal = (TextView) findViewById(R.id.tvGroupPasswordVal);
		tvGroupPasswordVal.setText(">> " + tbGroup.getFd_group_password() + "");		
		// �׷� ��������
		tvGroupNoticeVal = (TextView) findViewById(R.id.tvGroupNoticeVal);
		tvGroupNoticeVal.setText(">> " + tbGroup.getFd_group_notice());

		// �ʴ��ϱ� ��ư
		bInvite = (Button) findViewById(R.id.bInvite);
		// �����Ϸ� ��ư
		bGroupUpdate = (Button) findViewById(R.id.bGroupUpdate);
		
		// "�ʴ��ϱ�" ��ư Ŭ��
		bInvite.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {

				Intent intent = new Intent(GroupInfo.this, InviteSms.class);
				// intent.putExtra("groupMemberList", groupMemberList);
				intent.putExtra("pk_group", String.valueOf(pk_group));
				startActivity(intent);

			}
		});	
		
		// "�׷켳��" ��ư Ŭ��
		bGroupUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {

				Intent intent = new Intent(GroupInfo.this, GroupModify.class);
				intent.putExtra("pk_group", pk_group);
				startActivityForResult(intent, ActivityMap.GROUP_MODIFY);
			}
		});	
		
		// �ڷΰ��� ��ư
		Button bBack = (Button) findViewById(R.id.bBack);
		bBack.setOnClickListener(new View.OnClickListener()	{
			public void onClick(View arg0)	{
				finish();				
			}
		});	
		
		// bold ó��
		// ����
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);		
		tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// �����Ϸ�				
		bGroupUpdate.setPaintFlags(bGroupUpdate.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// �ʴ��ϱ�				
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
			// "�׷켳��" ����		
			case ActivityMap.GROUP_MODIFY:
				// "�׷켳��"���� "����", "�����Ϸ�" ��ư Ŭ��������
				if (resultCode == RESULT_OK) {	
					// ȣ���� ��Ƽ��Ƽ
					Intent groupInfoIntent = getIntent();					
					setResult(RESULT_OK, groupInfoIntent);
					finish();
					break;
				}
		}
		
	}
}
