package com.pbs.client.activity.edit;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.R;
import com.pbs.client.activity.main.WaitDlg;
import com.pbs.client.model.TbMember;
import com.pbs.client.util.DeviceManager;
import com.pbs.client.util.UserGson;

public class InviteSms extends ListActivity {

	private List<TbMember> tbMemberList = null;
	private UserGson userGson = new UserGson();
	private NewArrayAdapter newArrayAdapter = null;
	private String myPhoneNum = null;

	final static String ACTION_SENT = "ACTION_MESSAGE_SENT";
	final static String ACTION_DELIVERY = "ACTION_MESSAGE_DELIVERY";

	// ��μ��� Flag (�ʱ� ������ ��μ����� ������ ����)
	boolean allClickStatuFlag = false;

	String phone = "";
	final String smsBody = "Phone Book Share\n�׷� �ʴ� �˸�\n==\nKEY:1234\nPASSWORD:123\n==\n�ٿ�ε�:http://~~";

	PendingIntent sentIntent;
	PendingIntent deliveryIntent;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Ÿ��Ʋ�� ���ֱ�
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.edit_invite_sms);

		// CreateGroupComplete���� �Ѱ��� �׷�Ű�� �����´�.
		Intent intent = getIntent();
		String tmpGroupKey = intent.getStringExtra("pk_group");				
		final long groupKey = Long.parseLong(tmpGroupKey);
		final String groupPassword = intent.getStringExtra("fd_group_password");

		// �� ��ȭ��ȣ ��������
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);

		// ������ �׷��� �ɹ� ����Ʈ ��������
		tbMemberList = userGson.getMemeberList(groupKey, myPhoneNum);

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

		sentIntent = PendingIntent.getBroadcast(InviteSms.this, 0, new Intent(ACTION_SENT), 0);
		deliveryIntent = PendingIntent.getBroadcast(InviteSms.this, 0,new Intent(ACTION_DELIVERY), 0);

		Button mSave = (Button) findViewById(R.id.sendmessage);		

		// ���� ������ ��ư�� ��������
		mSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				new AlertDialog.Builder(InviteSms.this)
						.setTitle("SMS�� �ʴ��ϱ�")
						.setMessage("SMS�� �̿��Ͽ� �׷���� �ʴ��Ͻðڽ��ϱ�?")
						.setPositiveButton("Ȯ��",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0, int arg1) {		
										sendMessage(groupKey, groupPassword);											
									}
								})
						.setNegativeButton("���",
								new DialogInterface.OnClickListener() {						public void onClick(DialogInterface arg0,
											int arg1) {
										Toast.makeText(InviteSms.this,"SMS �ʴ븦 ����Ͽ����ϴ�.",
												Toast.LENGTH_SHORT).show();
									}
								}).show();

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
		// ���ں�����				
		mSave.setPaintFlags(mSave.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// ��ü����
		TextView tvAllchoice = (TextView) findViewById(R.id.tvAllchoice);		
		tvAllchoice.setPaintFlags(tvAllchoice.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		
	}

	// ���� ������ ��ư Ŭ�� �̺�Ʈ
	public void sendMessage(Long groupKey, String groupPassword) {
		
		WaitDlg dlg = new WaitDlg(InviteSms.this, "�ʴ��ϱ� �߼�", "SMS ���� �߼����Դϴ�.");
		
		try {
			
			dlg.start();
			
			// ���� ���� ��� �߸���		
			for (int i = 0; i < tbMemberList.size(); i++) {
				if (tbMemberList.get(i).isChecked()) {				
					
					String phone = tbMemberList.get(i).getFd_member_phone();
					String body = "PBS�ʴ�\n�׷���:http://pbsm.co.kr/i.do?a="+groupKey+"&b="+groupPassword+"&c="+phone;
					
					// phone = phone.replace(";",""); //���ڿ� ��ȯ
					if (phone.length() > 0) {
						sendSMS(body, phone);
					}					
					
				}			
				
			}
			
		}finally{
			dlg.stopLocal();
		} 

	}/////

	 
	public void sendSMS(String smsBody, String phoneNumber) {
		final SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, smsBody, sentIntent, deliveryIntent);
	}
	
	 
	@Override
	public void onResume() {		
		super.onResume();
		registerReceiver(mSentBr, new IntentFilter(ACTION_SENT));		
	}
	
	@Override
	public void onStop() {
		unregisterReceiver(mSentBr);
		super.onStop();		
	}

	// �޼��� ���ſ� ���� ���� Ȯ��
	BroadcastReceiver mSentBr = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Activity.RESULT_OK ���� ����
			if (getResultCode() == Activity.RESULT_OK) {
				Toast.makeText(InviteSms.this, "SMS ���� �Ϸ�", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(InviteSms.this, "SMS ���� ����", Toast.LENGTH_SHORT).show();
			}
		}
	};

	class NewArrayAdapter extends ArrayAdapter {
		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.edit_invite_sms_row, tbMemberList);

			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// ��� ����
			TbMember tbMember = tbMemberList.get(position);

			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.edit_invite_sms_row, null);

			// / �̸�
			TextView textView = (TextView) row.findViewById(R.id.name);
			textView.setText(tbMember.getFd_member_name());
			textView.setPaintFlags(textView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
			// ��ȭ��ȣ
			TextView textView2 = (TextView) row.findViewById(R.id.number);
			textView2.setText(tbMember.getFd_member_phone_view());
			textView2.setPaintFlags(textView2.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
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