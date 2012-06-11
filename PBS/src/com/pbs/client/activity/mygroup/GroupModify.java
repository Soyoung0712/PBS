package com.pbs.client.activity.mygroup;

import java.util.ArrayList;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.R;
import com.pbs.client.activity.edit.GetAddressList;
import com.pbs.client.activity.edit.GetMemberList;
import com.pbs.client.activity.newgroup.InviteSms;
import com.pbs.client.model.AddressUser;
import com.pbs.client.model.TbAccessUser;
import com.pbs.client.model.TbGroup;
import com.pbs.client.model.TbMember;
import com.pbs.client.util.DeviceManager;
import com.pbs.client.util.UserGson;
 

public class GroupModify extends Activity {

	private final int MEMBER_GET_MEMBER_LIST_ACTIVITY 	= 1;
	private final int MEMBER_GET_ADDRESS_LIST_ACTIVITY 	= 2;
	private final int ADMIN_GET_MEMBER_LIST_ACTIVITY 	= 3;
	private final int ADMIN_GET_ADDRESS_LIST_ACTIVITY 	= 4;
	
	private String myPhoneNum = null;
	private UserGson userGson = new UserGson();
	
	// �׷��
	private EditText etGroupName;	
	// �׷�Ű
	private TextView tvGroupKey;
	
	// ��й�ȣ ���� üũ �ڽ�
	private CheckBox chPassword;						
	// ��й�ȣ	
	private EditText etPassword;
	// ��й�ȣ Ȯ��
	private EditText etPasswordConfirm;
	// �׷� ��������	
	private EditText etGroupNotice;
	
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
	private Button bGroupUpdate;
	// SMS�˸�
	private Button bSmsNoti;
	// ���
	private Button bCancel;	
	// ����
	private Button bGroupDelete;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_group_modify);
		
		// �� ��ȭ��ȣ ��������
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);
		
		Intent intent = getIntent();
		final long pk_group = intent.getExtras().getLong("pk_group");
		
		// �׷� ���� ��������
		TbGroup tbGroup	= userGson.getGroupInfo(pk_group, myPhoneNum);
		
		// �׷��
		etGroupName = (EditText) findViewById(R.id.etGroupName);
		etGroupName.setText(tbGroup.getFd_group_name());
		// �׷�Ű
		tvGroupKey = (TextView) findViewById(R.id.tvGroupKey);
		tvGroupKey.setText(tbGroup.getPk_group() + "");
		
		// ��й�ȣ üũ�ڽ�
		chPassword = (CheckBox) findViewById(R.id.chPassword);
		// ��й�ȣ
		etPassword = (EditText) findViewById(R.id.etPassword);
		etPassword.setEnabled(false);
		// ��й�ȣ Ȯ��
		etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
		etPasswordConfirm.setEnabled(false);
		
		// �׷� �������� 
		etGroupNotice = (EditText) findViewById(R.id.etGroupNotice);
		etGroupNotice.setText(tbGroup.getFd_group_notice());
		
		// �׷�� ����
		List<TbMember> tmpGroupMemberList 	= userGson.getMemeberList(pk_group, myPhoneNum); // �׷�� ���� ��������
		groupMemberList = new ArrayList<AddressUser>();
		for(int i=0; i<tmpGroupMemberList.size(); i++) {
			TbMember tbMember = tmpGroupMemberList.get(i);
			AddressUser addressUser = new AddressUser();
			addressUser.setName(tbMember.getFd_member_name());
			addressUser.setDial(tbMember.getFd_member_phone());
			groupMemberList.add(addressUser);
		}
		etGroupMemberListInfo = (EditText) findViewById(R.id.etGroupMemberListInfo);
		bGroupGetAddressList = (Button) findViewById(R.id.bGroupGetAddressList);
		// �׷���� �ִ°�� "�׷�� �߰�" �ؽ�Ʈ ����
    	if( groupMemberList != null && groupMemberList.size() > 0 ) {	            		
    		String headerMemberName = groupMemberList.get(0).getName();
    		etGroupMemberListInfo.setText(headerMemberName + " �� " + (groupMemberList.size()-1) + "��");
    	}
		
		// ������ ����				
		List<TbAccessUser> tmpAccessUserList = userGson.getAdminList(pk_group, myPhoneNum); // ������ ���� ��������
		adminMemberList = new ArrayList<AddressUser>();
		for(int i=0; i<tmpAccessUserList.size(); i++) {	
			TbAccessUser tbAccessUser = tmpAccessUserList.get(i);
			AddressUser addressUser = new AddressUser();
			addressUser.setName(tbAccessUser.getFd_member_name());
			addressUser.setDial(tbAccessUser.getFd_access_phone());
			adminMemberList.add(addressUser);
		}		
		
		chAdmin = (CheckBox) findViewById(R.id.chAdmin);
		etAdminMemberListInfo = (EditText) findViewById(R.id.etAdminMemberListInfo);
		// �������� �ִ°�� "������ �߰�" �ؽ�Ʈ ����
    	if( adminMemberList != null && adminMemberList.size() > 0 ) {	            		
    		String headerMemberName = adminMemberList.get(0).getName();
    		etAdminMemberListInfo.setText(headerMemberName + " �� " + (adminMemberList.size()-1) + "��");
    	}
		etAdminMemberListInfo.setEnabled(false);
		bAdminGetAddressList = (Button) findViewById(R.id.bAdminGetAddressList);
		bAdminGetAddressList.setEnabled(false);
		
		// �����Ϸ� ��ư
		bGroupUpdate = (Button) findViewById(R.id.bGroupUpdate);
		// Sms�˸� ��ư
		bSmsNoti 	 = (Button) findViewById(R.id.bSmsNoti);
		// ��� ��ư
		bCancel 	 = (Button) findViewById(R.id.bCancel);
		// ���� ��ư
		bGroupDelete = (Button) findViewById(R.id.bGroupDelete);
		
		//-- �̺�Ʈ ��� --//
		//
		// "��й�ȣ ����" üũ�ڽ�
		chPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (chPassword.isChecked()) {
					etPassword.setEnabled(true);
					etPasswordConfirm.setEnabled(true);					
				} else {									
					etPassword.setEnabled(false);
					etPasswordConfirm.setEnabled(false);
				}
			}
		});	

		// "�׷�� ����" > "�׷�� �߰�" �ؽ�Ʈ �ڽ� Ŭ��
		etGroupMemberListInfo.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent event) {				
				if (event.getAction() == KeyEvent.ACTION_UP) {					
					Intent intent = new Intent(GroupModify.this, GetMemberList.class);
					intent.putExtra("memberList", groupMemberList);
					startActivityForResult(intent, MEMBER_GET_MEMBER_LIST_ACTIVITY);					
				}
				return false;
			}
			
		});
		
		// "�׷�� ����" > "��������" ��ư
		bGroupGetAddressList.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(GroupModify.this, GetAddressList.class);				
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
					Intent intent = new Intent(GroupModify.this, GetMemberList.class);
					intent.putExtra("memberList", adminMemberList);
					startActivityForResult(intent, ADMIN_GET_MEMBER_LIST_ACTIVITY);
				}
				return false;
			}
		});
		
		
		// ������ ��ȣ "��������" ��ư
		bAdminGetAddressList.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(GroupModify.this, GetAddressList.class);				
				startActivityForResult(intent, ADMIN_GET_ADDRESS_LIST_ACTIVITY);
			}
		});	
		
		// "�����Ϸ�" ��ư Ŭ��
		bGroupUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				
				//-- �Է°� ��ȿ�� üũ
				String groupName 		= etGroupName.getText().toString();
				String password 		= etPassword.getText().toString();
				String passwordConfirm 	= etPasswordConfirm.getText().toString();
				String groupNotice 		= etGroupNotice.getText().toString();
				
				boolean bCheckInput = true;
				String checkErrorMsg = "";
				if( bCheckInput && groupName.length() <= 0 ) {
					bCheckInput = false;
					checkErrorMsg = "�׷���� �Է��� �ּ���.";
				}
				if( chPassword.isChecked() ) {
					if( bCheckInput && password.length() <= 0 ) {
						bCheckInput = false;
						checkErrorMsg = "��й�ȣ�� �Է��� �ּ���.";
					}				
					if( bCheckInput && !password.equals(passwordConfirm) ) {
						bCheckInput = false;				
						checkErrorMsg = "��й�ȣ�� Ȯ���� �ּ���";
					}
				}
				
				// �Է°� ��ȿ�� ����
				if( bCheckInput == false ) {
					Toast.makeText(GroupModify.this, checkErrorMsg,Toast.LENGTH_LONG).show();
					
				// �Է°� ��ȿ�� üũ ���
				}else {							
					
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
					
					Log.d("GroupModify users: " , users.toString());
					Log.d("GroupModify admins: " , admins.toString());					
					
					// ��й�ȣ�� �������� ������, ��й�ȣ�� �������� ġȯ
					if( !chPassword.isChecked() ) {
						etPassword.setText("");
					}
					// �׷� ������Ʈ
					userGson.updateGroup(pk_group, 
										 etGroupName.getText().toString(), 
										 etPassword.getText().toString(),
										 etGroupNotice.getText().toString(), 
										 myPhoneNum,
										 users, 
										 admins);
					
					finish();
				}
				
			}
		});
		
		// "SMS �˸�" ��ư Ŭ��
		bSmsNoti.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				
				Intent intent = new Intent(GroupModify.this,InviteSms.class);
				//intent.putExtra("groupMemberList", groupMemberList);
				intent.putExtra("pk_group", String.valueOf(pk_group));
				startActivity(intent);
				
			}
		});
		
		// "���" ��ư Ŭ��
		bCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				finish();
			}
		});
		
		// "���� " ��ư Ŭ��
		bGroupDelete.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// �׷� ����
				userGson.deleteGroup(pk_group, myPhoneNum);
				finish();
			}
		});
	}	
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	/**
	 * Intent���
	 */	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		switch (requestCode) {
		
			// "�׷�� ����" > "�׷�� �߰�" ����
			case MEMBER_GET_MEMBER_LIST_ACTIVITY:				
				if (resultCode == RESULT_OK) {           	
	            	
					// ������ �׷�� ���� ��������
	            	ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>)intent.getSerializableExtra("addressUserList");
	            	// ������ �׷�� ���� ����
	            	groupMemberList = addressUserList;
	            	// �ؽ�Ʈ ���� ����
	            	if( groupMemberList != null && groupMemberList.size() > 0 ) {	            		
	            		String headerMemberName = groupMemberList.get(0).getName();
	            		etGroupMemberListInfo.setText(headerMemberName + " �� " + (groupMemberList.size()-1) + "��");
	            	}
	            	
	            }
				break;
						
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
				
			// "������ ����" > "������ �߰�" ����
			case ADMIN_GET_MEMBER_LIST_ACTIVITY:				
				if (resultCode == RESULT_OK) {           	
	            	
					// ������ �׷�� ���� ��������
	            	ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>)intent.getSerializableExtra("addressUserList");
	            	// ������ �׷�� ���� ����
	            	adminMemberList = addressUserList;
	            	// �ؽ�Ʈ ���� ����
	            	if( adminMemberList != null && adminMemberList.size() > 0 ) {	            		
	            		String headerMemberName = adminMemberList.get(0).getName();
	            		etAdminMemberListInfo.setText(headerMemberName + " �� " + (adminMemberList.size()-1) + "��");
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
