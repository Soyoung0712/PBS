package com.pbs.client.activity.mygroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.android.R;
import com.pbs.client.activity.edit.GetAddressList;
import com.pbs.client.activity.edit.GetMemberList;

public class GroupModify extends Activity
{
	EditText password;
	EditText passwordresult;

	EditText member;
	EditText manager;
	
	Button getMemberPhone;
	Button getManagerPhone;
	Button mResultOk;
	Button mCancel;

	CheckBox ch1;
	CheckBox ch2;

	boolean cnt = false; 
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_group_modify);

		getMemberPhone = (Button) findViewById(R.id.button2);  // �׷�� �������� ��ư
		getManagerPhone = (Button) findViewById(R.id.button4); // ������ �������� ��ư

		ch1 = (CheckBox) findViewById(R.id.checkBox1);
		ch2 = (CheckBox) findViewById(R.id.checkBox2);
		password = (EditText) findViewById(R.id.editText3); // ��й�ȣ ����Ʈ�ؽ�Ʈ
		passwordresult = (EditText) findViewById(R.id.editText4); // ��й�ȣ Ȯ�� ����Ʈ�ؽ�Ʈ

		mResultOk = (Button) findViewById(R.id.button5);  // �����Ϸ� ��ư
		mCancel = (Button) findViewById(R.id.button7);  // ��� ��ư

		member = (EditText) findViewById(R.id.editText5);  // �׷�� Ŭ�� ����Ʈ�ؽ�Ʈ
		manager = (EditText)findViewById(R.id.editText7);  // ������ Ŭ�� ����Ʈ�ؽ�Ʈ
 

		// ������ ��ȣ �Է� üũ�ڽ� üũ �� �⺻ ����  
		password.setEnabled(false);
		passwordresult.setEnabled(false);
		getManagerPhone.setEnabled(false);
		manager.setEnabled(false);

		member.setInputType(0);

	}

	@Override
	public void onResume()
	{
		super.onResume();

		// ��й�ȣ üũ �ڽ� �������� //
		ch1.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton arg0, boolean arg1)
			{
				// üũ ������ �Է� ����
				if (ch1.isChecked())
				{
					password.setEnabled(true);
					passwordresult.setEnabled(true);
				}
				// üũ �ȵ����� �ԷºҰ���
				else
				{
					password.setEnabled(false);
					passwordresult.setEnabled(false);

				}

			}
		});

		// ������ ��ȣ �Է� üũ�ڽ� ��������//
		ch2.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton arg0, boolean arg1)
			{
				// üũ ������ �Է°��� �ϰ�
				if (ch2.isChecked())
				{
					getManagerPhone.setEnabled(true);
					manager.setEnabled(true);
				}
				// üũ �ȵ����� �Է� �Ұ���
				else
				{
					getManagerPhone.setEnabled(false);
					manager.setEnabled(false);
				}
			}
		});

		// �׷�� ���� ���� �������� ��ư�� �������� ��ȭ��ȣ ��� ��������//
		getMemberPhone.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent intent = new Intent(GroupModify.this, GetAddressList.class);
				startActivity(intent);
			}
		});

		// ������ ��ȣ �Է� ���� �������� ��ư�� �������� ��ȭ��ȣ ��� ��������//
		getManagerPhone.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent intent1 = new Intent(GroupModify.this, GetAddressList.class);
				startActivity(intent1);
			}
		});

		// �����Ϸ� ��ư ������ ���� �Ϸ� �ϰ� ���׷� ȭ������ ���ư���
		mResultOk.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0)
			{

				Toast.makeText(GroupModify.this, "���� �Ϸ�", Toast.LENGTH_SHORT).show();
				finish();
			}
		});

		// ��ҹ�ư Ŭ����
		mCancel.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0)
			{
				Toast.makeText(GroupModify.this, "���", Toast.LENGTH_SHORT).show();
				finish();
			}
		});		
		
		// �׷�� ����Ʈ�ؽ�Ʈ ������ ��
		member.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==KeyEvent.ACTION_DOWN)
				{
					Intent intent = new Intent(GroupModify.this, GetMemberList.class);
					startActivity(intent);
				}
				return false;
			}
		});

		// ������ ����Ʈ�ؽ�Ʈ ������ ��
		manager.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent event) {
				// �׷�� EditText �� �������� �߻��Ǵ� �̺�Ʈ ó�� // �̺�Ʈ �߻��� ������ ���� ȭ������ �̵�
				if (event.getAction() == KeyEvent.ACTION_DOWN) {

					Intent intent = new Intent(GroupModify.this, GetMemberList.class);
					startActivity(intent);

				}
				return false;
			}
		});		
		
		
	}

}