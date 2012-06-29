package com.pbs.client.activity.mygroup;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.pbs.client.util.AddressUtil;
import com.pbs.client.util.DeviceManager;
import com.pbs.client.util.UserGson;

public class AddressDownload extends ListActivity {

	private String myPhoneNum = null;
	private List<TbMember> tbMemberList = null;
	private NewArrayAdapter newArrayAdapter = null;
	private UserGson userGson = new UserGson();
	// 모두선택 Flag (초기 설정은 모두선택이 해지된 상태)
	boolean allClickStatuFlag = false;	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 타이틀바 없애기
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.my_address_download);

		// 내 전화번호 가져오기
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);

		// 선택한 그룹의 맴버 리스트 가져오기
		Intent intent = getIntent();
		long pk_group = intent.getExtras().getLong("pk_group");
		String fd_group_name = intent.getExtras().getString("fd_group_name");
		tbMemberList = userGson.getMemeberList(pk_group, myPhoneNum);		

		// 그룹명
		TextView editgroupnameTextView = (TextView) findViewById(R.id.editgroupname);
		editgroupnameTextView.setText(fd_group_name);

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

		// "저장" 버튼
		Button mSave = (Button) findViewById(R.id.bSave);
		mSave.setPaintFlags(mSave.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		StroeButton(this, mSave);

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
		// 그룹명
		TextView tvGroupname = (TextView) findViewById(R.id.tvGroupname);		
		tvGroupname.setPaintFlags(tvGroupname.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// 전체선책
		TextView tvAllchoice = (TextView) findViewById(R.id.tvAllchoice);		
		tvAllchoice.setPaintFlags(tvAllchoice.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);		

	}

	// 저장 버튼 클릭했을 때
	private void StroeButton(final Activity activity, Button mSave) {
		
		mSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				int cntSave = 0;
				AddressUtil addressUtil = new AddressUtil();
				
				// 그룹명
				TextView editgroupnameTextView = (TextView) findViewById(R.id.editgroupname);
				String groupName = editgroupnameTextView.getText().toString();
				if( groupName.length() < 1 ) {
					Toast.makeText(AddressDownload.this, "저장하실 그룹명을 입력해 주세요", Toast.LENGTH_LONG).show();
				}else {
					WaitDlg dlg = new WaitDlg(AddressDownload.this, "그룹원 저장중", "요청하신 작업을 처리중입니다");
					dlg.start();
					try{
						
						// 선택한 사용자를 주소록에 저장
						for (int i = 0; i < tbMemberList.size(); i++) {						
							 
							if (tbMemberList.get(i).isChecked()) {
								
								addressUtil.addContact(activity, groupName,
										tbMemberList.get(i).getFd_member_name(),
										tbMemberList.get(i).getFd_member_phone());
								cntSave++;
							}					 
						}

						if (cntSave > 0) {
							Toast.makeText(AddressDownload.this, "정상적으로 저장되었습니다..", Toast.LENGTH_SHORT).show();
							
							finish();
						} else {
							Toast.makeText(AddressDownload.this, "저장할 멤버가 없습니다.", Toast.LENGTH_SHORT).show();
							
						}					
					}finally{
						dlg.stopLocal();
					}
				}
				
			}
		});
	}

	/**
	 * ListView Adapter
	 * 
	 * @author Administrator
	 * 
	 */
	class NewArrayAdapter extends ArrayAdapter {

		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.my_address_download_row, tbMemberList);
			this.context = context;
		}

		/**
		 * 멤버 리스트의 갯수만큼 호출되는 함수
		 */
		public View getView(int position, View convertView, ViewGroup parent) {

			// 멤버 정보
			TbMember tbMember = tbMemberList.get(position);

			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.my_address_download_row, null);			

			// 이름
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
			
			/*
			final CheckBox tmpCheckBox = (CheckBox) row.findViewById(R.id.checkBox);
			row.setOnTouchListener(new View.OnTouchListener() {				
				public boolean onTouch(View v, MotionEvent event) {
					tmpCheckBox.setChecked(tbMemberList.get(pos).check());
					return false;
				}
			});
			*/			

			return row;
		}
	}
}