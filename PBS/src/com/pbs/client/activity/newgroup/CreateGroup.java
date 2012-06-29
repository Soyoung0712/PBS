package com.pbs.client.activity.newgroup;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
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
import com.pbs.client.activity.main.WaitDlg;
import com.pbs.client.model.ActivityMap;
import com.pbs.client.model.AddressUser;
import com.pbs.client.model.TbGroup;
import com.pbs.client.util.DeviceManager;
import com.pbs.client.util.UserGson;

public class CreateGroup extends Activity
{	
	private String myPhoneNum = null;
	private UserGson userGson = new UserGson();

	// �׷��
	private EditText etGroupName;
	// ��й�ȣ
	private EditText etPassword;
	// ��й�ȣ Ȯ��
	private EditText etPasswordConfirm;
	// �׷� ��������
	private EditText etGroupNotice;

	// �׷�� ����
	private ArrayList<AddressUser> groupMemberList; // �׷�� ����Ʈ
	private EditText etGroupMemberListInfo; // "ȫ�浿 ��3��" �ؽ�Ʈ �ڽ�
	private Button bGroupGetAddressList; // "��������" ��ư

	// ������ ����
	private ArrayList<AddressUser> adminMemberList; // �׷�� ����Ʈ
	private CheckBox chAdmin; // ������ ���� üũ �ڽ�
	private EditText etAdminMemberListInfo; // "�Ӳ��� ��3��" �ؽ�Ʈ �ڽ�
	private Button bAdminGetAddressList; // "��������" ��ư

	// �����Ϸ�
	private Button bGroupResult;	 

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Ÿ��Ʋ�� ���ֱ�
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.new_create_group);

		// �� ��ȭ��ȣ ��������
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);

		// �׷��
		etGroupName = (EditText) findViewById(R.id.etGroupName);

		// ��й�ȣ
		etPassword = (EditText) findViewById(R.id.etPassword);
		// ��й�ȣ Ȯ��
		etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);

		// �׷� ��������
		etGroupNotice = (EditText) findViewById(R.id.etGroupNotice);

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
		
		// bold ó��
		// ����
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);		
		tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		// �����Ϸ�				
		bGroupResult.setPaintFlags(bGroupResult.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);			
		
		TextView tvGroupName = (TextView) findViewById(R.id.tvGroupName);
		tvGroupName.setPaintFlags(tvGroupName.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		TextView tvGroupNotice = (TextView) findViewById(R.id.tvGroupNotice);
		tvGroupNotice.setPaintFlags(tvGroupNotice.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
		TextView tbPassword = (TextView) findViewById(R.id.tbPassword);
		tbPassword.setPaintFlags(tbPassword.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
		TextView tvPasswordConfirm = (TextView) findViewById(R.id.tvPasswordConfirm);
		tvPasswordConfirm.setPaintFlags(tvPasswordConfirm.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
		TextView tvGroupMemberListInfo = (TextView) findViewById(R.id.tvGroupMemberListInfo);
		tvGroupMemberListInfo.setPaintFlags(tvGroupMemberListInfo.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		TextView chText = (TextView) findViewById(R.id.chText);
		chText.setPaintFlags(chText.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		TextView managerText = (TextView) findViewById(R.id.managerText);
		managerText.setPaintFlags(managerText.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
	}

	@Override
	public void onResume()
	{
		super.onResume();

		// "�׷�� ����" > "�׷�� �߰�" �ؽ�Ʈ �ڽ� Ŭ��
		etGroupMemberListInfo.setOnTouchListener(new OnTouchListener()
		{
			public boolean onTouch(View arg0, MotionEvent event)
			{
				if (event.getAction() == KeyEvent.ACTION_UP)
				{
					Intent intent = new Intent(CreateGroup.this, GetMemberList.class);
					intent.putExtra("memberList", groupMemberList);
					startActivityForResult(intent, ActivityMap.MEMBER_GET_MEMBER_LIST);
				}
				return false;
			}
		});

		// "�׷�� ����" > "��������" ��ư
		bGroupGetAddressList.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0) {
				
				Intent intent = new Intent(CreateGroup.this, GetAddressList.class);
				startActivityForResult(intent, ActivityMap.MEMBER_GET_ADDRESS_LIST);				

			}
		});

		// "������ ��ȣ�Է�" üũ�ڽ�
		chAdmin.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton arg0, boolean arg1)
			{
				if (chAdmin.isChecked()) {
					etAdminMemberListInfo.setEnabled(true);
					etAdminMemberListInfo.setBackgroundResource(R.drawable.edit_text_bg);
					bAdminGetAddressList.setEnabled(true);
				}else{
					etAdminMemberListInfo.setEnabled(false);
					etAdminMemberListInfo.setBackgroundResource(R.drawable.edit_text_bg_grey);
					bAdminGetAddressList.setEnabled(false);
				}
			}
		});

		// "������ ����" > "������ �߰�" �ؽ�Ʈ �ڽ� Ŭ��
		etAdminMemberListInfo.setOnTouchListener(new OnTouchListener()
		{
			public boolean onTouch(View arg0, MotionEvent event)
			{
				if (event.getAction() == KeyEvent.ACTION_UP)
				{
					Intent intent = new Intent(CreateGroup.this, GetMemberList.class);
					intent.putExtra("memberList", adminMemberList);
					startActivityForResult(intent, ActivityMap.ADMIN_GET_MEMBER_LIST);
				}
				return false;
			}
		});

		// ������ ��ȣ "��������" ��ư
		bAdminGetAddressList.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0) {
				
				Intent intent = new Intent(CreateGroup.this, GetAddressList.class);
				startActivityForResult(intent, ActivityMap.ADMIN_GET_ADDRESS_LIST);

			}
		});

		// "�����Ϸ�" ��ư Ŭ��
		bGroupResult.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {

				// -- �Է°� ��ȿ�� üũ
				String groupName = etGroupName.getText().toString();
				String password = etPassword.getText().toString();
				String passwordConfirm = etPasswordConfirm.getText().toString();
				String groupNotice = etGroupNotice.getText().toString();

				boolean bCheckInput = true;
				String checkErrorMsg = "";
				if (bCheckInput && groupName.length() <= 0)	{
					bCheckInput = false;
					checkErrorMsg = "�׷���� �Է��� �ּ���.";
				}
				
				if (bCheckInput && password.length() <= 0) {
					bCheckInput = false;
					checkErrorMsg = "��й�ȣ�� �Է��� �ּ���.";
				}
				
				if (bCheckInput && !password.equals(passwordConfirm)) {
					bCheckInput = false;
					checkErrorMsg = "��й�ȣ�� Ȯ���� �ּ���";
				}

				// �Է°� ��ȿ�� ����
				if (bCheckInput == false) {
					Toast.makeText(CreateGroup.this, checkErrorMsg, Toast.LENGTH_LONG).show();

				// �Է°� ��ȿ�� üũ ���
				}else {
					
					WaitDlg dlg = new WaitDlg(CreateGroup.this, "�׷� ����", "�׷��� �����ϰ� �ֽ��ϴ�");
					dlg.start();
					
					try {
						Intent intent = new Intent(CreateGroup.this, CreateGroupComplete.class);
						intent.putExtra("groupMame", etGroupName.getText().toString());
	
						// �׷�� ������ "�ۿ���:010123456" ���·� �迭�� ����
						String[] users = new String[groupMemberList.size()];
						for (int i = 0; i < groupMemberList.size(); i++) {
							String tmpData = groupMemberList.get(i).getName() + ":" + groupMemberList.get(i).getDial();
							users[i] = tmpData;
						}
	
						// ������ ������ "�ۿ���:010123456" ���·� �迭�� ����
						String[] admins = new String[adminMemberList.size()];
						for (int i = 0; i < adminMemberList.size(); i++) {
							String tmpData = adminMemberList.get(i).getName() + ":" + adminMemberList.get(i).getDial();
							admins[i] = tmpData;
						}
	
						Log.d("CreateGroup users: ", users.toString());
						Log.d("CreateGroup admins: ", admins.toString());
	
						TbGroup tbGroupResult = userGson.createGroup(etGroupName.getText().toString(), etPassword.getText().toString(), etGroupNotice.getText().toString(), myPhoneNum, users, admins);
	
						intent.putExtra("groupName", groupName);
						intent.putExtra("groupKey", tbGroupResult.getPk_group());
						intent.putExtra("groupPassword", password);
	
						startActivity(intent);
					}finally{
						dlg.stopLocal();
						finish();
					}
					
				}

				
				
			}
		});
		
		// �ڷΰ��� ��ư
		Button bBack = (Button) findViewById(R.id.bBack);
		bBack.setOnClickListener(new View.OnClickListener()	{
			public void onClick(View arg0)	{
				finish();				
			}
		});	

	}

	/**
	 * Intent���
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		switch (requestCode) {

			// "�׷�� ����" > "�׷�� �߰�" ����
			case ActivityMap.MEMBER_GET_MEMBER_LIST:
				if (resultCode == RESULT_OK) {

					// ������ �׷�� ���� ��������
					ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>) intent.getSerializableExtra("addressUserList");
					// ������ �׷�� ���� ����
					groupMemberList = addressUserList;
					// �ؽ�Ʈ ���� ����
					if (groupMemberList != null && groupMemberList.size() > 0) {
						String headerMemberName = groupMemberList.get(0).getName();
						etGroupMemberListInfo.setText(headerMemberName + " �� " + (groupMemberList.size() - 1) + "��");
					}else {
						etGroupMemberListInfo.setText("�׷�� �߰�");
					}

				}
				break;

			// "�׷�� ����" > "��������" ����
			case ActivityMap.MEMBER_GET_ADDRESS_LIST:
				if (resultCode == RESULT_OK) {

					// ������ �׷�� �߰�
					ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>) intent.getSerializableExtra("addressUserList");
					// �ߺ� ��ȣ�� �����ϰ� �߰��Ѵ�
					addAllListSubDuplication(groupMemberList, addressUserList);
					// �ؽ�Ʈ ���� ����
					if (groupMemberList != null && groupMemberList.size() > 0) {
						String headerMemberName = groupMemberList.get(0).getName();
						etGroupMemberListInfo.setText(headerMemberName + " �� " + (groupMemberList.size() - 1) + "��");
					}					
				}
				break;

			// "������ ����" > "������ �߰�" ����
			case ActivityMap.ADMIN_GET_MEMBER_LIST:
				if (resultCode == RESULT_OK) {

					// ������ �׷�� ���� ��������
					ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>) intent.getSerializableExtra("addressUserList");
					// ������ �׷�� ���� ����
					adminMemberList = addressUserList;
					// �ؽ�Ʈ ���� ����
					if (adminMemberList != null && adminMemberList.size() > 0) {
						String headerMemberName = adminMemberList.get(0).getName();
						etAdminMemberListInfo.setText(headerMemberName + " �� " + (adminMemberList.size() - 1) + "��");
					}else {						
						etAdminMemberListInfo.setText("������ �߰�");
					}

				}
				break;

			// "������ ����" > "��������" ����
			case ActivityMap.ADMIN_GET_ADDRESS_LIST:
				if (resultCode == RESULT_OK) {

					// ������ ������ �߰�
					ArrayList<AddressUser> addressUserList = (ArrayList<AddressUser>) intent.getSerializableExtra("addressUserList");
					// �ߺ� ��ȣ�� �����ϰ� �߰��Ѵ�
					addAllListSubDuplication(adminMemberList, addressUserList);
					// �ؽ�Ʈ ���� ����
					if (adminMemberList != null && adminMemberList.size() > 0) {
						String headerMemberName = adminMemberList.get(0).getName();
						etAdminMemberListInfo.setText(headerMemberName + " �� " + (adminMemberList.size() - 1) + "��");
					}					
				}
				break;

			default:
				break;
		}

	}

	/**
	 * userList1�� userList2�� �����Ѵ�. �� �ߺ� ��ȭ��ȣ�� ���� ���� �ʴ´�.
	 * 
	 * @param userList1
	 * @param userList2
	 */
	private void addAllListSubDuplication(ArrayList<AddressUser> userList1, ArrayList<AddressUser> userList2)
	{

		if (userList1 != null && userList2 != null)
		{
			for (int i = 0; i < userList2.size(); i++)
			{
				String dial1 = userList2.get(i).getDial();

				boolean bDuplication = false;
				for (int j = 0; j < userList1.size(); j++)
				{
					// �ߺ� ��ȭ��ȣ ����� ����
					if (dial1.equals(userList1.get(j).getDial()))
					{
						bDuplication = true;
						break;
					}
				}

				// �ߺ� ��ȭ��ȣ�� ���»���ڸ� �߰�
				if (bDuplication == false)
				{
					userList1.add(userList2.get(i));
				}
			}
		}

	}

}
