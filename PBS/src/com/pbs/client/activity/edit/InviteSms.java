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

	// 모두선택 Flag (초기 설정은 모두선택이 해지된 상태)
	boolean allClickStatuFlag = false;

	String phone = "";
	final String smsBody = "Phone Book Share\n그룹 초대 알림\n==\nKEY:1234\nPASSWORD:123\n==\n다운로드:http://~~";

	PendingIntent sentIntent;
	PendingIntent deliveryIntent;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 타이틀바 없애기
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.edit_invite_sms);

		// CreateGroupComplete에서 넘겨준 그룹키를 가져온다.
		Intent intent = getIntent();
		String tmpGroupKey = intent.getStringExtra("pk_group");				
		final long groupKey = Long.parseLong(tmpGroupKey);
		final String groupPassword = intent.getStringExtra("fd_group_password");

		// 내 전화번호 가져오기
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

		sentIntent = PendingIntent.getBroadcast(InviteSms.this, 0, new Intent(ACTION_SENT), 0);
		deliveryIntent = PendingIntent.getBroadcast(InviteSms.this, 0,new Intent(ACTION_DELIVERY), 0);

		Button mSave = (Button) findViewById(R.id.sendmessage);		

		// 문자 보내기 버튼을 눌렸을때
		mSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				new AlertDialog.Builder(InviteSms.this)
						.setTitle("SMS로 초대하기")
						.setMessage("SMS를 이용하여 그룹원을 초대하시겠습니까?")
						.setPositiveButton("확인",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0, int arg1) {		
										sendMessage(groupKey, groupPassword);											
									}
								})
						.setNegativeButton("취소",
								new DialogInterface.OnClickListener() {						public void onClick(DialogInterface arg0,
											int arg1) {
										Toast.makeText(InviteSms.this,"SMS 초대를 취소하였습니다.",
												Toast.LENGTH_SHORT).show();
									}
								}).show();

			}
		});

		// 뒤로가기 버튼
		Button bBack = (Button) findViewById(R.id.bBack);
		bBack.setOnClickListener(new View.OnClickListener()	{
			public void onClick(View arg0)	{				
				finish();				
			}
		});	
		
		// bold 처리
		// 제목
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);		
		tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// 문자보내기				
		mSave.setPaintFlags(mSave.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// 전체선택
		TextView tvAllchoice = (TextView) findViewById(R.id.tvAllchoice);		
		tvAllchoice.setPaintFlags(tvAllchoice.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		
	}

	// 문자 보내기 버튼 클릭 이벤트
	public void sendMessage(Long groupKey, String groupPassword) {
		
		WaitDlg dlg = new WaitDlg(InviteSms.this, "초대하기 발송", "SMS 문자 발송중입니다.");
		
		try {
			
			dlg.start();
			
			// 문자 받을 사람 추리기		
			for (int i = 0; i < tbMemberList.size(); i++) {
				if (tbMemberList.get(i).isChecked()) {				
					
					String phone = tbMemberList.get(i).getFd_member_phone();
					String body = "PBS초대\n그룹등록:http://pbsm.co.kr/i.do?a="+groupKey+"&b="+groupPassword+"&c="+phone;
					
					// phone = phone.replace(";",""); //문자열 변환
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

	// 메세지 수신에 대한 여부 확인
	BroadcastReceiver mSentBr = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Activity.RESULT_OK 수신 성공
			if (getResultCode() == Activity.RESULT_OK) {
				Toast.makeText(InviteSms.this, "SMS 전송 완료", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(InviteSms.this, "SMS 전송 실패", Toast.LENGTH_SHORT).show();
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
			// 멤버 정보
			TbMember tbMember = tbMemberList.get(position);

			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.edit_invite_sms_row, null);

			// / 이름
			TextView textView = (TextView) row.findViewById(R.id.name);
			textView.setText(tbMember.getFd_member_name());
			textView.setPaintFlags(textView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
			// 전화번호
			TextView textView2 = (TextView) row.findViewById(R.id.number);
			textView2.setText(tbMember.getFd_member_phone_view());
			textView2.setPaintFlags(textView2.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
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