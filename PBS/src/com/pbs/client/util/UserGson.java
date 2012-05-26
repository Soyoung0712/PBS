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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

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
	 * ���׷� ����Ʈ ��������
	 * @param groupKey : �׷� Ű
	 * @param accessPhoneNumber : �׷쿡 ���� ������ ��ȭ��ȣ
	 */
	public List<TbGroup> getMyGroupList(String accessPhone) {
				
		List<TbGroup> tbGroupList = null;
		
		// ���ڰ� üũ
		if( accessPhone != null && accessPhone.length() > 0) {
						
			// ��ȭ��ȣ�� "-" ����
			accessPhone = accessPhone.replaceAll("-", "");
			
			// ���������� �׷� ����Ʈ�� Json�������� �����´�.
			String url = urlMyGroupList + "?&fd_access_phone=" + accessPhone;									
			try {				
				
				JSONObject item = new JSONObject(getStringFromUrl(url));
				JSONArray jsonArray = item.getJSONArray("list");
				
				// �׷� ����Ÿ�� ������ ArrayList�� ����
				if( jsonArray != null && jsonArray.length() > 0 ) {
					tbGroupList = new ArrayList<TbGroup>();
				}
				
				// �׷� ����Ÿ ����
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
	 * �׷� ������ ��������
	 * @param groupKey : �׷� Ű
	 * @param adminPhone : �ش� �׷��� ������ ��ȭ��ȣ
	 * @return
	 */
	public TbGroup getGroupInfo(long groupKey, String adminPhone) {
		
		TbGroup tbGroup = null;
		
		// ���ڰ� üũ
		if( groupKey > 0L && adminPhone != null && adminPhone.length() > 0) {
						
			// ��ȭ��ȣ�� "-" ����
			adminPhone = adminPhone.replaceAll("-", "");
			
			// ���������� �׷������� Json�������� �����´�.
			String url = urlGroupInfo + "?pk_group="+ groupKey +"&fd_access_phone=" + adminPhone;									
			try {				
				
				JSONObject item = new JSONObject(getStringFromUrl(url));
				JSONObject groupJson = item.getJSONObject("view");
				
				// �׷� ������ ����
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
	 * ������ ��ȣ ����Ʈ ��������
	 * @param groupKey : �׷� Ű
	 * @param adminPhone : �ش� �׷��� ������ ��ȭ��ȣ
	 * @return
	 */
	public List<TbAccessUser> getAdminList(long groupKey, String adminPhone) {
		
		List<TbAccessUser> tbAccessUserList = null;
		
		// ���ڰ� üũ
		if( groupKey > 0L && adminPhone != null && adminPhone.length() > 0) {
						
			// ��ȭ��ȣ�� "-" ����
			adminPhone = adminPhone.replaceAll("-", "");
			
			// ���������� ������ ����Ʈ�� Json�������� �����´�.
			String url = urlAdminList + "?fk_group="+ groupKey +"&fd_access_phone=" + adminPhone;									
			try {				
				
				JSONObject item = new JSONObject(getStringFromUrl(url));
				JSONArray jsonArray = item.getJSONArray("list");
				
				// ������ ����Ÿ�� ������ ArrayList�� ����
				if( jsonArray != null && jsonArray.length() > 0 ) {
					tbAccessUserList = new ArrayList<TbAccessUser>();
				}
				
				// ������ ����Ÿ ����
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
	 * �׷�� ����Ʈ�� �����´�
	 * @param groupKey : �׷� Ű
	 * @param accessPhoneNumber : �׷쿡 ���� ������ ��ȭ��ȣ
	 * @return : �׷�� ����Ʈ
	 */
	public List<TbMember> getMemeberList(long groupKey, String accessPhone) {
		
		List<TbMember> tbMemberList = null;
		
		// ���ڰ� üũ
		if( groupKey > 0L && accessPhone != null && accessPhone.length() > 0) {
						
			// ��ȭ��ȣ�� "-" ����
			accessPhone = accessPhone.replaceAll("-", "");
			
			// ���������� �׷�� ����Ʈ�� Json�������� �����´�.
			String url = urlMemeberList + "?fk_group="+ groupKey +"&fd_member_phone=" + accessPhone;									
			try {				
				
				JSONObject item = new JSONObject(getStringFromUrl(url));
				JSONArray jsonArray = item.getJSONArray("list");
				
				// �׷�� ����Ÿ�� ������ ArrayList�� ����
				if( jsonArray != null && jsonArray.length() > 0 ) {
					tbMemberList = new ArrayList<TbMember>();
				}
				
				// �׷�� ����Ÿ ����
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
	 * �׷� ����
	 * @param groupName : �׷��
	 * @param groupPassword : �׷� ��й�ȣ
	 * @param groupNotice : �׷� ����
	 * @param groupCreatorPhone : �׷� ������ ����ȣ
	 * @param users : �׷� ���
	 * @param admins : �׷� ������
	 * @return
	 */
	public TbGroup createGroup(	String groupName, 
								String groupPassword, 
								String groupNotice, 
								String groupCreatorPhone, 
								String[] users, 
								String[] admins) {
		
		TbGroup tbGroup = null;
		
		// ���ڰ� üũ
		if( 	groupName != null 		  && groupName.length() > 0
			&&  groupPassword != null 	  && groupPassword.length() > 0			
			&&  groupCreatorPhone != null && groupCreatorPhone.length() > 0	) {
						
			// ��ȭ��ȣ�� "-" ����
			groupCreatorPhone = groupCreatorPhone.replaceAll("-", "");
			
			// users�� admins��ȭ��ȣ�� �Ʒ� �������� ��ȯ
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
			
			// ���������� �׷������� Json�������� �����´�.
			String url = urlGroupCreate 
					+ "?fd_group_name="		+ groupName 
					+ "&fd_group_password=" + groupPassword
					+ "&fd_group_notice=" 	+ groupNotice
					+ "&fd_group_creator=" 	+ groupCreatorPhone
					+ "&users=" 			+ strUsers
					+ "&admins=" 			+ strAdmins;
			
			try {				
				
				JSONObject item = new JSONObject(getStringFromUrl(url));
				JSONObject resultJson  = item.getJSONObject("commonResult");				
				
				// ���������� ��ϵǾ���
				String result = resultJson.getString("result");
				if( CommonResult.SUCCESS.equals(result) ) {
					
					// ������ �׷� ������ ����
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
	
	
	public boolean insertGroup() { return true;}
	public boolean updateGroup() { return true;}
	public boolean deleteGroup() { return true;}
	public boolean hiddenGroup() { return true;}
	
	
	
	/**
	 *  �־��� URL�� �������� ���ڿ��� ��´�.
	 * @param url : ���� �ּ�
	 * @return
	 */
	private String getStringFromUrl(String url) {
        
		StringBuffer sb = new StringBuffer();

        try {
        	BufferedReader br = new BufferedReader(new InputStreamReader(getInputStreamFromUrl(url), "UTF-8"));
            
            String line = null;
            // ���� ������ �����͸� �о StringBuffer�� �����Ѵ�.
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 

        return sb.toString();
    }
	
	/**
	 * �־��� URL�� ���� �Է� ��Ʈ��(InputStream)�� ��´�.
	 * @param url
	 * @return
	 */
	private InputStream getInputStreamFromUrl(String url) {
		InputStream contentStream = null;
		
		try {
			// HttpClient�� ����ؼ� �־��� URL�� ���� �Է� ��Ʈ���� ��´�.
			/*
			String strUrl = url;
			String params = "";
			int paramIndex = strUrl.lastIndexOf("?");
			if( paramIndex > 0 ) {
				strUrl = strUrl.substring(0, paramIndex);
				params = strUrl.substring(paramIndex, strUrl.length()-1);
				Log.d("strUrl > ", strUrl);
				Log.d("params > ", params);
				params = URLEncoder.encode(params);
			}
			*/
			HttpClient httpclient = new DefaultHttpClient();
			
			//HttpPost httpPost = new HttpPost(strUrl + params);			
			HttpResponse response = httpclient.execute(new HttpGet(url));
			//HttpResponse response = httpclient.execute(httpPost);
			contentStream = response.getEntity().getContent();
			
		// 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contentStream;
		
	}

}
