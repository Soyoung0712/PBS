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

		// ������ �׷��� �ɹ� ����Ʈ ��������
		Intent intent = getIntent();		
		memberList = (ArrayList<AddressUser>)intent.getSerializableExtra("memberList");
		if( memberList == null ) {
			memberList = new ArrayList<AddressUser>();
		}
		 
		// ����Ʈ�信 ����Ʈ ����
		newArrayAdapter = new NewArrayAdapter(this);
		setListAdapter(newArrayAdapter);

		// �����Ϸ� ��ư Ŭ�� ������
		Button complete = (Button) findViewById(R.id.complete);
		complete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(GetMemberList.this, "���� �Ϸ�", Toast.LENGTH_SHORT).show();
				
				// ȣ���� ��Ƽ��Ƽ				
				Intent intent = getIntent();
				intent.putExtra("addressUserList", memberList);				
				setResult(RESULT_OK, intent);
				finish();
				
			}
		});
		
		// ��ȣ �߰� ��ư		
		Button bAddMember = (Button) findViewById(R.id.bAddMember);
		bAddMember.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				showMemberAdd();
			}
		});	
	}
	

	// ��� �߰� ��ư Ŭ�� �̺�Ʈ	
	private void showMemberAdd() {
		
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout addgrouplayout = (LinearLayout) vi.inflate(R.layout.dialog_add_member, null);

		final EditText etName = (EditText) addgrouplayout.findViewById(R.id.etName);
		final EditText etPhone = (EditText) addgrouplayout.findViewById(R.id.etPhone);		
		
		// "��ȭ��ȣ �߰�" ���̾�α� ����
		Builder builder = new AlertDialog.Builder(this).setTitle("��ȭ��ȣ �߰�").setView(addgrouplayout);
		
		// "�߰�" ��ư ������ ����		
		DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {

			// ���̾� �˾��� "�߰�" ��ū Ŭ���� �̺�Ʈ
			public void onClick(DialogInterface dialog, int which) {					
				
				// �̸�, ��ȭ��ȣ �߰�				
				String name = etName.getText().toString();
				String phone = etPhone.getText().toString();								
				
				// �׷�Ű �߰� ����
				if( name.length() > 0 && phone.length() > 0 ) {					
					AddressUser addressUser = new AddressUser();
					addressUser.setName(name);
					addressUser.setDial(phone);
					
					// �̹� �ִ� ��ȣ���� üũ
					boolean bDuplication = false;
					for( int i=0; i < memberList.size(); i++ ) {						
						String tmpPhone = memberList.get(i).getDial();
						if ( tmpPhone.equals(phone) ) {
							bDuplication = true;
							break;
						}
						
					}
					
					if( bDuplication ) {
						Toast.makeText(GetMemberList.this, "�̹� ��ϵ� ����� ��ȣ �Դϴ�.",Toast.LENGTH_LONG).show();
					}else {
						memberList.add(0, addressUser);
						newArrayAdapter.notifyDataSetChanged();
					}					
					
				// �׷�Ű �߰� ����
				}else {					
					Toast.makeText(GetMemberList.this, "�߸��� ���� �Դϴ�.",Toast.LENGTH_LONG).show();
				}				
								
			}
			
		};
				
		// ���̾�α� SHOW
		builder.setNeutralButton("�߰�", onClickListener).show();
		
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
			
			// ��� ����
			AddressUser addressUser = memberList.get(pos);

			// �̸�
			EditText etName = (EditText) row.findViewById(R.id.etName);
			etName.setText(addressUser.getName());
			// ����ȣ
			EditText etNumber = (EditText) row.findViewById(R.id.etNumber);
			etNumber.setText(addressUser.getDial());
			// �����Ϸ� ��ư Ŭ�� ������
			Button bDeleteOne = (Button) row.findViewById(R.id.bDeleteOne);
			bDeleteOne.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(GetMemberList.this, "����", Toast.LENGTH_SHORT).show();
					memberList.remove(pos);
					notifyDataSetChanged();
				}
			});

			return row;
		}
	}

}
