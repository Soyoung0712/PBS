package com.pbs.client.activity.newgroup;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.android.R;
import com.android.R.id;
import com.android.R.layout;
import com.pbs.client.activity.edit.GetMemberList;
import com.pbs.client.activity.edit.GetAddressList;
import com.pbs.client.model.AddressUser;
import com.pbs.client.util.UserGson;

public class CreateGroup extends Activity {

	private final int MEMBER_GET_MEMBER_LIST_ACTIVITY 	= 1;
	private final int MEMBER_GET_ADDRESS_LIST_ACTIVITY 	= 2;
	private final int ADMIN_GET_MEMBER_LIST_ACTIVITY 	= 3;
	private final int ADMIN_GET_ADDRESS_LIST_ACTIVITY 	= 4;
	
	private String myPhoneNum = "01077778888";
	private UserGson userGson = new UserGson();
	
	// �׷��
	private EditText groupname;	
	// ��й�ȣ	
	private EditText password;
	// ��й�ȣ Ȯ��
	private EditText passwordConfirm;
	// �׷� ��������	
	private EditText groupNotice;
	
	// �׷�� ����	
	private ArrayList<AddressUser> groupMemberList; // �׷�� ����Ʈ	
	private EditText etGroupMemberListInfo;  		// "ȫ�浿 ��3��" �ؽ�Ʈ �ڽ�
	private Button bGroupGetAddressList;     		// "��������" ��ư
	
	// ������ ����
	private ArrayList<AddressUser> adminMemberList; // �׷�� ����Ʈ
	private CheckBox chAdmin;						// ������ ���� üũ �ڽ�
	private EditText etAdminMemberListInfo;  		// "�Ӳ��� ��3��" �ؽ�Ʈ �ڽ�
	private Button bAdminGetAddressList;     		// "��������" ��ư
	
	// �����Ϸ�
	private Button bGroupResult;
	// ���
	private Button bGroupCancel;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_create_group);
		
		// �׷��
		groupname = (EditText) findViewById(R.id.etGroupName);				
		
		// ��й�ȣ
		password = (EditText) findViewById(R.id.etPassword);
		// ��й�ȣ Ȯ��
		passwordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
		
		// �׷� �������� 
		groupNotice = (EditText) findViewById(R.id.etGroupNotice);
		
		// �׷�� ����
		groupMemberList = new ArrayList<AddressUser>();
		etGroupMemberListInfo = (EditText) findViewById(R.id.etGroupMemberListInfo);
		bGroupGetAddressList = (Button) findViewById(R.id.bGroupGetAddressList);
		
		// ������ ����
		adminMemberList = new ArrayList<AddressUser>();
		chAdmin = (CheckBox) findViewById(R.id.chAdmin);
		etAdminMemberListInfo = (EditText) findViewById(R.id.etAdminMemberListInfo);
		etAdminMemberListInfo.setEnabled(false);
		bAdminGetAddressList = (Button) findViewById(R.id.bAdminGetAddressList);
		bAdminGetAddressList.setEnabled(false);
		
		// �����Ϸ�
		bGroupResult = (Button) findViewById(R.id.bGroupResult);
		bGroupCancel = (Button) findViewById(R.id.bGroupCancel);		 
		 
	}
	@Override
	public void onResume() {
		super.onResume();	

		// "�׷�� ����" > "�׷�� �߰�" �ؽ�Ʈ �ڽ� Ŭ��
		etGroupMemberListInfo.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent event) {			
				if (event.getAction() == KeyEvent.ACTION_UP) {
					Intent intent = new Intent(CreateGroup.this, GetMemberList.class);
					startActivity(intent);
				}
				return false;
			}
		});
		
		// "�׷�� ����" > "��������" ��ư
		bGroupGetAddressList.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(CreateGroup.this, GetAddressList.class);				
				startActivityForResult(intent, MEMBER_GET_ADDRESS_LIST_ACTIVITY);				
			}
		});
		
		// "������ ��ȣ�Է�" üũ�ڽ�
		chAdmin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (chAdmin.isChecked()) {
					etAdminMemberListInfo.setEnabled(true);
					bAdminGetAddressList.setEnabled(true);					
				} else {									
					etAdminMemberListInfo.setEnabled(false);
					bAdminGetAddressList.setEnabled(false);
				}
			}
		});	
		
		// "������ ����" > "������ �߰�" �ؽ�Ʈ �ڽ� Ŭ�� 
		etAdminMemberListInfo.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent event) {
				if (event.getAction() == KeyEvent.ACTION_UP) {					
					// �ϴ� EditMember Ŭ���� �� �ص�.;;
					Intent intent = new Intent(CreateGroup.this, GetMemberList.class); 
					startActivity(intent);
				}
				return false;
			}
		});
		
		// ������ ��ȣ "��������" ��ư
		bAdminGetAddressList.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(CreateGroup.this, GetAddressList.class);				
				startActivityForResult(intent, ADMIN_GET_ADDRESS_LIST_ACTIVITY);
			}
		});	
		
		// "�����Ϸ�" ��ư Ŭ��
		bGroupResult.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(CreateGroup.this,CreateGroupComplete.class);
				intent.putExtra("groupname", groupname.getText().toString());				
				
				// �׷�� ������ "�ۿ���:010123456" ���·� �迭�� ����
				String[] users  = new String[groupMemberList.size()];				
				for( int i=0; i<groupMemberList.size(); i++ ) {
					String tmpData = groupMemberList.get(i).getName() + ":" + groupMemberList.get(i).getDial();
					users[i] = tmpData;
				}
				
				// ������ ������  "�ۿ���:010123456" ���·� �迭�� ����
				String[] admins = new String[adminMemberList.size()];
				for( int i=0; i<adminMemberList.size(); i++ ) {
					String tmpData = adminMemberList.get(i).getName() + ":" + adminMemberList.get(i).getDial();
					admins[i] = tmpData;
				}
				
				Log.d("CreateGroup users: " , users.toString());
				Log.d("CreateGroup admins: " , admins.toString());
				
				userGson.createGroup(	groupname.getText().toString(), 
										password.getText().toString(), 
										groupNotice.getText().toString(), 
										myPhoneNum, users, admins);
				startActivity(intent);
			}
		});

	}
	
	/**
	 * Intent���
	 */	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		switch (requestCode) {
		
			// "�׷�� ����" > "��������" ����
			case MEMBER_GET_ADDRESS_LIST_ACTIVITY:				
				if (resultCode == RESULT_OK) {           	
	            	
					// ������ �׷�� �߰�
	            	ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>)intent.getSerializableExtra("addressUserList");
	            	// �ߺ� ��ȣ�� �����ϰ� �߰��Ѵ�
	            	addAllListSubDuplication(groupMemberList, addressUserList);
	            	// �ؽ�Ʈ ���� ����
	            	if( groupMemberList != null && groupMemberList.size() > 0 ) {	            		
	            		String headerMemberName = groupMemberList.get(0).getName();
	            		etGroupMemberListInfo.setText(headerMemberName + " �� " + (groupMemberList.size()-1) + "��");
	            	}
	            	
	            }
				break;
				
			// "������ ����" > "��������" ����
			case ADMIN_GET_ADDRESS_LIST_ACTIVITY:				
				if (resultCode == RESULT_OK) {           	
	            	
					// ������ ������ �߰�
	            	ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>)intent.getSerializableExtra("addressUserList");	
	            	// �ߺ� ��ȣ�� �����ϰ� �߰��Ѵ�
	            	addAllListSubDuplication(adminMemberList, addressUserList);
	            	// �ؽ�Ʈ ���� ����
	            	if( adminMemberList != null && adminMemberList.size() > 0 ) {	            		
	            		String headerMemberName = adminMemberList.get(0).getName();
	            		etAdminMemberListInfo.setText(headerMemberName + " �� " + (adminMemberList.size()-1) + "��");
	            	}
	            	
	            }
				break;
	
			default:
				break;
		}
        
    }   
	
	
	/**
	 * userList1�� userList2�� �����Ѵ�.
	 * �� �ߺ� ��ȭ��ȣ�� ���� ���� �ʴ´�.
	 * @param userList1
	 * @param userList2
	 */
	private void addAllListSubDuplication(ArrayList<AddressUser> userList1, ArrayList<AddressUser> userList2 ) {
		
		if (userList1 != null && userList2 != null) {
			for( int i=0; i<userList2.size(); i++) {
				String dial1 = userList2.get(i).getDial();
				
				boolean bDuplication = false;
				for( int j=0; j<userList1.size(); j++) {
					// �ߺ� ��ȭ��ȣ ����� ����
					if( dial1.equals(userList1.get(j).getDial())) {
						bDuplication = true;
						break;
					}
				}
				
				// �ߺ� ��ȭ��ȣ�� ���»���ڸ� �߰�
				if( bDuplication == false) {
					userList1.add(userList2.get(i));
				}
			}
		}
		
	}

}
