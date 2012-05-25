package com.android;



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

public class MyGroupList extends ListActivity {

	// 내 그룹 리스트
	String[] grouplist = { "안드로이드 3기", "화이팅 자전거 동호회", "디아블로3 파티팀",
			"생산성본부 회사 관리팀", "OK JSP" };
	String[] groupnotice = {"안드로이드 3기 화이팅", "신사임당이 추가 되었습니다", "저녁 7시에 모입니다", "신입사원 강희우님이 들어오셨습니다", "게시판 정리 부탁드립니다"};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mygrouplist);

		// 리스트뷰에 리스트 적용
		setListAdapter(new NewArrayAdapter(this));

		// 그룹 추가 버튼
		Button addgroup = (Button) findViewById(R.id.button1);
		addgroup.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				showGroupAdd();
			}
		});
	}

	// 그룹 추가 버튼 클릭 이벤트
	private void showGroupAdd() {
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout addgrouplayout = (LinearLayout) vi.inflate(
				R.layout.addgroup, null);

		final EditText key = (EditText) addgrouplayout.findViewById(R.id.key);
		final EditText pwd = (EditText) addgrouplayout.findViewById(R.id.pwd);

		new AlertDialog.Builder(this).setTitle("그룹 추가").setView(addgrouplayout)
				.setNeutralButton("추가", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(
								MyGroupList.this,
								"그룹 KEY : " + key.getText().toString()
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
			super(context, R.layout.grouprow, grouplist);

			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();

			View row = inflater.inflate(R.layout.grouprow, null);

			TextView textView = (TextView) row.findViewById(R.id.name);
			textView.setText(grouplist[position]);
			TextView textView2 = (TextView) row.findViewById(R.id.notice);
			textView2.setText(groupnotice[position]);

			return row;
		}
	}

}
