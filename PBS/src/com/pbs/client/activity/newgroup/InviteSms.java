package com.pbs.client.activity.newgroup;

import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.bluetooth.BluetoothClass.Device;
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
import com.pbs.client.util.DeviceManager;
import com.pbs.client.util.UserGson;

public class InviteSms extends ListActivity {

	private List<TbMember> tbMemberList = null;
	private UserGson userGson = new UserGson();
	private NewArrayAdapter newArrayAdapter = null;
	private String myPhoneNum = null;

	final static String ACTION_SENT = "ACTION_MESSAGE_SENT";
	final static String ACTION_DELIVERY = "ACTION_MESSAGE_DELIVERY";
	
	// 모두선택 Flag (초기 설정은 모두선택이 해지된 상태)
	boolean allClickStatuFlag = false;
	boolean smsSend;
	String phone = "";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_invite_sms);
		
		// CreateGroupComplete에서 넘겨준 그룹키를 가져온다.
		Intent intent = getIntent();
		String tmpGroupKey = intent.getStringExtra("pk_group");
		long groupKey = Long.parseLong(tmpGroupKey);	

		//내 전화번호 가져오기		
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);
		
		// 선택한 그룹의 맴버 리스트 가져오기
		tbMemberList = userGson.getMemeberList(groupKey, myPhoneNum);

		// 리스트뷰에 리스트 적용
		newArrayAdapter = new NewArrayAdapter(this);
		setListAdapter(newArrayAdapter);

		// "모두선택" 버튼 설정
		CheckBox allchoice = (CheckBox) findViewById(R.id.checkBoxAll);
		allchoice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				// 전체 해제
				if (allClickStatuFlag) {
					for (int i = 0; i < tbMemberList.size(); i++) {
						tbMemberList.get(i).setChecked(false);
					}
					allClickStatuFlag = false;

					// 전체 선택
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

		// 문자 보내기 버튼을 눌렸을때
		mSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				SendMessage();
			}
		});

		// 취소 버튼을 눌렸을때
		mCancle.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Toast.makeText(InviteSms.this, "취소", Toast.LENGTH_SHORT)
						.show();
				finish();
			}
		});
	}

	// 문자 보내기 버튼 클릭 이벤트
	@SuppressWarnings("deprecation")
	public void SendMessage() {
		
		final SmsManager sms = SmsManager.getDefault();
	
		 
		 
		// 문자 내용 입력
		final String smsBody = "Phone Book Share\n그룹 초대 알림\n==\nKEY:1234\nPASSWORD:123\n==\n다운로드:http://~~";
 
		// 문자 받을 사람 추리기
		for (int i = 0; i < tbMemberList.size(); i++) {
			if (tbMemberList.get(i).isChecked()) {
				phone += ";" + tbMemberList.get(i).getFd_member_phone();
			}
		}
		// 문자 받는 사람들 번호 입력
		if (phone.length() > 0) 
			phone = phone.substring(1);
	 

		final PendingIntent sentIntent = PendingIntent.getBroadcast
				(InviteSms.this, 0, new Intent(ACTION_SENT), 0);
		final PendingIntent deliveryIntent = PendingIntent.getBroadcast
				(InviteSms.this, 0, new Intent(ACTION_DELIVERY), 0);
		
		
		new AlertDialog.Builder(InviteSms.this)
		.setTitle("그룹 초대 SMS 발송")
		.setMessage("선택된 그룹원에게 SMS를 이용하여 초대 합니다")
		.setPositiveButton("확인", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface arg0, int arg1)
			{
				 
				sms.sendTextMessage(phone, null, smsBody, sentIntent, deliveryIntent);			
				Toast.makeText(InviteSms.this, "그룹 초대하기 SMS 발송 완료", Toast.LENGTH_SHORT).show();
	 
			}
		})
		.setNegativeButton("취소", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface arg0, int arg1)
			{
				Toast.makeText(InviteSms.this, "SMS 전송 취소", Toast.LENGTH_SHORT).show();
			}
		})
		.show();
		
		 
		 
			 
		
		
 
	}
	
 
	

	class NewArrayAdapter extends ArrayAdapter {
		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.new_invite_sms_row, tbMemberList);

			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// 멤버 정보
			TbMember tbMember = tbMemberList.get(position);

			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.new_invite_sms_row, null);

			// / 이름
			TextView textView = (TextView) row.findViewById(R.id.name);
			textView.setText(tbMember.getFd_member_name());
			// 전화번호
			TextView textView2 = (TextView) row.findViewById(R.id.number);
			textView2.setText(tbMember.getFd_member_phone());
			// 체크박스 상태
			final int pos = position;
			CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkBox);
			checkBox.setChecked(tbMember.isChecked());
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				// 클릭할때 마다 상태 저장
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					tbMemberList.get(pos).setChecked(isChecked);
				}
			});

			return row;
		}
	}
}