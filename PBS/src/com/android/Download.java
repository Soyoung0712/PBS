package com.android;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Download extends ListActivity {

	// �� �׷� ����Ʈ
	String[] name = { "ȫ�浿1", "ȫ�浿2", "ȫ�浿3", "ȫ�浿4", "ȫ�浿5", "ȫ�浿6", "ȫ�浿7",
			"ȫ�浿8", "ȫ�浿9" };
	String[] number = { "010-1234-1234", "010-1234-1234", "010-1234-1234",
			"010-1234-1234", "010-1234-1234", "010-1234-1234", "010-1234-1234",
			"010-1234-1234", "010-1234-1234", "010-1234-1234", };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downloadlist);

		// ����Ʈ�信 ����Ʈ ����
		setListAdapter(new NewArrayAdapter(this));
		
		Button mSave = (Button)findViewById(R.id.sendmessage);
		Button mCancle = (Button)findViewById(R.id.cancle);
		
		
		//���� ��ư�� �������� 
		mSave.setOnClickListener(new View.OnClickListener()
		{			
			public void onClick(View arg0)
			{
				Toast.makeText(Download.this, "���� �Ϸ�", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		
		//��� ��ư�� ��������
		mCancle.setOnClickListener(new View.OnClickListener()
		{			
			public void onClick(View arg0)
			{
				Toast.makeText(Download.this, "���", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	
		
	}

 
	
	class NewArrayAdapter extends ArrayAdapter {
		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.downloadrow, name);

			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();

			View row = inflater.inflate(R.layout.downloadrow, null);

			TextView textView = (TextView) row.findViewById(R.id.name);
			textView.setText(name[position]);
			TextView textView2 = (TextView) row.findViewById(R.id.number);
			textView2.setText(number[position]);

			return row;
		}
	}
}