package com.android;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbs.client.model.TbMember;
import com.pbs.client.util.IntentsUtil;
import com.pbs.client.util.UserGson;

public class GroupMemberList extends ListActivity {

	private String myPhoneNum = "01077778888";
	private List<TbMember> tbMemberList = null;	
	private UserGson userGson = new UserGson();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupmemberlist);

		// ������ �׷��� �ɹ� ����Ʈ ��������
		Intent intent = getIntent();
		final long pk_group = intent.getExtras().getLong("pk_group");
		tbMemberList = userGson.getMemeberList(pk_group, myPhoneNum);

		// ����Ʈ�信 ����Ʈ ����
		setListAdapter(new NewArrayAdapter(this));

		// "�������� �̵�" ��ư
		Button mPhoneMove = (Button) findViewById(R.id.button1);
		mPhoneMove.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(GroupMemberList.this, Download.class);
				intent.putExtra("pk_group", pk_group);
				startActivity(intent);
			}
		});
		
		// "���� ������" ��ư
		Button mSMSMove = (Button) findViewById(R.id.button2);
		mSMSMove.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(GroupMemberList.this, GroupCheckList.class);
				intent.putExtra("pk_group", pk_group);
				startActivity(intent);
			}
		});
		
		// "���" ��ư
		Button mCancel = (Button) findViewById(R.id.button3);
		mCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
			}
		});

	}

	
	class NewArrayAdapter extends ArrayAdapter {
		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.memberrow, tbMemberList);
			this.context = context;			
		}

		
		public View getView(int position, View convertView, ViewGroup parent) {
			
			final int pos = position;
			
			// ��� ����
			TbMember tbMember = tbMemberList.get(pos);

			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.memberrow, null);

			// �̸�
			TextView textView = (TextView) row.findViewById(R.id.name);
			textView.setText(tbMember.getFd_member_name());
			// ��ȭ��ȣ
			TextView textView2 = (TextView) row.findViewById(R.id.number);
			textView2.setText(tbMember.getFd_member_phone());
			// ��ȭ�ɱ� �̹���
			ImageView imageView = (ImageView) row.findViewById(R.id.phone);
			imageView.setImageResource(R.drawable.phone);			
			imageView.setOnClickListener(new View.OnClickListener() {
				// Ŭ���� ��ȭ�ɱ�
				public void onClick(View arg0) {
					TbMember tbMember = tbMemberList.get(pos);										
					IntentsUtil.phoneCall(context, tbMember.getFd_member_phone());
				}
			});
			
			return row;
			
		}
	}
}