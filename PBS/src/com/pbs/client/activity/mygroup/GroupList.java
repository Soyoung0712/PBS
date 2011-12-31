package com.pbs.client.activity.mygroup;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pbs.client.R;
import com.pbs.client.activity.main.WaitDlg;
import com.pbs.client.activity.newgroup.CreateGroup;
import com.pbs.client.model.TbGroup;
import com.pbs.client.util.DeviceManager;
import com.pbs.client.util.UserGson;

/**
 * 내 그룹 리스트
 * 
 * @author Administrator
 * 
 */
public class GroupList extends ListActivity {

	private String myPhoneNum = null;
	private List<TbGroup> tbGroupList = null;
	private NewArrayAdapter newArrayAdapter = null;
	private UserGson userGson = new UserGson();	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// 타이틀바 없애기
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.my_group_list);

		// 내 전화번호 가져오기
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);
				
		// 내그룹 리스트 가져오기		
		
		
		// 내그룹 리스트 가져오기 완료후 프로그래스바 종료
		//WaitDlg.stop(dlg);
		
		

		// 그룹 공유 버튼
		Button addgroup = (Button) findViewById(R.id.bAddGroup);
		addgroup.setPaintFlags(addgroup.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		addgroup.setOnClickListener(new View.OnClickListener()	{
			public void onClick(View v)	{
				showGroupAdd();
			}
		});

		// 그룹 생성 버튼
		Button newGroup = (Button) findViewById(R.id.bCreateGroup);
		newGroup.setPaintFlags(newGroup.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		newGroup.setOnClickListener(new View.OnClickListener()	{
			public void onClick(View arg0)	{
				Intent intent = new Intent(GroupList.this, CreateGroup.class);
				startActivity(intent);
				
			}
		});
		
		// 뒤로가기 버튼
		Button bBack = (Button) findViewById(R.id.bBack);
		bBack.setOnClickListener(new View.OnClickListener()	{
			public void onClick(View arg0)	{
				dialogFinish();				
			}
		});		
		
		// bold 처리
		// 제목
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);		
		tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		
		TextView tvEmplyText1 = (TextView) findViewById(R.id.tvEmplyText1);		
		tvEmplyText1.setPaintFlags(tvEmplyText1.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		
		TextView tvEmplyText2 = (TextView) findViewById(R.id.tvEmplyText2);		
		tvEmplyText2.setPaintFlags(tvEmplyText2.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

		
	}

	// 백버튼 종료 하기
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 팝업을 띄움
			dialogFinish();			
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 종료 확인 다이알로그
	 */
	private void dialogFinish() {
		
		AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle("종료확인")
				.setIcon(R.drawable.icon3)
				.setMessage("종료하시겠습니까?")
				.setPositiveButton("예", new DialogInterface.OnClickListener()	{
					public void onClick(DialogInterface dialog, int which)	{
						finish();
					}})
				.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}})
				.show();		
	}
	
	@Override
	public void onResume() {
		super.onResume();

		// 그룹 리스트 갱신
		WaitDlg dlg = new WaitDlg(GroupList.this, "서버 요청", "그룹 리스트를 불러오고 있습니다");
		tbGroupList = new ArrayList<TbGroup>();
		try {
			dlg.start();
			tbGroupList.addAll(userGson.getMyGroupList(myPhoneNum));			
		} finally {
			dlg.stopLocal(); // 처리중 로딩바 없애기
		}	

		// 리스트뷰에 리스트 적용
		newArrayAdapter = new NewArrayAdapter(this);
		setListAdapter(newArrayAdapter);
		// newArrayAdapter.notifyDataSetChanged();

	}

	// 그룹 공유 버튼 클릭 이벤트   
	private void showGroupAdd()	{
		
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout addgrouplayout = (LinearLayout) vi.inflate(R.layout.dialog_add_group, null);

		final EditText key = (EditText) addgrouplayout.findViewById(R.id.key);
		final EditText pwd = (EditText) addgrouplayout.findViewById(R.id.pwd);

		// "그룹추가" 다이얼로그 생성 //
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
					Toast.makeText(GroupList.this, "그룹키를 잘못 입력하셨습니다", 	Toast.LENGTH_LONG).show();
				}

				// 그룹키 추가
				boolean addGroupResult = userGson.addGroup(groupKey, groupPassword, myPhoneNum);

				// 그룹키 추가 성공
				if (addGroupResult) {
					
					WaitDlg dlg = new WaitDlg(GroupList.this, "서버 요청", "그룹을 추가하고 있습니다");
					dlg.start();
					
					tbGroupList.clear();
					tbGroupList.addAll(userGson.getMyGroupList(myPhoneNum));
					newArrayAdapter.notifyDataSetChanged();
					
					dlg.stopLocal();
					
					Toast.makeText(GroupList.this, "그룹이 추가 되었습니다.", Toast.LENGTH_LONG).show();

				// 그룹키 추가 실패
				}else {			 					
					Toast.makeText(GroupList.this, "잘못된 그룹입니다.", Toast.LENGTH_LONG).show();
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
			super(context, R.layout.my_group_list_row, tbGroupList);
			this.context = context;
		}

		/**
		 * 리스트를 한 row씩 출력해준다
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			
			final int pos = position;
			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.my_group_list_row, null);

			// 현재 출력중인 Group 정보
			final TbGroup curTbGroup = tbGroupList.get(position);
			
			if("Y".equals(curTbGroup.getFd_admin_yn()) ) {				
				Drawable apple_tree = (Drawable) getResources().getDrawable(R.drawable.apple_tree);
				ImageView ivAdminCheck = (ImageView) row.findViewById(R.id.ivAdminCheck);
				ivAdminCheck.setImageDrawable(apple_tree);				
			}else {
				Drawable apple_box = (Drawable) getResources().getDrawable(R.drawable.apple_box);
				ImageView ivAdminCheck = (ImageView) row.findViewById(R.id.ivAdminCheck);
				ivAdminCheck.setImageDrawable(apple_box);				
			}

			// 그룹명
			TextView textView = (TextView) row.findViewById(R.id.groupName);
			textView.setText(curTbGroup.getFd_group_name());
			textView.setPaintFlags(textView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

			// 그룹 공지사항
			TextView textView2 = (TextView) row.findViewById(R.id.groupNotice);
			textView2.setText(curTbGroup.getFd_group_notice());
			textView2.setPaintFlags(textView2.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
			
			return row;
		}

	}

	/**
	 * 리스트에서 "그룹" 클릭
	 */
	public void onListItemClick(ListView parent, View v, int position, long id) {		
		Intent intent = new Intent(GroupList.this, MemberList.class);
		intent.putExtra("pk_group", tbGroupList.get(position).getPk_group());		
		intent.putExtra("fd_admin_yn", tbGroupList.get(position).getFd_admin_yn());		
		intent.putExtra("fd_group_name", tbGroupList.get(position).getFd_group_name());		
		startActivity(intent);		
	}	

}
