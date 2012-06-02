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
	// 모두선택 Flag (초기 설정은 모두선택이 해지된 상태)
	boolean allClickStatuFlag = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_invite_sms);

		// 선택한 그룹의 맴버 리스트 가져오기
		tbMemberList = userGson.getMemeberList(5L, myPhoneNum);

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
	public void SendMessage() {
		String phone = "";
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		// 문자 내용 입력
		String smsBody = "Phone Book Share\n그룹 초대 알림\n==\nKEY:1234\nPASSWORD:123\n==\n다운로드:http://~~";
		sendIntent.putExtra("sms_body", smsBody);
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