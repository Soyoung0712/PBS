package com.pbs.client.util;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.pbs.client.model.BoardResult;
import com.pbs.client.model.CommonResult;
import com.pbs.client.model.TbAccessUser;
import com.pbs.client.model.TbGroup;
import com.pbs.client.model.TbMember;

public class UserGson {	
	
	private String pbsDomain = "http://14.63.223.82:8080/pbs";
	
	private String urlMyGroupList = pbsDomain + "/accessUser/listGroup.json"	;
	private String urlGroupAdd    = pbsDomain + "/accessUser/insertByPwd.json"	;
	private String urlGroupHidden = pbsDomain + "/accessUser/delete.json"		;
	private String urlGroupCreate = pbsDomain + "/group/insert.json"			;
	private String urlGroupInfo   = pbsDomain + "/group/viewByPhone.json"		;
	private String urlAdminList   = pbsDomain + "/accessUser/list.json"			;
	private String urlGroupDelete = pbsDomain + "/group/delete.json"			;
	private String urlGroupUpdate = pbsDomain + "/group/update.json"			;
	private String urlMemeberList = pbsDomain + "/member/list.json"				;
	
	
	/**
	 * 내그룹 리스트 가져오기
	 * @param groupKey : 그룹 키
	 * @param accessPhoneNumber : 그룹에 접근 가능한 전화번호
	 */
	public List<TbGroup> getMyGroupList(String accessPhone) {
				
		List<TbGroup> tbGroupList = null;
		
		// 인자값 체크
		if( accessPhone != null && accessPhone.length() > 0) {
						
			// 전화번호에 "-" 제거
			accessPhone = accessPhone.replaceAll("-", "");
			
			// 웹서버에서 그룹 리스트를 Json형식으로 가져온다.
			String url = urlMyGroupList + "?&fd_access_phone=" + accessPhone;									
			try {				
				
				JSONObject item = new JSONObject(getStringFromUrl(url));
				JSONArray jsonArray = item.getJSONArray("list");
				
				// 그룹 데이타가 있으면 ArrayList를 생성
				if( jsonArray != null && jsonArray.length() > 0 ) {
					tbGroupList = new ArrayList<TbGroup>();
				}
				
				// 그룹 데이타 저장
				for( int i=0; i<jsonArray.length(); i++ ) {					
					long   pk_group 		= jsonArray.getJSONObject(i).getLong("pk_group");					 
					String fd_group_name 	= jsonArray.getJSONObject(i).getString("fd_group_name"  );
					String fd_group_notice 	= jsonArray.getJSONObject(i).getString("fd_group_notice");
					String fd_admin_yn 		= jsonArray.getJSONObject(i).getString("fd_admin_yn"      );
					
					TbGroup tbGroup = new TbGroup();
					tbGroup.setPk_group(pk_group);
					tbGroup.setFd_group_name(fd_group_name);
					tbGroup.setFd_group_notice(fd_group_notice);
					tbGroup.setFd_admin_yn(fd_admin_yn);
					tbGroupList.add(tbGroup);				
				}				
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
		}
		
		return tbGroupList;
		
	}
	
	
	/**
	 * 그룹 상세정보 가져오기
	 * @param groupKey : 그룹 키
	 * @param adminPhone : 해당 그룹의 관리자 전화번호
	 * @return
	 */
	public TbGroup getGroupInfo(long groupKey, String adminPhone) {
		
		TbGroup tbGroup = null;
		
		// 인자값 체크
		if( groupKey > 0L && adminPhone != null && adminPhone.length() > 0) {
						
			// 전화번호에 "-" 제거
			adminPhone = adminPhone.replaceAll("-", "");
			
			// 웹서버에서 그룹정보를 Json형식으로 가져온다.
			String url = urlGroupInfo + "?pk_group="+ groupKey +"&fd_access_phone=" + adminPhone;									
			try {				
				
				JSONObject item = new JSONObject(getStringFromUrl(url));
				JSONObject groupJson = item.getJSONObject("view");
				
				// 그룹 상세정보 저장
				if( item != null ) {					
					tbGroup = new TbGroup();					
					long pk_group 		 	 = groupJson.getLong("pk_group");
					String fd_group_name     = groupJson.getString("fd_group_name");					
					String fd_group_notice   = groupJson.getString("fd_group_notice");					
					String fd_group_creator  = groupJson.getString("fd_group_creator");					
					tbGroup.setPk_group(pk_group);
					tbGroup.setFd_group_name(fd_group_name);					
					tbGroup.setFd_group_notice(fd_group_notice);
					tbGroup.setFd_group_creator(fd_group_creator);					
				}								
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
		}
		
		return tbGroup;
	}
	
	/**
	 * 관리자 번호 리스트 가져오기
	 * @param groupKey : 그룹 키
	 * @param adminPhone : 해당 그룹의 관리자 전화번호
	 * @return
	 */
	public List<TbAccessUser> getAdminList(long groupKey, String adminPhone) {
		
		List<TbAccessUser> tbAccessUserList = null;
		
		// 인자값 체크
		if( groupKey > 0L && adminPhone != null && adminPhone.length() > 0) {
						
			// 전화번호에 "-" 제거
			adminPhone = adminPhone.replaceAll("-", "");
			
			// 웹서버에서 관리자 리스트를 Json형식으로 가져온다.
			String url = urlAdminList + "?fk_group="+ groupKey +"&fd_access_phone=" + adminPhone;									
			try {				
				
				JSONObject item = new JSONObject(getStringFromUrl(url));
				JSONArray jsonArray = item.getJSONArray("list");
				
				// 관리자 데이타가 있으면 ArrayList를 생성
				if( jsonArray != null && jsonArray.length() > 0 ) {
					tbAccessUserList = new ArrayList<TbAccessUser>();
				}
				
				// 관리자 데이타 저장
				for( int i=0; i<jsonArray.length(); i++ ) {					
					long   fk_group 		= jsonArray.getJSONObject(i).getLong("fk_group");					 
					String fd_access_phone 	= jsonArray.getJSONObject(i).getString("fd_access_phone");					
					TbAccessUser tbAccessUser = new TbAccessUser();
					tbAccessUser.setFk_group(fk_group);
					tbAccessUser.setFd_access_phone(fd_access_phone);
					tbAccessUserList.add(tbAccessUser);				
				}				
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
		}
		
		return tbAccessUserList;
		
	}
	
	/**
	 * 그룹원 리스트를 가져온다
	 * @param groupKey : 그룹 키
	 * @param accessPhoneNumber : 그룹에 접근 가능한 전화번호
	 * @return : 그룹원 리스트
	 */
	public List<TbMember> getMemeberList(long groupKey, String accessPhone) {
		
		List<TbMember> tbMemberList = null;
		
		// 인자값 체크
		if( groupKey > 0L && accessPhone != null && accessPhone.length() > 0) {
						
			// 전화번호에 "-" 제거
			accessPhone = accessPhone.replaceAll("-", "");
			
			// 웹서버에서 그룹원 리스트를 Json형식으로 가져온다.
			String url = urlMemeberList + "?fk_group="+ groupKey +"&fd_member_phone=" + accessPhone;									
			try {				
				
				JSONObject item = new JSONObject(getStringFromUrl(url));
				JSONArray jsonArray = item.getJSONArray("list");
				
				// 그룹원 데이타가 있으면 ArrayList를 생성
				if( jsonArray != null && jsonArray.length() > 0 ) {
					tbMemberList = new ArrayList<TbMember>();
				}
				
				// 그룹원 데이타 저장
				for( int i=0; i<jsonArray.length(); i++ ) {					
					long   fk_group 		= jsonArray.getJSONObject(i).getLong("fk_group");					 
					String fd_member_phone 	= jsonArray.getJSONObject(i).getString("fd_member_phone");
					String fd_member_name 	= jsonArray.getJSONObject(i).getString("fd_member_name");
					TbMember tbMember = new TbMember();
					tbMember.setFk_group(fk_group);
					tbMember.setFd_member_phone(fd_member_phone);
					tbMember.setFd_member_name(fd_member_name);
					tbMemberList.add(tbMember);				
				}				
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
		}
		
		return tbMemberList;		
		
	}
	

	/**
	 * 그룹 생성
	 * @param groupName : 그룹명
	 * @param groupPassword : 그룹 비밀번호
	 * @param groupNotice : 그룹 공지
	 * @param groupCreatorPhone : 그룹 생성자 폰번호
	 * @param users : 그룹 멤버
	 * @param admins : 그룹 관리자
	 * @return
	 */
	public TbGroup createGroup(	String groupName, 
								String groupPassword, 
								String groupNotice, 
								String groupCreatorPhone, 
								String[] users, 
								String[] admins) {
		
		TbGroup tbGroup = null;
		
		// 인자값 체크
		if( 	groupName != null 		  && groupName.length() > 0
			&&  groupPassword != null 	  && groupPassword.length() > 0			
			&&  groupCreatorPhone != null && groupCreatorPhone.length() > 0	) {
						
			// 전화번호에 "-" 제거
			groupCreatorPhone = groupCreatorPhone.replaceAll("-", "");
			
			// users와 admins전화번호를 아래 형식으로 변환
			// "Tester:010123456,Tester2:01033335555,Tester3:01077778888,Tester4:01099995555"
			String strUsers = "";
			if( users != null ) {
				for (String user : users) {
					strUsers +=  user + ",";
				}				
			}
			
			String strAdmins = "";
			if( admins != null ) {
				for (String admin : admins) {
					strAdmins +=  admin + ",";
				}				
			}
			
			try {
				
				// 웹서버에서 그룹정보를 Json형식으로 가져온다.
				String url = urlGroupCreate 
						+ "?fd_group_name="		+ URLEncoder.encode(groupName, "UTF-8") 
						+ "&fd_group_password=" + URLEncoder.encode(groupPassword, "UTF-8")
						+ "&fd_group_notice=" 	+ URLEncoder.encode(groupNotice, "UTF-8")
						+ "&fd_group_creator=" 	+ URLEncoder.encode(groupCreatorPhone, "UTF-8")
						+ "&users=" 			+ URLEncoder.encode(strUsers, "UTF-8")
						+ "&admins=" 			+ URLEncoder.encode(strAdmins, "UTF-8");
				
				Log.d("url >> ",url);
				
				JSONObject item = new JSONObject(getStringFromUrl(url));
				JSONObject resultJson  = item.getJSONObject("commonResult");				
				
				// 정상적으로 등록되었음
				String result = resultJson.getString("result");
				if( CommonResult.SUCCESS.equals(result) ) {
					
					// 생성된 그룹 상세정보 저장
					JSONObject tbGroupJson = item.getJSONObject("tbGroup");
					if( item != null ) {					
						tbGroup = new TbGroup();					
						long pk_group 		 	 = tbGroupJson.getLong("pk_group");
						String fd_group_name     = tbGroupJson.getString("fd_group_name");
						String fd_group_password = tbGroupJson.getString("fd_group_password");
						String fd_group_notice   = tbGroupJson.getString("fd_group_notice");					
						String fd_group_creator  = tbGroupJson.getString("fd_group_creator");					
						tbGroup.setPk_group(pk_group);
						tbGroup.setFd_group_password(fd_group_password);
						tbGroup.setFd_group_name(fd_group_name);					
						tbGroup.setFd_group_notice(fd_group_notice);
						tbGroup.setFd_group_creator(fd_group_creator);					
					}
					
				}								
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
		}
		
		return tbGroup;
		
	}
	
	
	/**
	 * 내그룹 > 그룹추가
	 * @param groupKey : 그룹 키
	 * @param groupPassword : 그룹 비밀번호
	 * @param accessPhone : 그룹 비밀번호
	 * @return
	 */
	public boolean addGroup(long groupKey, String groupPassword, String accessPhone ) {				

		boolean result = false;		
		
		// 인자값 체크
		if( 	groupKey > 0L 
			&& groupPassword != null && groupPassword.length() > 0
			&& accessPhone   != null && accessPhone.length()   > 0 ) {
						
			// 전화번호에 "-" 제거
			accessPhone = accessPhone.replaceAll("-", "");
			
			// 웹서버에서 그룹정보를 Json형식으로 가져온다.
			String url = urlGroupAdd + "?pk_group=" + groupKey + "&fd_group_password=" + groupPassword + "&fd_access_phone=" + accessPhone;									
			try {				
				
				JSONObject item = new JSONObject(getStringFromUrl(url));
				JSONObject groupJson = item.getJSONObject("insert");
				
				// 그룹 상세정보 저장
				if( item != null ) {	
					
					String jsonResult     = groupJson.getString("result");
					if( jsonResult != null && jsonResult.equals(BoardResult.SUCCESS) ) {
						result =  true;
					}
				}								
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
		}
		
		return result;
	}
	
//	http://14.63.223.82:8080/pbs/group/update.json?pk_group=100007&fd_group_name=Update Tester Group
//	&fd_group_password=1234
//	&fd_group_notice=Test Group Update Success
//	&fd_group_creator=01022232802
//	&users=Tester:010122802,Tester2:01033332802,Tester3:01077772802,Tester4:01099992802
//	&admins=Tester3:01077772802,Tester4:01099992802
	public boolean updateGroup(String groupName, 
			String groupPassword,
			String groupNotice, String groupCreatorPhone, String[] users,
			String[] admins) {
		return true;
	}
	
	public boolean deleteGroup() { return true;}
	
	
	/**
	 * 내그룹 > 그룹감추기
	 * @param groupKey : 그룹 키
	 * @param accessPhone : 그룹 비밀번호
	 * @return
	 */
	public boolean hiddenGroup(long groupKey, String accessPhone ) {
		
		boolean result = false;		
		
		// 인자값 체크
		if( 	groupKey > 0L
			&& accessPhone   != null && accessPhone.length()   > 0 ) {
						
			// 전화번호에 "-" 제거
			accessPhone = accessPhone.replaceAll("-", "");

			// 웹서버에서 그룹정보를 Json형식으로 가져온다.
			String url = urlGroupHidden + "?fk_group=" + groupKey + "&fd_access_phone=" + accessPhone;									
			try {				
				
				JSONObject item = new JSONObject(getStringFromUrl(url));
				JSONObject groupJson = item.getJSONObject("delete");
				
				// 그룹 상세정보 저장
				if( item != null ) {	
					
					String jsonResult     = groupJson.getString("result");
					if( jsonResult != null && jsonResult.equals(BoardResult.SUCCESS) ) {
						result =  true;
					}
				}								
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
		}
		
		return result;
		
	}
	
	
	
	/**
	 *  주어진 URL의 페이지를 문자열로 얻는다.
	 * @param url : 접속 주소
	 * @return
	 */
	private String getStringFromUrl(String url) {
        
		StringBuffer sb = new StringBuffer();

        try {
        	BufferedReader br = new BufferedReader(new InputStreamReader(getInputStreamFromUrl(url), "UTF-8"));
            
            String line = null;
            // 라인 단위로 데이터를 읽어서 StringBuffer에 저장한다.
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 

        return sb.toString();
    }
	
	/**
	 * 주어진 URL에 대한 입력 스트림(InputStream)을 얻는다.
	 * @param url
	 * @return
	 */
	private InputStream getInputStreamFromUrl(String url) {
		InputStream contentStream = null;
		
		try {
			// HttpClient를 사용해서 주어진 URL에 대한 입력 스트림을 얻는다.			
			HttpClient httpclient = new DefaultHttpClient();					
			HttpResponse response = httpclient.execute(new HttpPost(url));
			contentStream = response.getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contentStream;
		
	}

}
