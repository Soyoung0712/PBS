package com.pbs.client.activity.mygroup;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.R;
import com.pbs.client.model.TbAccessUser;
import com.pbs.client.model.TbMember;
import com.pbs.client.util.DeviceManager;
import com.pbs.client.util.IntentsUtil;
import com.pbs.client.util.UserGson;

public class MemberList extends ListActivity {

	private String myPhoneNum = null;
	private List<TbMember> tbMemberList = null;	
	private UserGson userGson = new UserGson();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		// Ÿ��Ʋ�� ���ֱ�
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.my_member_list);

		// �� ��ȭ��ȣ ��������
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);
		
		// ������ �׷��� �ɹ� ����Ʈ ��������
		Intent intent = getIntent();
		final long pk_group = intent.getExtras().getLong("pk_group");
		final String fd_admin_yn = intent.getExtras().getString("fd_admin_yn");
		final String fd_group_name = intent.getExtras().getString("fd_group_name");		
		tbMemberList = userGson.getMemeberList(pk_group, myPhoneNum);
		
		// ������ ��������
		List<TbAccessUser> tbAccessUserList = userGson.getAdminList(pk_group, myPhoneNum);
		for (int i = tbAccessUserList.size()-1; 0 <= i ; i--) {			
			TbMember tbMember = new TbMember();
			tbMember.setFd_member_name(tbAccessUserList.get(i).getFd_member_name());
			tbMember.setFd_member_phone(tbAccessUserList.get(i).getFd_access_phone());
			tbMember.setAdmin(true);
			tbMemberList.add(0, tbMember);
		}

		// ����Ʈ�信 ����Ʈ ����
		setListAdapter(new NewArrayAdapter(this));
		
		// ���ں�����/�׷� ����
		Button mSMSMove = (Button) findViewById(R.id.bSmsSend);
		Button mPhoneMove = (Button) findViewById(R.id.bAddressSave);
		
		if( tbMemberList.size() > 0 ) {
			
			// "���� ������" ��ư
			mSMSMove.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					Intent intent = new Intent(MemberList.this, SendSms.class);
					intent.putExtra("pk_group", pk_group);
					startActivity(intent);
				}
			});
			
			// "�׷�����" ��ư			
			mPhoneMove.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					Intent intent = new Intent(MemberList.this, AddressDownload.class);
					intent.putExtra("pk_group", pk_group);
					intent.putExtra("fd_group_name", fd_group_name);
					startActivity(intent);
				}
			});
			
		}else {
			mSMSMove.setVisibility(View.GONE);
			mPhoneMove.setVisibility(View.GONE);
		}
		
		Button bInfo = (Button) findViewById(R.id.bInfo);
		Button bDelete = (Button) findViewById(R.id.bDelete);
		
		// ������ �϶� 
		if( "Y".equals(fd_admin_yn) ) {
			
			// "�׷�����" ��ư
			bInfo.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					Intent intent = new Intent(MemberList.this, GroupInfo.class);
					intent.putExtra("pk_group", pk_group);
					startActivity(intent);
				}
			});
			bInfo.setVisibility(View.VISIBLE);
			
		// �׷�� �϶�
		}else {
			
			// "����" ��ư			
			bDelete.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					Intent intent = new Intent(MemberList.this, GroupModify.class);
					intent.putExtra("pk_group", pk_group);
					startActivity(intent);
				}
			});			
			bDelete.setVisibility(View.VISIBLE);
		}
		
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
		// �� ������ �̵�
		mPhoneMove.setPaintFlags(mPhoneMove.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// ���ں�����
		mSMSMove.setPaintFlags(mSMSMove.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// �׷�����
		bInfo.setPaintFlags(bInfo.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// ����
		bDelete.setPaintFlags(bDelete.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
	}

	
	class NewArrayAdapter extends ArrayAdapter {
		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.my_member_list_row, tbMemberList);
			this.context = context;			
		}

		
		public View getView(int position, View convertView, ViewGroup parent) {
			
			final int pos = position;
			
			// ��� ����
			TbMember tbMember = tbMemberList.get(pos);

			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.my_member_list_row, null);

			// �̸�
			TextView textView = (TextView) row.findViewById(R.id.name);
			textView.setText(tbMember.getFd_member_name());
			textView.setPaintFlags(textView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
			// ��ȭ��ȣ
			TextView textView2 = (TextView) row.findViewById(R.id.number);
			textView2.setText(tbMember.getFd_member_phone_view());
			textView2.setPaintFlags(textView2.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
			// ��ȭ�ɱ� �̹���
			Button imageView = (Button) row.findViewById(R.id.phone);	
			imageView.setOnClickListener(new View.OnClickListener() {
				// Ŭ���� ��ȭ�ɱ�
				public void onClick(View arg0) {
					TbMember tbMember = tbMemberList.get(pos);										
					IntentsUtil.phoneCall(context, tbMember.getFd_member_phone_view());
				}
			});
			
			return row;
			
		}
	}
}