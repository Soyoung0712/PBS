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

		// 선택한 그룹의 맴버 리스트 가져오기
		Intent intent = getIntent();
		long pk_group = intent.getExtras().getLong("pk_group");
		tbMemberList = userGson.getMemeberList(pk_group, myPhoneNum);

		// 리스트뷰에 리스트 적용
		setListAdapter(new NewArrayAdapter(this));

		// "내폰으로 이동" 버튼
		Button mPhoneMove = (Button) findViewById(R.id.button1);
		mPhoneMove.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {

				Intent intent = new Intent(GroupMemberList.this, Download.class);
				startActivity(intent);
			}
		});
		
		// "문자 보내기" 버튼
		Button mSMSMove = (Button) findViewById(R.id.button2);
		mSMSMove.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent1 = new Intent(GroupMemberList.this,
						InviteMember.class);
				startActivity(intent1);
			}
		});
		
		// "취소" 버튼
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
			
			// 멤버 정보
			TbMember tbMember = tbMemberList.get(pos);

			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.memberrow, null);

			// 이름
			TextView textView = (TextView) row.findViewById(R.id.name);
			textView.setText(tbMember.getFd_member_name());
			// 전화번호
			TextView textView2 = (TextView) row.findViewById(R.id.number);
			textView2.setText(tbMember.getFd_member_phone());
			// 전화걸기 이미지
			ImageView imageView = (ImageView) row.findViewById(R.id.phone);
			imageView.setImageResource(R.drawable.phone);			
			imageView.setOnClickListener(new View.OnClickListener() {
				// 클릭시 전화걸기
				public void onClick(View arg0) {
					TbMember tbMember = tbMemberList.get(pos);										
					IntentsUtil.phoneCall(context, tbMember.getFd_member_phone());
				}
			});
			
			return row;
			
		}
	}
}