package com.pbs.test;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class PBS_TESTActivity extends TabActivity
{

	EditText groupname;
	EditText groupnumber;

	EditText password;
	EditText passwordresult;
	
	Button numberUrl;
	Button plus;
	Button plus2;

	CheckBox ch1;
	CheckBox ch2;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		TabHost tabHost = getTabHost();
		LayoutInflater inflater = LayoutInflater.from(this);
		inflater.inflate(R.layout.main, tabHost.getTabContentView(), true);

		groupname = (EditText) findViewById(R.id.editText7);
		groupnumber = (EditText) findViewById(R.id.editText8);
		
		numberUrl = (Button)findViewById(R.id.button2);
		
		plus = (Button) findViewById(R.id.button3);
		plus2 = (Button) findViewById(R.id.button4);
		
		ch1 = (CheckBox) findViewById(R.id.checkBox1);
		ch2 = (CheckBox) findViewById(R.id.checkBox2);
		password = (EditText) findViewById(R.id.editText3);
		passwordresult = (EditText) findViewById(R.id.editText4);

		TabSpec tabSpec1 = tabHost.newTabSpec("tab1").setIndicator("�� �׷�");
		tabSpec1.setContent(R.id.tab2);
		tabHost.addTab(tabSpec1);

		TabSpec tabSpec2 = tabHost.newTabSpec("tab2").setIndicator("�� �׷� �����");
		tabSpec2.setContent(R.id.tab1);
		tabHost.addTab(tabSpec2);

		tabHost.setCurrentTab(0);

	}

	@Override
	public void onResume()
	{
		super.onResume();

		password.setEnabled(false);
		passwordresult.setEnabled(false);
		groupname.setEnabled(false);
		groupnumber.setEnabled(false);
		plus.setEnabled(false);
		plus2.setEnabled(false);

		// ��й�ȣ üũ �ڽ� �������� //
		ch1.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton arg0, boolean arg1)
			{
				//üũ ������ �Է� ����
				if (ch1.isChecked())
				{
					password.setEnabled(true);
					passwordresult.setEnabled(true);
				}
				//üũ �ȵ����� �ԷºҰ���
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
					groupname.setEnabled(true);
					groupnumber.setEnabled(true);
					plus.setEnabled(true);
					plus2.setEnabled(true);
				}
				// üũ �ȵ����� �Է� �Ұ���
				else
				{
					groupname.setEnabled(false);
					groupnumber.setEnabled(false);
					plus.setEnabled(false);
					plus2.setEnabled(false);
				}
			}
		});
		
		// �׷�� ���� ���� �������� ��ư�� �������� ��ȭ��ȣ ��� ��������//
		numberUrl.setOnClickListener(new OnClickListener()
		{			
			public void onClick(View arg0)
			{
				Intent content = new Intent();
				content.setAction(android.content.Intent.ACTION_VIEW);
				content.setData(ContactsContract.Contacts.CONTENT_URI);
				startActivity(content);				
			}
		});
		
		// ������ ��ȣ �Է� ���� �������� ��ư�� �������� ��ȭ��ȣ ��� ��������//
		plus2.setOnClickListener(new OnClickListener()
		{			
			public void onClick(View arg0)
			{				Intent content1 = new Intent();
				content1.setAction(android.content.Intent.ACTION_VIEW);
				content1.setData(ContactsContract.Contacts.CONTENT_URI);
				startActivity(content1);
			}
		});
		
		

	}

 
}