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
	// 모두선택 Flag (초기 설정은 모두선택이 해지된 상태)
	boolean allClickStatuFlag = false;

	final static String ACTION_SENT = "ACTION_MESSAGE_SENT";
	final static String ACTION_DELIVERY = "ACTION_MESSAGE_DELIVERY";

	// PendingIntent sentIntent;
	// PendingIntent deliveryIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 타이틀바 없애기
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.my_send_sms);

		// 내 전화번호 가져오기
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);

		// 선택한 그룹의 맴버 리스트 가져오기
		Intent intent = getIntent();
		long pk_group = intent.getExtras().getLong("pk_group");
		tbMemberList = userGson.getMemeberList(pk_group, myPhoneNum);

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

		// sentIntent = PendingIntent.getBroadcast(SendSms.this, 0, new
		// Intent(ACTION_SENT), 0);
		// deliveryIntent = PendingIntent.getBroadcast(SendSms.this, 0, new
		// Intent(ACTION_DELIVERY), 0);

		// 문자 보내기 버튼을 눌렸을때
		mSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				SendMessage();
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
	public void SendMessage() {
		String phone = "";
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		// 문자 받을 사람 추리기
		for (int i = 0; i < tbMemberList.size(); i++) {
			if (tbMemberList.get(i).isChecked()) {
				phone += ";" + tbMemberList.get(i).getFd_member_phone();
			}
		}
		// 문자 받는 사람들 번호 입력
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
	// // 메세지 수신에 대한 여부 확인
	// BroadcastReceiver mSentBr = new BroadcastReceiver()
	// {
	// @Override
	// public void onReceive(Context context, Intent intent)
	// {
	// // Activity.RESULT_OK 수신 성공
	// if (getResultCode() == Activity.RESULT_OK)
	// {
	// Toast.makeText(SendSms.this, "SMS 전송 완료", Toast.LENGTH_SHORT).show();
	// }
	// else
	// {
	// Toast.makeText(SendSms.this, "SMS 전송 실패", Toast.LENGTH_SHORT).show();
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
			// 멤버 정보
			TbMember tbMember = tbMemberList.get(position);

			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.my_send_sms_row, null);

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