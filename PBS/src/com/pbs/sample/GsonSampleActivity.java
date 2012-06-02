package com.pbs.sample;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.R;
import com.android.R.id;
import com.android.R.layout;
import com.pbs.client.model.TbAccessUser;
import com.pbs.client.model.TbGroup;
import com.pbs.client.model.TbMember;
import com.pbs.client.util.UserGson;

public class GsonSampleActivity extends Activity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {			
		
		Log.d("GsonSampleActivity", "Call onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample_gson);
		
		((TextView)findViewById(R.id.gsonSample)).setText("hello");
		
		UserGson userGson = new UserGson();
		
		
		// �׷�� ����Ʈ ��������		
		List<TbMember> tbMemberList = userGson.getMemeberList(7L, "01082052802" );
		for( int i=0 ; i<tbMemberList.size(); i++ ) {
			TbMember tbMember = tbMemberList.get(i);
			
			// �׷�� �̸�
			Log.d("[" + i + "]��° �׷�� �̸�: ", tbMember.getFd_member_name());
			Log.d("[" + i + "]��° �׷�� �̸�: ", tbMember.getFd_member_phone());
		}		
		
		/*
		// �׷� ������ ����Ʈ ��������
		List<TbAccessUser> tbAccessUserList = userGson.getAdminList(1L, "01082052802" );
		for (TbAccessUser tbAccessUser : tbAccessUserList) {			
			Log.d("TbAccessUserList", tbAccessUser.toString());			
		}
		
		//�׷� ������ ��������
		TbGroup tbGroupInfo = userGson.getGroupInfo(6L, "01082052802" );
		Log.d("tbGroupInfo", tbGroupInfo.toString());		
		
		//�� �׷� ����Ʈ ��������
		List<TbGroup> tbGroupList = userGson.getMyGroupList("01077778888" );
		for (TbGroup tbGroup : tbGroupList) {
			Log.d("TbAccessUserList", tbGroup.toString());			
		}	
		*/
		
		/*
		// �ѱ� ���� Tomcat�� Server.xml�� UTF-8���ڵ� �ɼ��� �ش�
		// �׷� �����ϱ�
		String groupName = "�ѱ� �׷�999";
		String groupPassword = "1234";
		String groupNotice = "�ѱ�Group Success999"; 
		String groupCreatorPhone = "01022225555"; 
		String[] users = {"�����1:010123456","�����2:01033335555","�����3:01077778888","�����4:01099995555"};
		String[] admins = {"�ۿ���1:01077778888","�ۿ���2:01099995555"};
		
		TbGroup createTbGroup = userGson.createGroup(groupName, groupPassword, groupNotice, groupCreatorPhone, users, admins);
		if( createTbGroup != null ) {
			Log.d("createTbGroup", createTbGroup.toString());
		}else {
			Log.d("createTbGroup", "Create Fail");
		}
		*/
		
		// �׷� �߰�
		//http://14.63.223.82:8080/pbs/accessUser/insertByPwd.json?pk_group=5&fd_group_password=1234&fd_access_phone=01082052802
		boolean addGroupResult = userGson.addGroup(5L, "1234", "01082052802" );
		if( addGroupResult ) {
			Log.d("addGroupResult", "�׷� �߰� ����");
		}else {
			Log.d("addGroupResult", "�׷� �߰� ����");
		}
		
		// �׷� ���߱�
		boolean hiddenGroupResult = userGson.hiddenGroup(5L, "01082052802" );
		if( hiddenGroupResult ) {
			Log.d("hiddenGroupResult", "�׷� ���߱� ����");
		}else {
			Log.d("hiddenGroupResult", "�׷� ���߱� ����");		
		}				
		
		
	}
	
}