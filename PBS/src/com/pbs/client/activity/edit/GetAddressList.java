package com.pbs.client.activity.edit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
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
import com.pbs.client.activity.main.WaitDlg;
import com.pbs.client.model.AddressUser;
import com.pbs.client.util.AddressUserSort;

public class GetAddressList extends ListActivity {

	private NewArrayAdapter newArrayAdapter = null;
	// 모두선택 Flag (초기 설정은 모두선택이 해지된 상태)
	boolean allClickStatuFlag = false;	
	
	// 전화번호부 리스트
	private List<AddressUser> addressUserList;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		// 타이틀바 없애기
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.edit_get_address_list);
		
		addressUserList = new ArrayList<AddressUser>();
		
		// 이름 오름차순 정렬
		Collections.sort(addressUserList, new AddressUserSort());		

		// 리스트뷰에 리스트 저장
		newArrayAdapter = new NewArrayAdapter(this);
		setListAdapter(newArrayAdapter);

		// "모두선택" 버튼 설정
		CheckBox allchoice = (CheckBox) findViewById(R.id.checkBoxAll);
		allchoice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				// 전체 해제
				if (allClickStatuFlag) {
					for (int i = 0; i < addressUserList.size(); i++) {
						addressUserList.get(i).setChecked(false);
					}
					allClickStatuFlag = false;

					// 전체 선택
				} else {
					for (int i = 0; i < addressUserList.size(); i++) {
						addressUserList.get(i).setChecked(true);
					}
					allClickStatuFlag = true;
				}
				// List Refresh
				newArrayAdapter.notifyDataSetChanged();
			}
		});

		// "확인" 버튼
		Button mGroupRestul = (Button) findViewById(R.id.sendmessage);
		mGroupRestul.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Toast.makeText(GetAddressList.this, "가져오기 완료",
						Toast.LENGTH_SHORT).show();

				// 선택한 전화번호만 저장
				ArrayList<AddressUser> resultAddressUserList = new ArrayList<AddressUser>();
				for (int i = 0; i < addressUserList.size(); i++) {
					if (addressUserList.get(i).isChecked()) {
						resultAddressUserList.add(addressUserList.get(i));
					}
				}

				// 호출한 액티비티
				Intent intent = getIntent();
				intent.putExtra("addressUserList", resultAddressUserList);
				setResult(RESULT_OK, intent);
				finish();
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
		mGroupRestul.setPaintFlags(mGroupRestul.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);		
		// 전체선택
		TextView tvAllchoice = (TextView) findViewById(R.id.tvAllchoice);		
		tvAllchoice.setPaintFlags(tvAllchoice.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		
		// 전화번호 주소록 가져오기
		ContentResolver cr = getContentResolver();
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
		Cursor c = managedQuery(ContactsContract.Contacts.CONTENT_URI, null, null, null, sortOrder);
		startManagingCursor(c);

		// 전화번호 주소록 사용자 저장		
		while (c.moveToNext()) {

			// ID 저장
			String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
			// 이름 저장
			String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

			// 전화번호 저장			
			if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {				
				Cursor cp = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ", new String[] { id }, null);
				while (cp.moveToNext()) {
					String dial = cp.getString(cp.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));
					// 전화번호가 있는 경우만 전화번호부에 보여준다.
					if (dial != null && dial.length() > 0) {
						// 사용자 정보 저장
						AddressUser addressUser = new AddressUser();
						addressUser.setId(id);
						addressUser.setName(name);
						addressUser.setDial(dial);
						// 주소록 명단에 추가
						addressUserList.add(addressUser);
					}

				}
				cp.close();
			}
			
		}
		c.close();
		
		newArrayAdapter.notifyDataSetChanged();
		
		/*
		new Thread(new Runnable() {
			
			WaitDlg dlg = new WaitDlg(GetAddressList.this, "주소록 가져오기", "그룹원 리스트를 불러오고 있습니다");			
			public void run() {

				// 시간 많이 걸리는 처리
				try{
					dlg.start();
	        		
				}finally{
					dlg.stopLocal();
				}				
          		
				runOnUiThread(new Runnable() {
					public void run() {
		            	
		            }
		        });
				
			}
		}).start();
		*/

	}
 
	 // Back 버튼 이벤트 처리
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    	 Intent intent = getIntent();
			intent = getIntent();
			setResult(RESULT_OK, intent);
			finish();
			return true;
	     }

	     return super.onKeyDown(keyCode, event);
	 }
	 
	class NewArrayAdapter extends ArrayAdapter {

		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.edit_get_address_list_row, addressUserList);
			this.context = context;
		}
 
		public View getView(int position, View convertView, ViewGroup parent) {

			final int pos = position;
			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.edit_get_address_list_row, null);

			// 전화번호부
			AddressUser addressUser = addressUserList.get(pos);

			// 이름
			TextView nameTextView = (TextView) row.findViewById(R.id.name);
			nameTextView.setText(addressUser.getName());
			nameTextView.setPaintFlags(nameTextView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

			// 전화번호
			TextView dialTextView = (TextView) row.findViewById(R.id.dial);
			dialTextView.setText(addressUser.getDial_view());
			dialTextView.setPaintFlags(dialTextView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

			// 체크박스 상태
			CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkBox);
			checkBox.setChecked(addressUser.isChecked());
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				// 클릭할때 마다 상태 저장
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					addressUserList.get(pos).setChecked(isChecked);
				}

			});

			return row;
		}
	}
}