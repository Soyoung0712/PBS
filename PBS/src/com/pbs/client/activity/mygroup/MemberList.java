package com.pbs.client.activity.mygroup;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.R;
import com.pbs.client.model.TbAccessUser;
import com.pbs.client.model.TbMember;
import com.pbs.client.util.DeviceManager;
import com.pbs.client.util.IntentsUtil;
import com.pbs.client.util.UserGson;

public class MemberList extends ListActivity {

	private String myPhoneNum = null;
	private List<TbMember> tbMemberList = null;	
	private UserGson userGson = new UserGson();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		// 타이틀바 없애기
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.my_member_list);

		// 내 전화번호 가져오기
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);
		
		// 선택한 그룹의 맴버 리스트 가져오기
		Intent intent = getIntent();
		final long pk_group = intent.getExtras().getLong("pk_group");
		final String fd_admin_yn = intent.getExtras().getString("fd_admin_yn");
		final String fd_group_name = intent.getExtras().getString("fd_group_name");		
		tbMemberList = userGson.getMemeberList(pk_group, myPhoneNum);
		
		// 관리자 가져오기
		List<TbAccessUser> tbAccessUserList = userGson.getAdminList(pk_group, myPhoneNum);
		for (int i = tbAccessUserList.size()-1; 0 <= i ; i--) {			
			TbMember tbMember = new TbMember();
			tbMember.setFd_member_name(tbAccessUserList.get(i).getFd_member_name());
			tbMember.setFd_member_phone(tbAccessUserList.get(i).getFd_access_phone());
			tbMember.setAdmin(true);
			tbMemberList.add(0, tbMember);
		}

		// 리스트뷰에 리스트 적용
		setListAdapter(new NewArrayAdapter(this));
		
		// 문자보내기/그룹 저장
		Button mSMSMove = (Button) findViewById(R.id.bSmsSend);
		Button mPhoneMove = (Button) findViewById(R.id.bAddressSave);
		
		if( tbMemberList.size() > 0 ) {
			
			// "문자 보내기" 버튼
			mSMSMove.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					Intent intent = new Intent(MemberList.this, SendSms.class);
					intent.putExtra("pk_group", pk_group);
					startActivity(intent);
				}
			});
			
			// "그룹저장" 버튼			
			mPhoneMove.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					Intent intent = new Intent(MemberList.this, AddressDownload.class);
					intent.putExtra("pk_group", pk_group);
					intent.putExtra("fd_group_name", fd_group_name);
					startActivity(intent);
				}
			});
			
		}else {
			mSMSMove.setVisibility(View.GONE);
			mPhoneMove.setVisibility(View.GONE);
		}
		
		Button bInfo = (Button) findViewById(R.id.bInfo);
		Button bDelete = (Button) findViewById(R.id.bDelete);
		
		// 관리자 일때 
		if( "Y".equals(fd_admin_yn) ) {
			
			// "그룹정보" 버튼
			bInfo.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					Intent intent = new Intent(MemberList.this, GroupInfo.class);
					intent.putExtra("pk_group", pk_group);
					startActivity(intent);
				}
			});
			bInfo.setVisibility(View.VISIBLE);
			
		// 그룹원 일때
		}else {
			
			// "삭제" 버튼			
			bDelete.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					Intent intent = new Intent(MemberList.this, GroupModify.class);
					intent.putExtra("pk_group", pk_group);
					startActivity(intent);
				}
			});			
			bDelete.setVisibility(View.VISIBLE);
		}
		
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
		// 내 폰으로 이동
		mPhoneMove.setPaintFlags(mPhoneMove.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// 문자보내기
		mSMSMove.setPaintFlags(mSMSMove.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// 그룹정보
		bInfo.setPaintFlags(bInfo.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// 삭제
		bDelete.setPaintFlags(bDelete.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
	}

	
	class NewArrayAdapter extends ArrayAdapter {
		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.my_member_list_row, tbMemberList);
			this.context = context;			
		}

		
		public View getView(int position, View convertView, ViewGroup parent) {
			
			final int pos = position;
			
			// 멤버 정보
			TbMember tbMember = tbMemberList.get(pos);

			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.my_member_list_row, null);

			// 이름
			TextView textView = (TextView) row.findViewById(R.id.name);
			textView.setText(tbMember.getFd_member_name());
			textView.setPaintFlags(textView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
			// 전화번호
			TextView textView2 = (TextView) row.findViewById(R.id.number);
			textView2.setText(tbMember.getFd_member_phone_view());
			textView2.setPaintFlags(textView2.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
			// 전화걸기 이미지
			Button imageView = (Button) row.findViewById(R.id.phone);	
			imageView.setOnClickListener(new View.OnClickListener() {
				// 클릭시 전화걸기
				public void onClick(View arg0) {
					TbMember tbMember = tbMemberList.get(pos);										
					IntentsUtil.phoneCall(context, tbMember.getFd_member_phone_view());
				}
			});
			
			return row;
			
		}
	}
}