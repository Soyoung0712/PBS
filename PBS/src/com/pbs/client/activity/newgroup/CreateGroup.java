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

public class CreateGroup extends Activity {

	private final int MEMBER_GET_MEMBER_LIST_ACTIVITY 	= 1;
	private final int MEMBER_GET_ADDRESS_LIST_ACTIVITY 	= 2;
	private final int ADMIN_GET_MEMBER_LIST_ACTIVITY 	= 3;
	private final int ADMIN_GET_ADDRESS_LIST_ACTIVITY 	= 4;
	
	private EditText groupname;	

	private EditText password;
	private EditText passwordresult;

	
	private Button plus3;
	

	private EditText groupNameFix;
	
	private Button mGroupResult;
	private Button mGroupCanclel;
	
	// �׷�� ����
	private ArrayList<AddressUser> groupMemberList; // �׷�� ����Ʈ
	private EditText etGroupMemberListInfo;  // "ȫ�浿 ��3��" �ؽ�Ʈ �ڽ�
	private Button bGroupGetAddressList;     // "��������" ��ư
	
	
	// ������ ����
	private ArrayList<AddressUser> adminMemberList; // �׷�� ����Ʈ
	private EditText etAdminMemberListInfo;  // "�Ӳ��� ��3��" �ؽ�Ʈ �ڽ�
	private Button bAdminGetAddressList;     // "��������" ��ư

	private CheckBox ch1;
	private CheckBox ch2;

	
	Editable etable;
	String result;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_create_group);
		
		groupMemberList = new ArrayList<AddressUser>();
		adminMemberList = new ArrayList<AddressUser>();
 
		groupNameFix = (EditText)findViewById(R.id.editText1);
		    etable = groupNameFix.getText();
		    result = etable.toString();
		  
		groupname = (EditText) findViewById(R.id.editText7);		
		bGroupGetAddressList = (Button) findViewById(R.id.button2);
		plus3 = (Button) findViewById(R.id.button3);
		bAdminGetAddressList = (Button) findViewById(R.id.button4);

		ch2 = (CheckBox) findViewById(R.id.checkBox2);
		password = (EditText) findViewById(R.id.editText3);
		passwordresult = (EditText) findViewById(R.id.editText4);
		mGroupResult = (Button) findViewById(R.id.button5);
		etGroupMemberListInfo = (EditText) findViewById(R.id.editText5);
		etAdminMemberListInfo = (EditText) findViewById(R.id.editText7);

		
		groupname.setEnabled(false);		

		plus3.setEnabled(false);
		bAdminGetAddressList.setEnabled(false);
		
	 
		 
	}
	@Override
	public void onResume() {
		super.onResume();

		ch2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (ch2.isChecked()) {
					groupname.setEnabled(true);					
					bGroupGetAddressList.setEnabled(true);
					plus3.setEnabled(true);
					bAdminGetAddressList.setEnabled(true);
				} else {
					groupname.setEnabled(false);
					plus3.setEnabled(false);
					bAdminGetAddressList.setEnabled(false);
				}
			}
		});

	
		
		
		// �׷���� �Ϸ��ư ��������
		mGroupResult.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(CreateGroup.this,
						CreateGroupComplete.class);
				intent.putExtra("groupname", groupNameFix.getText());
			
				
				 
					startActivity(intent);
			 
			}
		});

		// "�׷�� ����" > "�Ӳ��� ��3��" �ؽ�Ʈ �ڽ� Ŭ��
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
		
		// "������ ����" > "�Ӳ��� ��3��" �ؽ�Ʈ �ڽ� Ŭ�� 
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
