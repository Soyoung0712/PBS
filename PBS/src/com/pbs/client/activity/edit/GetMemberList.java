package com.pbs.client.activity.edit;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.R;
import com.pbs.client.activity.newgroup.CreateGroup;
import com.pbs.client.model.AddressUser;

public class GetMemberList extends ListActivity {
	
	private NewArrayAdapter newArrayAdapter;
	private ArrayList<AddressUser> memberList;	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_get_member_list);

		// 선택한 그룹의 맴버 리스트 가져오기
		Intent intent = getIntent();		
		memberList = (ArrayList<AddressUser>)intent.getSerializableExtra("memberList");
		if( memberList == null ) {
			memberList = new ArrayList<AddressUser>();
		}
		 
		// 리스트뷰에 리스트 적용
		newArrayAdapter = new NewArrayAdapter(this);
		setListAdapter(newArrayAdapter);

		// 편집완료 버튼 클릭 했을때
		Button complete = (Button) findViewById(R.id.complete);
		complete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(GetMemberList.this, "편집 완료", Toast.LENGTH_SHORT).show();
				
				// 호출한 액티비티				
				Intent intent = getIntent();
				intent.putExtra("addressUserList", memberList);				
				setResult(RESULT_OK, intent);
				finish();
				
			}
		});
		
		// 번호 추가 버튼		
		Button bAddMember = (Button) findViewById(R.id.bAddMember);
		bAddMember.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				showMemberAdd();
			}
		});	
	}
	

	// 멤버 추가 버튼 클릭 이벤트	
	private void showMemberAdd() {
		
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout addgrouplayout = (LinearLayout) vi.inflate(R.layout.dialog_add_member, null);

		final EditText etName = (EditText) addgrouplayout.findViewById(R.id.etName);
		final EditText etPhone = (EditText) addgrouplayout.findViewById(R.id.etPhone);		
		
		// "전화번호 추가" 다이얼로그 생성
		Builder builder = new AlertDialog.Builder(this).setTitle("전화번호 추가").setView(addgrouplayout);
		
		// "추가" 버튼 리슨너 생성		
		DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {

			// 레이어 팝업의 "추가" 버큰 클릭시 이벤트
			public void onClick(DialogInterface dialog, int which) {					
				
				// 이름, 전화번호 추가				
				String name = etName.getText().toString();
				String phone = etPhone.getText().toString();								
				
				// 그룹키 추가 성공
				if( name.length() > 0 && phone.length() > 0 ) {					
					AddressUser addressUser = new AddressUser();
					addressUser.setName(name);
					addressUser.setDial(phone);
					
					// 이미 있는 번호인지 체크
					boolean bDuplication = false;
					for( int i=0; i < memberList.size(); i++ ) {						
						String tmpPhone = memberList.get(i).getDial();
						if ( tmpPhone.equals(phone) ) {
							bDuplication = true;
							break;
						}
						
					}
					
					if( bDuplication ) {
						Toast.makeText(GetMemberList.this, "이미 등록된 사용자 번호 입니다.",Toast.LENGTH_LONG).show();
					}else {
						memberList.add(0, addressUser);
						newArrayAdapter.notifyDataSetChanged();
					}					
					
				// 그룹키 추가 실패
				}else {					
					Toast.makeText(GetMemberList.this, "잘못된 정보 입니다.",Toast.LENGTH_LONG).show();
				}				
								
			}
			
		};
				
		// 다이얼로그 SHOW
		builder.setNeutralButton("추가", onClickListener).show();
		
	}

	
	class NewArrayAdapter extends ArrayAdapter {

		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.edit_get_member_list_row, memberList);
			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final int pos = position;			

			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.edit_get_member_list_row, null);
			
			// 멤버 정보
			AddressUser addressUser = memberList.get(pos);

			// 이름
			EditText etName = (EditText) row.findViewById(R.id.etName);
			etName.setText(addressUser.getName());
			// 전번호
			EditText etNumber = (EditText) row.findViewById(R.id.etNumber);
			etNumber.setText(addressUser.getDial());
			// 편집완료 버튼 클릭 했을때
			Button bDeleteOne = (Button) row.findViewById(R.id.bDeleteOne);
			bDeleteOne.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(GetMemberList.this, "삭제", Toast.LENGTH_SHORT).show();
					memberList.remove(pos);
					notifyDataSetChanged();
				}
			});

			return row;
		}
	}

}
