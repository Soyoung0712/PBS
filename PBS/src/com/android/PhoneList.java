package com.android;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PhoneList extends ListActivity {

	// �� �׷� ����Ʈ
	String[] name = { "ȫ�浿1", "ȫ�浿2", "ȫ�浿3", "ȫ�浿4", "ȫ�浿5", "ȫ�浿6", "ȫ�浿7",
			"ȫ�浿8", "ȫ�浿9" };
	String[] number = { "016-1234-1234", "010-1234-1234", "010-1234-1234",
								"010-1234-1234", "010-1234-1234", "010-1234-1234", "010-1234-1234",
								"010-1234-1234",  "010-1234-1235" };
 
    
	private NewArrayAdapter newArrayAdapter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phonelist);

		// ����Ʈ�信 ����Ʈ ����
		 
		newArrayAdapter = new NewArrayAdapter(PhoneList.this);
		setListAdapter(newArrayAdapter);
		
		 
		
	 
		
	}

 
 
		class NewArrayAdapter extends ArrayAdapter {
			Activity context;

			@SuppressWarnings("unchecked")
			NewArrayAdapter(Activity context) {
				super(context, R.layout.phonelistrow, name);
				this.context = context;			
			}

	  
 
		public View getView(int position, final View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			  
			View row = inflater.inflate(R.layout.phonelistrow, null);

			final TextView mName = (TextView) row.findViewById(R.id.name);
			mName.setText(name[position]);
			final TextView mPhone =(TextView) row.findViewById(R.id.number);
			mPhone.setText(number[position]);
			
			Button mDelete =(Button)row.findViewById(R.id.delete);
			
			
			
			// ������ư ������ �����ϱ�
			mDelete.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View arg0)
				{
				 
				}
			});
			 
			 
			
			return row;
		}
	}
}