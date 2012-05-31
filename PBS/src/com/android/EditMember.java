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
import android.widget.EditText;
import android.widget.Toast;

public class EditMember extends ListActivity {

	String[] number = { "010-1234-1234", "010-1234-1234", "010-1234-1234",
			"010-1234-1234", "010-1234-1234", "010-1234-1234", "010-1234-1234",
			"010-1234-1234", "010-1234-1234", "010-1234-1234" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editmemberlist);

		// 리스트뷰에 리스트 적용
		setListAdapter(new NewArrayAdapter(this));

		// 편집완료 버튼 클릭 했을때
		Button complete = (Button) findViewById(R.id.complete);
		complete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(EditMember.this, "편집 완료", Toast.LENGTH_SHORT)
						.show();
				finish();
				Intent intent = new Intent(EditMember.this, MakeNewGroup.class);
				startActivity(intent);
			}
		});
	}

	class NewArrayAdapter extends ArrayAdapter {
		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.editmemberrow, number);

			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();

			View row = inflater.inflate(R.layout.editmemberrow, null);

			EditText textView2 = (EditText) row.findViewById(R.id.number);
			textView2.setText(number[position]);

			return row;
		}
	}

}
