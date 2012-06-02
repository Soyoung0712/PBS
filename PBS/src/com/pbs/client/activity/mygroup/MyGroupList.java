package com.pbs.client.activity.mygroup;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.R;
import com.android.R.id;
import com.android.R.layout;
import com.pbs.client.model.TbGroup;
import com.pbs.client.util.UserGson;

/**
 * 내 그룹 리스트
 * @author Administrator
 *
 */
public class MyGroupList extends ListActivity {

	private String myPhoneNum = "01077778888";
	private List<TbGroup> tbGroupList = null;
	private NewArrayAdapter newArrayAdapter = null;
	private UserGson userGson = new UserGson();	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mygrouplist);
		
		// 내그룹 리스트 가져오기		
		tbGroupList = userGson.getMyGroupList(myPhoneNum);
				
		// 리스트뷰에 리스트 적용
		newArrayAdapter = new NewArrayAdapter(this);
		setListAdapter(newArrayAdapter);
		//newArrayAdapter.notifyDataSetChanged();
		
		// 그룹 추가 버튼		
		Button addgroup = (Button) findViewById(R.id.button1);
		addgroup.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				showGroupAdd();
			}
		});		

	}

	// 그룹 추가 버튼 클릭 이벤트	
	private void showGroupAdd() {
		
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout addgrouplayout = (LinearLayout) vi.inflate(R.layout.addgroup, null);

		final EditText key = (EditText) addgrouplayout.findViewById(R.id.key);
		final EditText pwd = (EditText) addgrouplayout.findViewById(R.id.pwd);		
		
		// "그룹추가" 다이얼로그 생성
		Builder builder = new AlertDialog.Builder(this).setTitle("그룹 추가").setView(addgrouplayout);
		
		// "추가" 버튼 리슨너 생성		
		DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {

			// 레이어 팝업의 "추가" 버큰 클릭시 이벤트
			public void onClick(DialogInterface dialog, int which) {					
				
				// 그룹키, 패스워드, 내폰번호
				Long groupKey = 0L;
				String groupPassword = pwd.getText().toString();
				String accessPhone = myPhoneNum;			
				
				// 그룹키 Long형으로 변환
				try {
					groupKey = Long.parseLong(key.getText().toString());
				} catch (Exception e) {
					Log.d("MyGroupList", "groupKey Input Error [groupKey]:" + groupKey);
					Toast.makeText(MyGroupList.this, "그룹키를 잘못 입력하셨습니다",Toast.LENGTH_LONG).show();
				}				
			
				// 그룹키 추가
				boolean addGroupResult = userGson.addGroup(groupKey, groupPassword, myPhoneNum );
				
				// 그룹키 추가 성공
				if( addGroupResult ) {										
					tbGroupList.clear();
					tbGroupList.addAll(userGson.getMyGroupList(myPhoneNum));					
					newArrayAdapter.notifyDataSetChanged();
					Toast.makeText(MyGroupList.this, "그룹이 추가 되었습니다.",Toast.LENGTH_LONG).show();
					
				// 그룹키 추가 실패
				}else {					
					Toast.makeText(MyGroupList.this, "잘못된 그룹입니다.",Toast.LENGTH_LONG).show();
				}				
								
			}
			
		};
				
		// 다이얼로그 SHOW
		builder.setNeutralButton("추가", onClickListener).show();
		
	}
	
	
	class NewArrayAdapter extends ArrayAdapter {
		
		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.grouprow, tbGroupList);
			this.context = context;			
		}

		/**
		 * 리스트를 한 row씩 출력해준다
		 */
		public View getView(int position, View convertView, ViewGroup parent) {			
			
			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.grouprow, null);
			
			// 현재 출력중인 Group 정보
			final TbGroup curTbGroup = tbGroupList.get(position);
			
			// 그룹명
			TextView textView = (TextView) row.findViewById(R.id.groupName);
			textView.setText(tbGroupList.get(position).getFd_group_name());
			
			// 그룹 공지사항
			TextView textView2 = (TextView) row.findViewById(R.id.groupNotice);
			textView2.setText(tbGroupList.get(position).getFd_group_notice());			
			
			// "설정" 버튼
			Button mGroupSetting = (Button) row.findViewById(R.id.groupSetting);
			mGroupSetting.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(MyGroupList.this, GroupModify.class);
					startActivity(intent);
				}

			});
						
			//  "삭제" 버튼 
			Button mGroupHidden = (Button) row.findViewById(R.id.groupHidden);
			mGroupHidden.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					
					// 그룹 감추기
					boolean hiddenGroupResult = userGson.hiddenGroup(curTbGroup.getPk_group(), myPhoneNum );
					
					// 그룹 리스트 Refresh
					if( hiddenGroupResult ) {					
						tbGroupList.clear();
						tbGroupList.addAll(userGson.getMyGroupList(myPhoneNum));					
						newArrayAdapter.notifyDataSetChanged();						
						Toast.makeText(MyGroupList.this, "그룹이 삭제 되었습니다.",Toast.LENGTH_LONG).show();
					}
										
				}				
			});			
			
			// (관리자는 "설정", 일반사용자는 "삭제" 버튼이 노출)
			if( !curTbGroup.getFd_admin_yn().equals("Y") ) {
				mGroupSetting.setVisibility(View.INVISIBLE);
			}else{
				mGroupHidden.setVisibility(View.INVISIBLE);
			}

			return row;
		}

	}
	
	/**
	 * 리스트에서 "그룹" 클릭
	 */
	public void onListItemClick(ListView parent, View v, int position, long id) {								
		Intent intent = new Intent(MyGroupList.this, MemberList.class);		
		intent.putExtra("pk_group", tbGroupList.get(position).getPk_group());
		intent.putExtra("fd_group_name", tbGroupList.get(position).getFd_group_name());
		startActivity(intent);		
    }


}
