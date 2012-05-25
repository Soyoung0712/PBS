package com.android;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupMemberList extends ListActivity {

	// ³» ±×·ì ¸®½ºÆ®
	String[] member = { "È«±æµ¿1", "È«±æµ¿2", "È«±æµ¿3", "È«±æµ¿4", "È«±æµ¿5", "È«±æµ¿6", "È«±æµ¿7",
			"È«±æµ¿8", "È«±æµ¿9" };
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

		// ¸®½ºÆ®ºä¿¡ ¸®½ºÆ® Àû¿ë
		setListAdapter(new NewArrayAdapter(this));

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