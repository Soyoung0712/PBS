package com.pbs.client.activity.mygroup;

import java.util.List;


import android.app.Activity;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
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

import com.pbs.client.R;
import com.pbs.client.activity.edit.InviteSms;
import com.pbs.client.model.TbMember;
import com.pbs.client.util.DeviceManager;
import com.pbs.client.util.UserGson;

public class SendSms extends ListActivity {

	private List<TbMember> tbMemberList = null;
	private UserGson userGson = new UserGson();
	private NewArrayAdapter newArrayAdapter = null;
	private String myPhoneNum = null;
	// ��μ��� Flag (�ʱ� ������ ��μ����� ������ ����)
	boolean allClickStatuFlag = false;

	final static String ACTION_SENT = "ACTION_MESSAGE_SENT";
	final static String ACTION_DELIVERY = "ACTION_MESSAGE_DELIVERY";

	// PendingIntent sentIntent;
	// PendingIntent deliveryIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Ÿ��Ʋ�� ���ֱ�
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.my_send_sms);

		// �� ��ȭ��ȣ ��������
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);

		// ������ �׷��� �ɹ� ����Ʈ ��������
		Intent intent = getIntent();
		long pk_group = intent.getExtras().getLong("pk_group");
		tbMemberList = userGson.getMemeberList(pk_group, myPhoneNum);

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

		// sentIntent = PendingIntent.getBroadcast(SendSms.this, 0, new
		// Intent(ACTION_SENT), 0);
		// deliveryIntent = PendingIntent.getBroadcast(SendSms.this, 0, new
		// Intent(ACTION_DELIVERY), 0);

		// ���� ������ ��ư�� ��������
		mSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				SendMessage();
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
	public void SendMessage() {
		String phone = "";
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		// ���� ���� ��� �߸���
		for (int i = 0; i < tbMemberList.size(); i++) {
			if (tbMemberList.get(i).isChecked()) {
				phone += ";" + tbMemberList.get(i).getFd_member_phone();
			}
		}
		// ���� �޴� ����� ��ȣ �Է�
		if (phone.length() > 0) {
			phone = phone.substring(1);
			sendIntent.putExtra("address", phone);
		} else {
			sendIntent.putExtra("address", phone);
		}
		sendIntent.setType("vnd.android-dir/mms-sms");

		startActivity(sendIntent);
	}

	// public void onResume()
	// {
	// super.onResume();
	//
	// registerReceiver(mSentBr, new IntentFilter(ACTION_SENT));
	// }
	//
	// // �޼��� ���ſ� ���� ���� Ȯ��
	// BroadcastReceiver mSentBr = new BroadcastReceiver()
	// {
	// @Override
	// public void onReceive(Context context, Intent intent)
	// {
	// // Activity.RESULT_OK ���� ����
	// if (getResultCode() == Activity.RESULT_OK)
	// {
	// Toast.makeText(SendSms.this, "SMS ���� �Ϸ�", Toast.LENGTH_SHORT).show();
	// }
	// else
	// {
	// Toast.makeText(SendSms.this, "SMS ���� ����", Toast.LENGTH_SHORT).show();
	// }
	// }
	// };

	class NewArrayAdapter extends ArrayAdapter {
		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.my_send_sms_row, tbMemberList);

			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// ��� ����
			TbMember tbMember = tbMemberList.get(position);

			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.my_send_sms_row, null);

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