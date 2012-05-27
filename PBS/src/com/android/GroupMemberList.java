package com.android;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupMemberList extends ListActivity {

	// �� �׷� ����Ʈ
	String[] member = { "ȫ�浿1", "ȫ�浿2", "ȫ�浿3", "ȫ�浿4", "ȫ�浿5", "ȫ�浿6", "ȫ�浿7",
			"ȫ�浿8", "ȫ�浿9" };
	String[] number = { "010-1234-1234", "010-1234-1234", "010-1234-1234",
			"010-1234-1234", "010-1234-1234", "010-1234-1234", "010-1234-1234",
			"010-1234-1234", "010-1234-1234", "010-1234-1234", };
	int[] img = { R.drawable.phone, R.drawable.phone, R.drawable.phone,
			R.drawable.phone, R.drawable.phone, R.drawable.phone,
			R.drawable.phone, R.drawable.phone, R.drawable.phone };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupmemberlist);

		// ����Ʈ�信 ����Ʈ ����
		setListAdapter(new NewArrayAdapter(this));

		Button mPhoneMove = (Button)findViewById(R.id.button1);
		Button mSMSMove= (Button)findViewById(R.id.button2);
		Button mCancel = (Button)findViewById(R.id.button3);
		mPhoneMove.setOnClickListener(new View.OnClickListener()
		{			
			public void onClick(View arg0)
			{
				
				Intent intent = new Intent(GroupMemberList.this,Download.class)	;
				startActivity(intent);
			}
		});
		mSMSMove.setOnClickListener(new View.OnClickListener()
		{			
			public void onClick(View arg0)
			{
				Intent intent1 = new Intent(GroupMemberList.this,InviteMember.class)	;
				startActivity(intent1);
			}
		});
		mCancel.setOnClickListener(new View.OnClickListener()
		{			
			public void onClick(View arg0)
			{
				finish();
			}
		});
		
	}

	class NewArrayAdapter extends ArrayAdapter {
		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.memberrow, member);

			this.context = context;
		}

		// 
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();

			View row = inflater.inflate(R.layout.memberrow, null);

			TextView textView = (TextView) row.findViewById(R.id.name);
			textView.setText(member[position]);
			TextView textView2 = (TextView) row.findViewById(R.id.number);
			textView2.setText(number[position]);

			ImageView imageView = (ImageView) row.findViewById(R.id.phone);
			imageView.setImageResource(img[position]);

			return row;
		}
	}
}