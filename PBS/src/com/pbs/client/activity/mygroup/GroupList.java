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
 * �� �׷� ����Ʈ
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

		// Ÿ��Ʋ�� ���ֱ�
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.my_group_list);

		// �� ��ȭ��ȣ ��������
		myPhoneNum = DeviceManager.getMyPhoneNumber(this);
				
		// ���׷� ����Ʈ ��������		
		
		
		// ���׷� ����Ʈ �������� �Ϸ��� ���α׷����� ����
		//WaitDlg.stop(dlg);
		
		

		// �׷� ���� ��ư
		Button addgroup = (Button) findViewById(R.id.bAddGroup);
		addgroup.setPaintFlags(addgroup.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		addgroup.setOnClickListener(new View.OnClickListener()	{
			public void onClick(View v)	{
				showGroupAdd();
			}
		});

		// �׷� ���� ��ư
		Button newGroup = (Button) findViewById(R.id.bCreateGroup);
		newGroup.setPaintFlags(newGroup.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		newGroup.setOnClickListener(new View.OnClickListener()	{
			public void onClick(View arg0)	{
				Intent intent = new Intent(GroupList.this, CreateGroup.class);
				startActivity(intent);
				
			}
		});
		
		// �ڷΰ��� ��ư
		Button bBack = (Button) findViewById(R.id.bBack);
		bBack.setOnClickListener(new View.OnClickListener()	{
			public void onClick(View arg0)	{
				dialogFinish();				
			}
		});		
		
		// bold ó��
		// ����
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);		
		tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		
		TextView tvEmplyText1 = (TextView) findViewById(R.id.tvEmplyText1);		
		tvEmplyText1.setPaintFlags(tvEmplyText1.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		
		TextView tvEmplyText2 = (TextView) findViewById(R.id.tvEmplyText2);		
		tvEmplyText2.setPaintFlags(tvEmplyText2.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

		
	}

	// ���ư ���� �ϱ�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// �˾��� ���
			dialogFinish();			
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * ���� Ȯ�� ���̾˷α�
	 */
	private void dialogFinish() {
		
		AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle("����Ȯ��")
				.setIcon(R.drawable.icon3)
				.setMessage("�����Ͻðڽ��ϱ�?")
				.setPositiveButton("��", new DialogInterface.OnClickListener()	{
					public void onClick(DialogInterface dialog, int which)	{
						finish();
					}})
				.setNegativeButton("�ƴϿ�", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}})
				.show();		
	}
	
	@Override
	public void onResume() {
		super.onResume();

		// �׷� ����Ʈ ����
		WaitDlg dlg = new WaitDlg(GroupList.this, "���� ��û", "�׷� ����Ʈ�� �ҷ����� �ֽ��ϴ�");
		tbGroupList = new ArrayList<TbGroup>();
		try {
			dlg.start();
			tbGroupList.addAll(userGson.getMyGroupList(myPhoneNum));			
		} finally {
			dlg.stopLocal(); // ó���� �ε��� ���ֱ�
		}	

		// ����Ʈ�信 ����Ʈ ����
		newArrayAdapter = new NewArrayAdapter(this);
		setListAdapter(newArrayAdapter);
		// newArrayAdapter.notifyDataSetChanged();

	}

	// �׷� ���� ��ư Ŭ�� �̺�Ʈ   
	private void showGroupAdd()	{
		
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout addgrouplayout = (LinearLayout) vi.inflate(R.layout.dialog_add_group, null);

		final EditText key = (EditText) addgrouplayout.findViewById(R.id.key);
		final EditText pwd = (EditText) addgrouplayout.findViewById(R.id.pwd);

		// "�׷��߰�" ���̾�α� ���� //
		Builder builder = new AlertDialog.Builder(this).setTitle("�׷� �߰�").setView(addgrouplayout);

		// "�߰�" ��ư ������ ����
		DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {

			// ���̾� �˾��� "�߰�" ��ū Ŭ���� �̺�Ʈ
			public void onClick(DialogInterface dialog, int which) {

				// �׷�Ű, �н�����, ������ȣ
				Long groupKey = 0L;
				String groupPassword = pwd.getText().toString();
				String accessPhone = myPhoneNum;

				// �׷�Ű Long������ ��ȯ
				try {
					groupKey = Long.parseLong(key.getText().toString());
				} catch (Exception e) {
					Log.d("MyGroupList", "groupKey Input Error [groupKey]:" + groupKey);
					Toast.makeText(GroupList.this, "�׷�Ű�� �߸� �Է��ϼ̽��ϴ�", 	Toast.LENGTH_LONG).show();
				}

				// �׷�Ű �߰�
				boolean addGroupResult = userGson.addGroup(groupKey, groupPassword, myPhoneNum);

				// �׷�Ű �߰� ����
				if (addGroupResult) {
					
					WaitDlg dlg = new WaitDlg(GroupList.this, "���� ��û", "�׷��� �߰��ϰ� �ֽ��ϴ�");
					dlg.start();
					
					tbGroupList.clear();
					tbGroupList.addAll(userGson.getMyGroupList(myPhoneNum));
					newArrayAdapter.notifyDataSetChanged();
					
					dlg.stopLocal();
					
					Toast.makeText(GroupList.this, "�׷��� �߰� �Ǿ����ϴ�.", Toast.LENGTH_LONG).show();

				// �׷�Ű �߰� ����
				}else {			 					
					Toast.makeText(GroupList.this, "�߸��� �׷��Դϴ�.", Toast.LENGTH_LONG).show();
				}

			}

		};

		// ���̾�α� SHOW
		builder.setNeutralButton("�߰�", onClickListener).show();

	}

	class NewArrayAdapter extends ArrayAdapter {

		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.my_group_list_row, tbGroupList);
			this.context = context;
		}

		/**
		 * ����Ʈ�� �� row�� ������ش�
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			
			final int pos = position;
			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.my_group_list_row, null);

			// ���� ������� Group ����
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

			// �׷��
			TextView textView = (TextView) row.findViewById(R.id.groupName);
			textView.setText(curTbGroup.getFd_group_name());
			textView.setPaintFlags(textView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

			// �׷� ��������
			TextView textView2 = (TextView) row.findViewById(R.id.groupNotice);
			textView2.setText(curTbGroup.getFd_group_notice());
			textView2.setPaintFlags(textView2.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
			
			return row;
		}

	}

	/**
	 * ����Ʈ���� "�׷�" Ŭ��
	 */
	public void onListItemClick(ListView parent, View v, int position, long id) {		
		Intent intent = new Intent(GroupList.this, MemberList.class);
		intent.putExtra("pk_group", tbGroupList.get(position).getPk_group());		
		intent.putExtra("fd_admin_yn", tbGroupList.get(position).getFd_admin_yn());		
		intent.putExtra("fd_group_name", tbGroupList.get(position).getFd_group_name());		
		startActivity(intent);		
	}	

}
