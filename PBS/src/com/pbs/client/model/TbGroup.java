package com.pbs.client.model;

import java.sql.Timestamp;
import java.util.Date;

public class TbGroup {

	//-- properties --//
	// 그룹 일련번호
	private long pk_group;
	// 그룹명
	private String fd_group_name;
	// 그룹 비밀번호
	private String fd_group_password;
	// 그룹 공지사항
	private String fd_group_notice;
	// 그룹 사용여부
	private String fd_use_yn;
	// 그룹 생성일
	private Timestamp fd_reg_date;
	// 그룹 생성자 번호
	private String fd_group_creator;
	// 그룹 수정일
	private Date fd_mod_date;
	// 그룹 수정자 번호
	private String fd_group_modifier;

	//-- Getter --//
	public long getPk_group() {
		return pk_group;
	}
	public String getFd_group_name() {
		return fd_group_name;
	}
	public String getFd_group_password() {
		return fd_group_password;
	}
	public String getFd_group_notice() {
		return fd_group_notice;
	}
	public String getFd_use_yn() {
		return fd_use_yn;
	}
	public Timestamp getFd_reg_date() {
		return fd_reg_date;
	}
	public String getFd_group_creator() {
		return fd_group_creator;
	}
	public Date getFd_mod_date() {
		return fd_mod_date;
	}
	public String getFd_group_modifier() {
		return fd_group_modifier;
	}

	//-- Setter --//
	public void setPk_group(long pk_group) {
		this.pk_group = pk_group;
	}
	public void setFd_group_name(String fd_group_name) {
		this.fd_group_name = fd_group_name;
	}
	public void setFd_group_password(String fd_group_password) {
		this.fd_group_password = fd_group_password;
	}
	public void setFd_group_notice(String fd_group_notice) {
		this.fd_group_notice = fd_group_notice;
	}
	public void setFd_use_yn(String fd_use_yn) {
		this.fd_use_yn = fd_use_yn;
	}
	public void setFd_reg_date(Timestamp fd_reg_date) {
		this.fd_reg_date = fd_reg_date;
	}
	public void setFd_group_creator(String fd_group_creator) {
		this.fd_group_creator = fd_group_creator;
	}
	public void setFd_mod_date(Date fd_mod_date) {
		this.fd_mod_date = fd_mod_date;
	}
	public void setFd_group_modifier(String fd_group_modifier) {
		this.fd_group_modifier = fd_group_modifier;
	}

	//-- toString --//
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("=== [" + this.getClass().getSimpleName() +  "] ===\n");
		sb.append("[pk_group         ] : " + pk_group         + "\n");
		sb.append("[fd_group_name    ] : " + fd_group_name    + "\n");
		sb.append("[fd_group_password] : " + fd_group_password+ "\n");
		sb.append("[fd_group_notice  ] : " + fd_group_notice  + "\n");
		sb.append("[fd_use_yn        ] : " + fd_use_yn        + "\n");
		sb.append("[fd_reg_date      ] : " + fd_reg_date      + "\n");
		sb.append("[fd_group_creator ] : " + fd_group_creator + "\n");
		sb.append("[fd_mod_date      ] : " + fd_mod_date      + "\n");
		sb.append("[fd_group_modifier] : " + fd_group_modifier+ "\n");
		return sb.toString();
	}

	//-- equals --//
	@Override
	public boolean equals(Object obj) {					
		if (this == obj) { return true;  }                 
		if (obj == null) { return false; }                 
		if (getClass() != obj.getClass()) { return false; }

		TbGroup other = (TbGroup) obj;
		if (pk_group != other.pk_group) return false; 
		if (fd_group_name == null) {							
			if (other.fd_group_name != null)			
				return false;				
		} else if (!fd_group_name.equals(other.fd_group_name))	
			return false;							

		if (fd_group_password == null) {							
			if (other.fd_group_password != null)			
				return false;				
		} else if (!fd_group_password.equals(other.fd_group_password))	
			return false;							

		if (fd_group_notice == null) {							
			if (other.fd_group_notice != null)			
				return false;				
		} else if (!fd_group_notice.equals(other.fd_group_notice))	
			return false;							

		if (fd_use_yn == null) {							
			if (other.fd_use_yn != null)			
				return false;				
		} else if (!fd_use_yn.equals(other.fd_use_yn))	
			return false;							

		if (fd_reg_date == null) {							
			if (other.fd_reg_date != null)			
				return false;				
		} else if (!fd_reg_date.equals(other.fd_reg_date))	
			return false;							

		if (fd_group_creator == null) {							
			if (other.fd_group_creator != null)			
				return false;				
		} else if (!fd_group_creator.equals(other.fd_group_creator))	
			return false;							

		if (fd_mod_date == null) {							
			if (other.fd_mod_date != null)			
				return false;				
		} else if (!fd_mod_date.equals(other.fd_mod_date))	
			return false;							

		if (fd_group_modifier == null) {							
			if (other.fd_group_modifier != null)			
				return false;				
		} else if (!fd_group_modifier.equals(other.fd_group_modifier))	
			return false;							

		return true;
	}

	//-- USER TODO --//
	private String fd_admin_yn;
	public String getFd_admin_yn() {
		return fd_admin_yn;
	}
	public void setFd_admin_yn(String fd_admin_yn) {
		this.fd_admin_yn = fd_admin_yn;
	}
	
}