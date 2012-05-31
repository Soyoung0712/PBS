package com.android;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import com.android.Download.NewArrayAdapter;
import com.pbs.client.model.AddressUser;

public class GetPhoneList extends ListActivity {

	private NewArrayAdapter newArrayAdapter = null;
	// 모두선택 Flag (초기 설정은 모두선택이 해지된 상태)
	boolean allClickStatuFlag = false;
	
	// 전화번호부 리스트
	private List<AddressUser> addressUserList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getphonelist);
		
		// 전화번호 주소록 가져오기
		ContentResolver cr = getContentResolver();
        Cursor c = this.managedQuery(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);        
        startManagingCursor(c);
        
        // 전화번호 주소록 사용자 저장
        addressUserList = new ArrayList<AddressUser>();
        while(c.moveToNext()) {
        	
        	AddressUser addressUser = new AddressUser();
        	
        	// ID 저장
            String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            addressUser.setId(id);

            // 이름 저장
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            addressUser.setName(name);
            
            // 전화 저장
            List<String> dialList = new ArrayList<String>();
            if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor cp = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ", new String[] { id }, null);
                while (cp.moveToNext()) {
                	dialList.add(cp.getString(cp.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1)));
                }
                cp.close();
            }
            addressUser.setDialList(dialList);
            
            /*
             * 사진,메일은 사용하지 않으므로 주석처리
            // 사진 저장
            int photoID = c.getInt(c.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
            Bitmap icon = null;
            if (photoID != 0) {
                Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id));
                InputStream in = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
                icon = BitmapFactory.decodeStream(in);
            }
            addressUser.setIcon(icon);           

            // 메일 저장
            String mail = "";
            Cursor cm = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ? ", new String[] { id }, null);
            while (cm.moveToNext()) {
                mail = cm.getString(cm.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            }
            addressUser.setMail(mail);
            cm.close();
            */            
            // 추가            
            addressUserList.add(addressUser);
        }        
        c.close();
        
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
				Toast.makeText(GetPhoneList.this, "가져오기 완료", Toast.LENGTH_SHORT).show();
				
				// 선택한 전화번호만 저장
				List<AddressUser> resultAddressUserList = new ArrayList<AddressUser>();						
				for(int i=0; i<addressUserList.size(); i++) {
					if( addressUserList.get(i).isChecked()  ) {
						resultAddressUserList.add(addressUserList.get(i));
					}
				}
				
				finish();
			}
		});

		// "취소" 버튼
		Button mGroupCancel = (Button) findViewById(R.id.cancle);
		mGroupCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Toast.makeText(GetPhoneList.this, "취소", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		
	}

	class NewArrayAdapter extends ArrayAdapter {
		
		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.checkmemberrow, addressUserList);
			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			
			final int pos = position;
			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.checkmemberrow, null);
			
			// 전화번호부
			AddressUser addressUser =  addressUserList.get(pos);

			// 이름
			TextView textView = (TextView) row.findViewById(R.id.name);
			textView.setText(addressUser.getName());
			
			// 체크박스 상태			
			CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkBox);
			checkBox.setChecked(addressUser.isChecked());
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				// 클릭할때 마다 상태 저장
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					addressUserList.get(pos).setChecked(isChecked);					
				}

			});

			return row;
		}
	}
}