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
 * �� �׷� ����Ʈ
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
		
		// ���׷� ����Ʈ ��������		
		tbGroupList = userGson.getMyGroupList(myPhoneNum);
				
		// ����Ʈ�信 ����Ʈ ����
		newArrayAdapter = new NewArrayAdapter(this);
		setListAdapter(newArrayAdapter);
		//newArrayAdapter.notifyDataSetChanged();
		
		// �׷� �߰� ��ư		
		Button addgroup = (Button) findViewById(R.id.button1);
		addgroup.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				showGroupAdd();
			}
		});		

	}

	// �׷� �߰� ��ư Ŭ�� �̺�Ʈ	
	private void showGroupAdd() {
		
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout addgrouplayout = (LinearLayout) vi.inflate(R.layout.addgroup, null);

		final EditText key = (EditText) addgrouplayout.findViewById(R.id.key);
		final EditText pwd = (EditText) addgrouplayout.findViewById(R.id.pwd);		
		
		// "�׷��߰�" ���̾�α� ����
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
					Toast.makeText(MyGroupList.this, "�׷�Ű�� �߸� �Է��ϼ̽��ϴ�",Toast.LENGTH_LONG).show();
				}				
			
				// �׷�Ű �߰�
				boolean addGroupResult = userGson.addGroup(groupKey, groupPassword, myPhoneNum );
				
				// �׷�Ű �߰� ����
				if( addGroupResult ) {										
					tbGroupList.clear();
					tbGroupList.addAll(userGson.getMyGroupList(myPhoneNum));					
					newArrayAdapter.notifyDataSetChanged();
					Toast.makeText(MyGroupList.this, "�׷��� �߰� �Ǿ����ϴ�.",Toast.LENGTH_LONG).show();
					
				// �׷�Ű �߰� ����
				}else {					
					Toast.makeText(MyGroupList.this, "�߸��� �׷��Դϴ�.",Toast.LENGTH_LONG).show();
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
			super(context, R.layout.grouprow, tbGroupList);
			this.context = context;			
		}

		/**
		 * ����Ʈ�� �� row�� ������ش�
		 */
		public View getView(int position, View convertView, ViewGroup parent) {			
			
			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.grouprow, null);
			
			// ���� ������� Group ����
			final TbGroup curTbGroup = tbGroupList.get(position);
			
			// �׷��
			TextView textView = (TextView) row.findViewById(R.id.groupName);
			textView.setText(tbGroupList.get(position).getFd_group_name());
			
			// �׷� ��������
			TextView textView2 = (TextView) row.findViewById(R.id.groupNotice);
			textView2.setText(tbGroupList.get(position).getFd_group_notice());			
			
			// "����" ��ư
			Button mGroupSetting = (Button) row.findViewById(R.id.groupSetting);
			mGroupSetting.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(MyGroupList.this, GroupModify.class);
					startActivity(intent);
				}

			});
						
			//  "����" ��ư 
			Button mGroupHidden = (Button) row.findViewById(R.id.groupHidden);
			mGroupHidden.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					
					// �׷� ���߱�
					boolean hiddenGroupResult = userGson.hiddenGroup(curTbGroup.getPk_group(), myPhoneNum );
					
					// �׷� ����Ʈ Refresh
					if( hiddenGroupResult ) {					
						tbGroupList.clear();
						tbGroupList.addAll(userGson.getMyGroupList(myPhoneNum));					
						newArrayAdapter.notifyDataSetChanged();						
						Toast.makeText(MyGroupList.this, "�׷��� ���� �Ǿ����ϴ�.",Toast.LENGTH_LONG).show();
					}
										
				}				
			});			
			
			// (�����ڴ� "����", �Ϲݻ���ڴ� "����" ��ư�� ����)
			if( !curTbGroup.getFd_admin_yn().equals("Y") ) {
				mGroupSetting.setVisibility(View.INVISIBLE);
			}else{
				mGroupHidden.setVisibility(View.INVISIBLE);
			}

			return row;
		}

	}
	
	/**
	 * ����Ʈ���� "�׷�" Ŭ��
	 */
	public void onListItemClick(ListView parent, View v, int position, long id) {								
		Intent intent = new Intent(MyGroupList.this, MemberList.class);		
		intent.putExtra("pk_group", tbGroupList.get(position).getPk_group());
		intent.putExtra("fd_group_name", tbGroupList.get(position).getFd_group_name());
		startActivity(intent);		
    }


}
