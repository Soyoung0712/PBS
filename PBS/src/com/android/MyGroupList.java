package com.android;



import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pbs.client.model.TbGroup;
import com.pbs.client.util.UserGson;

public class MyGroupList extends ListActivity {

	// �� �׷� ����Ʈ
	/*
	String[] grouplist = { "�ȵ���̵� 3��", "ȭ���� ������ ��ȣȸ", "��ƺ��3 ��Ƽ��",
			"���꼺���� ȸ�� ������", "OK JSP" };
	String[] groupnotice = {"�ȵ���̵� 3�� ȭ����", "�Ż��Ӵ��� �߰� �Ǿ����ϴ�", "���� 7�ÿ� ���Դϴ�", "���Ի�� �������� �����̽��ϴ�", "�Խ��� ���� ��Ź�帳�ϴ�"};
	*/
	List<TbGroup> tbGroupList = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		UserGson userGson = new UserGson();
		tbGroupList = userGson.getMyGroupList("01077778888");
		
		setContentView(R.layout.mygrouplist);
				

		// ����Ʈ�信 ����Ʈ ����
		setListAdapter(new NewArrayAdapter(this));

		// �׷� �߰� ��ư
		Button addgroup = (Button) findViewById(R.id.button1);
		addgroup.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				showGroupAdd();
			}
		});
	}

	// �׷� �߰� ��ư Ŭ�� �̺�Ʈ
	private void showGroupAdd() {
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout addgrouplayout = (LinearLayout) vi.inflate(
				R.layout.addgroup, null);

		final EditText key = (EditText) addgrouplayout.findViewById(R.id.key);
		final EditText pwd = (EditText) addgrouplayout.findViewById(R.id.pwd);

		new AlertDialog.Builder(this).setTitle("�׷� �߰�").setView(addgrouplayout)
				.setNeutralButton("�߰�", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(
								MyGroupList.this,
								"�׷� KEY : " + key.getText().toString()
										+ "\nPASSWORD : "
										+ pwd.getText().toString(),
								Toast.LENGTH_LONG).show();
					}
				}).show();
	}
	
	class NewArrayAdapter extends ArrayAdapter {
		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.grouprow, tbGroupList);

			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();

			View row = inflater.inflate(R.layout.grouprow, null);

			TextView textView = (TextView) row.findViewById(R.id.name);
			textView.setText(tbGroupList.get(position).getFd_group_name());
			TextView textView2 = (TextView) row.findViewById(R.id.notice);
			textView2.setText(tbGroupList.get(position).getFd_group_notice());

			return row;
		}
	}

}
