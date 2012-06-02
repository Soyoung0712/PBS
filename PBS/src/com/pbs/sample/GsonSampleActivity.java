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
		
		
		// 그룹원 리스트 가져오기		
		List<TbMember> tbMemberList = userGson.getMemeberList(7L, "01082052802" );
		for( int i=0 ; i<tbMemberList.size(); i++ ) {
			TbMember tbMember = tbMemberList.get(i);
			
			// 그룹원 이름
			Log.d("[" + i + "]번째 그룹원 이름: ", tbMember.getFd_member_name());
			Log.d("[" + i + "]번째 그룹원 이름: ", tbMember.getFd_member_phone());
		}		
		
		/*
		// 그룹 관리자 리스트 가져오기
		List<TbAccessUser> tbAccessUserList = userGson.getAdminList(1L, "01082052802" );
		for (TbAccessUser tbAccessUser : tbAccessUserList) {			
			Log.d("TbAccessUserList", tbAccessUser.toString());			
		}
		
		//그룹 상세정보 가져오기
		TbGroup tbGroupInfo = userGson.getGroupInfo(6L, "01082052802" );
		Log.d("tbGroupInfo", tbGroupInfo.toString());		
		
		//내 그룹 리스트 가져오기
		List<TbGroup> tbGroupList = userGson.getMyGroupList("01077778888" );
		for (TbGroup tbGroup : tbGroupList) {
			Log.d("TbAccessUserList", tbGroup.toString());			
		}	
		*/
		
		/*
		// 한글 사용시 Tomcat에 Server.xml에 UTF-8인코딩 옵션을 준다
		// 그룹 생성하기
		String groupName = "한글 그룹999";
		String groupPassword = "1234";
		String groupNotice = "한글Group Success999"; 
		String groupCreatorPhone = "01022225555"; 
		String[] users = {"강희우1:010123456","강희우2:01033335555","강희우3:01077778888","강희우4:01099995555"};
		String[] admins = {"송영석1:01077778888","송영석2:01099995555"};
		
		TbGroup createTbGroup = userGson.createGroup(groupName, groupPassword, groupNotice, groupCreatorPhone, users, admins);
		if( createTbGroup != null ) {
			Log.d("createTbGroup", createTbGroup.toString());
		}else {
			Log.d("createTbGroup", "Create Fail");
		}
		*/
		
		// 그룹 추가
		//http://14.63.223.82:8080/pbs/accessUser/insertByPwd.json?pk_group=5&fd_group_password=1234&fd_access_phone=01082052802
		boolean addGroupResult = userGson.addGroup(5L, "1234", "01082052802" );
		if( addGroupResult ) {
			Log.d("addGroupResult", "그룹 추가 성공");
		}else {
			Log.d("addGroupResult", "그룹 추가 실패");
		}
		
		// 그룹 감추기
		boolean hiddenGroupResult = userGson.hiddenGroup(5L, "01082052802" );
		if( hiddenGroupResult ) {
			Log.d("hiddenGroupResult", "그룹 감추기 성공");
		}else {
			Log.d("hiddenGroupResult", "그룹 감추기 실패");		
		}				
		
		
	}
	
}