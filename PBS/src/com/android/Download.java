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

	// 내 그룹 리스트
	String[] name = { "홍길동1", "홍길동2", "홍길동3", "홍길동4", "홍길동5", "홍길동6", "홍길동7",
			"홍길동8", "홍길동9" };
	String[] number = { "010-1234-1234", "010-1234-1234", "010-1234-1234",
			"010-1234-1234", "010-1234-1234", "010-1234-1234", "010-1234-1234",
			"010-1234-1234", "010-1234-1234", "010-1234-1234", };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downloadlist);

		// 리스트뷰에 리스트 적용
		setListAdapter(new NewArrayAdapter(this));
		
		Button mSave = (Button)findViewById(R.id.sendmessage);
		Button mCancle = (Button)findViewById(R.id.cancle);
		
		
		//저장 버튼을 눌렸을때 
		mSave.setOnClickListener(new View.OnClickListener()
		{			
			public void onClick(View arg0)
			{
				Toast.makeText(Download.this, "저장 완료", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		
		//취소 버튼을 눌렸을때
		mCancle.setOnClickListener(new View.OnClickListener()
		{			
			public void onClick(View arg0)
			{
				Toast.makeText(Download.this, "취소", Toast.LENGTH_SHORT).show();
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