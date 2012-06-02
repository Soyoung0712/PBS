package com.pbs.client.activity.newgroup;

import java.util.List;

import com.android.R;
import com.android.R.id;
import com.android.R.layout;
import com.pbs.client.model.TbGroup;
import com.pbs.client.model.TbMember;
import com.pbs.client.util.UserGson;

import android.app.Activity;
import android.app.LauncherActivity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class InviteSms extends ListActivity {

	private List<TbMember> tbMemberList = null;
	private UserGson userGson = new UserGson();
	private NewArrayAdapter newArrayAdapter = null;
	private String myPhoneNum = "01077778888";
	// ��μ��� Flag (�ʱ� ������ ��μ����� ������ ����)
	boolean allClickStatuFlag = false;

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
	public void SendMessage() {
		String phone = "";
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		// ���� ���� �Է�
		String smsBody = "Phone Book Share\n�׷� �ʴ� �˸�\n==\nKEY:1234\nPASSWORD:123\n==\n�ٿ�ε�:http://~~";
		sendIntent.putExtra("sms_body", smsBody);
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