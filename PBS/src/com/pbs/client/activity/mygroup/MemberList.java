package com.pbs.client.activity.mygroup;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.R;
import com.pbs.client.activity.main.WaitDlg;
import com.pbs.client.model.ActivityMap;
import com.pbs.client.model.TbAccessUser;
import com.pbs.client.model.TbMember;
import com.pbs.client.util.DeviceManager;
import com.pbs.client.util.IntentsUtil;
import com.pbs.client.util.UserGson;

public class MemberList extends ListActivity {

	private String myPhoneNum = null;
	private List<TbMember> tbMemberList = new ArrayList<TbMember>();	
	private UserGson userGson = new UserGson();	
	private NewArrayAdapter newArrayAdapter = null;
	
	private long pk_group = 0L;
	private String fd_admin_yn = null;
	private String fd_group_name = null;
	
	private Button mPhoneMove;
	private Button mSMSMove;

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
		pk_group = intent.getExtras().getLong("pk_group");
		fd_admin_yn = intent.getExtras().getString("fd_admin_yn");
		fd_group_name = intent.getExtras().getString("fd_group_name");
		
		// ����Ʈ�信 ����Ʈ ����
		newArrayAdapter = new NewArrayAdapter(this);
		setListAdapter(newArrayAdapter);
				
		Button bInfo = (Button) findViewById(R.id.bInfo);
		Button bDelete = (Button) findViewById(R.id.bDelete);
		
		// ������ �϶� 
		if( "Y".equals(fd_admin_yn) ) {
			
			// "�׷�����" ��ư
			bInfo.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					Intent intent = new Intent(MemberList.this, GroupInfo.class);
					intent.putExtra("pk_group", pk_group);					
					startActivityForResult(intent, ActivityMap.GROUP_INFO);
				}
			});
			bInfo.setVisibility(View.VISIBLE);
			
		// �׷�� �϶�
		}else {		
			
			// "����" ��ư			
			bDelete.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// �׷� ���߱�
					userGson.hiddenGroup(pk_group, myPhoneNum);
					finish();
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
		mPhoneMove = (Button) findViewById(R.id.bAddressSave);
		mPhoneMove.setPaintFlags(mPhoneMove.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// ���ں�����
		mSMSMove = (Button) findViewById(R.id.bSmsSend);
		mSMSMove.setPaintFlags(mSMSMove.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// �׷�����
		bInfo.setPaintFlags(bInfo.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// ����
		bDelete.setPaintFlags(bDelete.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		
		new Thread(new Runnable() {
			
			WaitDlg dlg = new WaitDlg(MemberList.this, "���� ��û", "�׷�� ����Ʈ�� �ҷ����� �ֽ��ϴ�");
			
			public void run() {

				// �ð� ���� �ɸ��� ó��
				try{
					dlg.start();
	          		tbMemberList.clear();          		
	          		tbMemberList.addAll(userGson.getMemeberList(pk_group, myPhoneNum));
	          		
	          		// ������ ��������
	          		List<TbAccessUser> tbAccessUserList = userGson.getAdminList(pk_group, myPhoneNum);
	          		for (int i = tbAccessUserList.size()-1; 0 <= i ; i--) {			
	          			TbMember tbMember = new TbMember();
	          			tbMember.setFd_member_name(tbAccessUserList.get(i).getFd_member_name());
	          			tbMember.setFd_member_phone(tbAccessUserList.get(i).getFd_access_phone());
	          			tbMember.setAdmin(true);
	          			tbMemberList.add(0, tbMember);
	          		}
				}finally{
					dlg.stopLocal();
				}				
          		
				runOnUiThread(new Runnable() {
					public void run() {	
						
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
						
		            	newArrayAdapter.notifyDataSetChanged();
		            }
		        });
				
			}
		}).start();		

	}
	
	@Override
	public void onResume() {
	
		super.onResume();	
		
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
			
			// �����ڴ� Ȳ�ݻ�� ǥ��
			if( tbMember.isAdmin() ) {				
				Drawable apple_gold = (Drawable) getResources().getDrawable(R.drawable.apple_gold);
				ImageView ivAdminCheck = (ImageView) row.findViewById(R.id.ivAdminCheck);
				ivAdminCheck.setImageDrawable(apple_gold);
			// �׷���� ������� ǥ��
			}else {
				Drawable apple_red = (Drawable) getResources().getDrawable(R.drawable.apple_red);
				ImageView ivAdminCheck = (ImageView) row.findViewById(R.id.ivAdminCheck);
				ivAdminCheck.setImageDrawable(apple_red);
			}

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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		switch (requestCode) {
			// "�׷�����" ����
			case ActivityMap.GROUP_INFO:
				// "�׷켳��"���� "����", "�����Ϸ�" ��ư Ŭ��������
				// "�׷�����"���� �����ϰ� �Ѿ��
				if (resultCode == RESULT_OK) {
					finish();
					break;
				}
		}
	}
	
}