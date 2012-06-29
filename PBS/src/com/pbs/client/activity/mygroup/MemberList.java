package com.pbs.client.activity.mygroup;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.R;
import com.pbs.client.activity.main.WaitDlg;
import com.pbs.client.model.ActivityMap;
import com.pbs.client.model.TbAccessUser;
import com.pbs.client.model.TbMember;
import com.pbs.client.util.DeviceManager;
import com.pbs.client.util.IntentsUtil;
import com.pbs.client.util.UserGson;

public class MemberList extends ListActivity {

	private String myPhoneNum = null;
	private List<TbMember> tbMemberList = new ArrayList<TbMember>();	
	private UserGson userGson = new UserGson();	
	private NewArrayAdapter newArrayAdapter = null;
	
	private long pk_group = 0L;
	private String fd_admin_yn = null;
	private String fd_group_name = null;
	
	private Button mPhoneMove;
	private Button mSMSMove;

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
		pk_group = intent.getExtras().getLong("pk_group");
		fd_admin_yn = intent.getExtras().getString("fd_admin_yn");
		fd_group_name = intent.getExtras().getString("fd_group_name");
		
		// 리스트뷰에 리스트 적용
		newArrayAdapter = new NewArrayAdapter(this);
		setListAdapter(newArrayAdapter);
				
		Button bInfo = (Button) findViewById(R.id.bInfo);
		Button bDelete = (Button) findViewById(R.id.bDelete);
		
		// 관리자 일때 
		if( "Y".equals(fd_admin_yn) ) {
			
			// "그룹정보" 버튼
			bInfo.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					Intent intent = new Intent(MemberList.this, GroupInfo.class);
					intent.putExtra("pk_group", pk_group);					
					startActivityForResult(intent, ActivityMap.GROUP_INFO);
				}
			});
			bInfo.setVisibility(View.VISIBLE);
			
		// 그룹원 일때
		}else {		
			
			// "삭제" 버튼			
			bDelete.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// 그룹 감추기
					userGson.hiddenGroup(pk_group, myPhoneNum);
					finish();
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
		mPhoneMove = (Button) findViewById(R.id.bAddressSave);
		mPhoneMove.setPaintFlags(mPhoneMove.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// 문자보내기
		mSMSMove = (Button) findViewById(R.id.bSmsSend);
		mSMSMove.setPaintFlags(mSMSMove.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// 그룹정보
		bInfo.setPaintFlags(bInfo.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// 삭제
		bDelete.setPaintFlags(bDelete.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		
		new Thread(new Runnable() {
			
			WaitDlg dlg = new WaitDlg(MemberList.this, "서버 요청", "그룹원 리스트를 불러오고 있습니다");
			
			public void run() {

				// 시간 많이 걸리는 처리
				try{
					dlg.start();
	          		tbMemberList.clear();          		
	          		tbMemberList.addAll(userGson.getMemeberList(pk_group, myPhoneNum));
	          		
	          		// 관리자 가져오기
	          		List<TbAccessUser> tbAccessUserList = userGson.getAdminList(pk_group, myPhoneNum);
	          		for (int i = tbAccessUserList.size()-1; 0 <= i ; i--) {			
	          			TbMember tbMember = new TbMember();
	          			tbMember.setFd_member_name(tbAccessUserList.get(i).getFd_member_name());
	          			tbMember.setFd_member_phone(tbAccessUserList.get(i).getFd_access_phone());
	          			tbMember.setAdmin(true);
	          			tbMemberList.add(0, tbMember);
	          		}
				}finally{
					dlg.stopLocal();
				}				
          		
				runOnUiThread(new Runnable() {
					public void run() {	
						
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
						
		            	newArrayAdapter.notifyDataSetChanged();
		            }
		        });
				
			}
		}).start();		

	}
	
	@Override
	public void onResume() {
	
		super.onResume();	
		
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
			
			// 관리자는 황금사과 표시
			if( tbMember.isAdmin() ) {				
				Drawable apple_gold = (Drawable) getResources().getDrawable(R.drawable.apple_gold);
				ImageView ivAdminCheck = (ImageView) row.findViewById(R.id.ivAdminCheck);
				ivAdminCheck.setImageDrawable(apple_gold);
			// 그룹원은 빨간사과 표시
			}else {
				Drawable apple_red = (Drawable) getResources().getDrawable(R.drawable.apple_red);
				ImageView ivAdminCheck = (ImageView) row.findViewById(R.id.ivAdminCheck);
				ivAdminCheck.setImageDrawable(apple_red);
			}

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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		switch (requestCode) {
			// "그룹정보" 응답
			case ActivityMap.GROUP_INFO:
				// "그룹설정"에서 "삭제", "설정완료" 버튼 클릭했을때
				// "그룹정보"에서 종료하고 넘어옴
				if (resultCode == RESULT_OK) {
					finish();
					break;
				}
		}
	}
	
}