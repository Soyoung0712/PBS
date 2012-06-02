package com.pbs.client.activity.edit;

import java.util.List;

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

import com.android.R;
import com.pbs.client.activity.newgroup.CreateGroup;
import com.pbs.client.model.TbMember;
import com.pbs.client.util.UserGson;

public class EditMember extends ListActivity {

	private String myPhoneNum = "01077778888";
	private List<TbMember> tbMemberList = null;
	private NewArrayAdapter newArrayAdapter = null;
	private UserGson userGson = new UserGson();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editmemberlist);

		// 선택한 그룹의 맴버 리스트 가져오기
		tbMemberList = userGson.getMemeberList(5L, myPhoneNum);

		// 리스트뷰에 리스트 적용
		newArrayAdapter = new NewArrayAdapter(this);
		setListAdapter(newArrayAdapter);

		// 편집완료 버튼 클릭 했을때
		Button complete = (Button) findViewById(R.id.complete);
		complete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(EditMember.this, "편집 완료", Toast.LENGTH_SHORT)
						.show();
				finish();
				Intent intent = new Intent(EditMember.this, CreateGroup.class);
				startActivity(intent);
			}
		});
	}

	class NewArrayAdapter extends ArrayAdapter {

		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.editmemberrow, tbMemberList);

			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// 멤버 정보
			TbMember tbMember = tbMemberList.get(position);

			LayoutInflater inflater = context.getLayoutInflater();

			View row = inflater.inflate(R.layout.editmemberrow, null);

			EditText edit1 = (EditText) row.findViewById(R.id.name);
			edit1.setText(tbMember.getFd_member_name());
			EditText edit2 = (EditText) row.findViewById(R.id.number);
			edit2.setText(tbMember.getFd_member_phone());

			return row;
		}
	}

}
