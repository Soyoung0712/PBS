package com.pbs.client.activity.newgroup;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.R;
import com.pbs.client.model.TbMember;
import com.pbs.client.util.UserGson;

public class InviteSms extends ListActivity {

	private List<TbMember> tbMemberList = null;
	private UserGson userGson = new UserGson();
	private NewArrayAdapter newArrayAdapter = null;
	private String myPhoneNum = "01077778888";

	final static String ACTION_SENT = "ACTION_MESSAGE_SENT";
	final static String ACTION_DELIVERY = "ACTION_MESSAGE_DELIVERY";
	
	// ��μ��� Flag (�ʱ� ������ ��μ����� ������ ����)
	boolean allClickStatuFlag = false;
	boolean smsSend = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_invite_sms);

		// ������ �׷��� �ɹ� ����Ʈ ��������
		tbMemberList = userGson.getMemeberList(5L, myPhoneNum);

		// ����Ʈ�信 ����Ʈ ����
		newArrayAdapter = new NewArrayAdapter(this);
		setListAdapter(newArrayAdapter);

		// "��μ���" ��ư ����
		CheckBox allchoice = (CheckBox) findViewById(R.id.checkBoxAll);
		allchoice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				// ��ü ����
				if (allClickStatuFlag) {
					for (int i = 0; i < tbMemberList.size(); i++) {
						tbMemberList.get(i).setChecked(false);
					}
					allClickStatuFlag = false;

					// ��ü ����
				} else {
					for (int i = 0; i < tbMemberList.size(); i++) {
						tbMemberList.get(i).setChecked(true);
					}
					allClickStatuFlag = true;
				}
				// List Refresh
				newArrayAdapter.notifyDataSetChanged();
			}
		});

		Button mSave = (Button) findViewById(R.id.sendmessage);
		Button mCancle = (Button) findViewById(R.id.cancle);

		// ���� ������ ��ư�� ��������
		mSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				SendMessage();
			}
		});

		// ��� ��ư�� ��������
		mCancle.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Toast.makeText(InviteSms.this, "���", Toast.LENGTH_SHORT)
						.show();
				finish();
			}
		});
	}

	// ���� ������ ��ư Ŭ�� �̺�Ʈ
	@SuppressWarnings("deprecation")
	public void SendMessage() {
		
		SmsManager sms = SmsManager.getDefault();
		String phone = "";
		
		
		// ���� ���� �Է�
		String smsBody = "Phone Book Share\n�׷� �ʴ� �˸�\n==\nKEY:1234\nPASSWORD:123\n==\n�ٿ�ε�:http://~~";
 
		// ���� ���� ��� �߸���
		for (int i = 0; i < tbMemberList.size(); i++) {
			if (tbMemberList.get(i).isChecked()) {
				phone += ";" + tbMemberList.get(i).getFd_member_phone();
			}
		}
		// ���� �޴� ����� ��ȣ �Է�
		if (phone.length() > 0) 
			phone = phone.substring(1);
	 

		PendingIntent sentIntent = PendingIntent.getBroadcast
				(InviteSms.this, 0, new Intent(ACTION_SENT), 0);
		PendingIntent deliveryIntent = PendingIntent.getBroadcast
				(InviteSms.this, 0, new Intent(ACTION_DELIVERY), 0);
		
		
		new AlertDialog.Builder(InviteSms.this)
		.setTitle("�׷� �ʴ� SMS �߼�")
		.setMessage("���õ� �׷������ SMS�� �̿��Ͽ� �ʴ� �մϴ�")
		.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface arg0, int arg1)
			{
				smsSend = true;
			}
		})
		.setNegativeButton("���", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface arg0, int arg1)
			{
				
			}
		})
		.show();
		
		if(smsSend)
		{
			sms.sendTextMessage(phone, null, smsBody, sentIntent, deliveryIntent);
			smsSend = false;
			Toast.makeText(InviteSms.this, "�׷� �ʴ��ϱ� SMS �߼� �Ϸ�", Toast.LENGTH_SHORT).show();
			
		}
		
//		startActivity(sendIntent);
	}

	class NewArrayAdapter extends ArrayAdapter {
		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.new_invite_sms_row, tbMemberList);

			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// ��� ����
			TbMember tbMember = tbMemberList.get(position);

			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.new_invite_sms_row, null);

			// / �̸�
			TextView textView = (TextView) row.findViewById(R.id.name);
			textView.setText(tbMember.getFd_member_name());
			// ��ȭ��ȣ
			TextView textView2 = (TextView) row.findViewById(R.id.number);
			textView2.setText(tbMember.getFd_member_phone());
			// üũ�ڽ� ����
			final int pos = position;
			CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkBox);
			checkBox.setChecked(tbMember.isChecked());
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				// Ŭ���Ҷ� ���� ���� ����
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					tbMemberList.get(pos).setChecked(isChecked);
				}
			});

			return row;
		}
	}
}