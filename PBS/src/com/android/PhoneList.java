package com.android;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PhoneList extends ListActivity {

	// ³» ±×·ì ¸®½ºÆ®
	String[] name = { "È«±æµ¿1", "È«±æµ¿2", "È«±æµ¿3", "È«±æµ¿4", "È«±æµ¿5", "È«±æµ¿6", "È«±æµ¿7",
			"È«±æµ¿8", "È«±æµ¿9" };
	String[] number = { "016-1234-1234", "010-1234-1234", "010-1234-1234",
			"010-1234-1234", "010-1234-1234", "010-1234-1234", "010-1234-1234",
			"010-1234-1234", "010-1234-1234", "010-1234-1235", };
 
	 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phonelist);

		// ¸®½ºÆ®ºä¿¡ ¸®½ºÆ® Àû¿ë
		 
		setListAdapter(new NewArrayAdapter(PhoneList.this));
		
		
		
	}

 
 
		class NewArrayAdapter extends ArrayAdapter {
			Activity context;

			@SuppressWarnings("unchecked")
			NewArrayAdapter(Activity context) {
				super(context, R.layout.phonelistrow, name);
				this.context = context;			
			}

	  
 
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();

			View row = inflater.inflate(R.layout.phonelistrow, null);

			TextView mName = (TextView) row.findViewById(R.id.name);
			mName.setText(name[position]);
			
			TextView mPhone =(TextView) row.findViewById(R.id.phone);
			mName.setText(number[position]);
			
			Button mDelete =(Button) row.findViewById(R.id.delete);
			 
			
			return row;
		}
	}
}