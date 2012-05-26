package com.pbs.client.model;

import java.sql.Timestamp;

public class TbAccessUser {

	//-- properties --//
	// 그룹 일련번호
	private long fk_group;
	// 접속 가능 번호
	private String fd_access_phone;
	// 관리자 권한 유무
	private String fd_admin_yn;
	// 등록일
	private Timestamp fd_reg_date;

	//-- Getter --//
	public long getFk_group() {
		return fk_group;
	}
	public String getFd_access_phone() {
		return fd_access_phone;
	}
	public String getFd_admin_yn() {
		return fd_admin_yn;
	}
	public Timestamp getFd_reg_date() {
		return fd_reg_date;
	}

	//-- Setter --//
	public void setFk_group(long fk_group) {
		this.fk_group = fk_group;
	}
	public void setFd_access_phone(String fd_access_phone) {
		this.fd_access_phone = fd_access_phone;
	}
	public void setFd_admin_yn(String fd_admin_yn) {
		this.fd_admin_yn = fd_admin_yn;
	}
	public void setFd_reg_date(Timestamp fd_reg_date) {
		this.fd_reg_date = fd_reg_date;
	}

	//-- toString --//
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("=== [" + this.getClass().getSimpleName() +  "] ===\n");
		sb.append("[fk_group       ] : " + fk_group       + "\n");
		sb.append("[fd_access_phone] : " + fd_access_phone+ "\n");
		sb.append("[fd_admin_yn    ] : " + fd_admin_yn    + "\n");
		sb.append("[fd_reg_date    ] : " + fd_reg_date    + "\n");
		return sb.toString();
	}

	//-- equals --//
	@Override
	public boolean equals(Object obj) {					
		if (this == obj) { return true;  }                 
		if (obj == null) { return false; }                 
		if (getClass() != obj.getClass()) { return false; }

		TbAccessUser other = (TbAccessUser) obj;
		if (fk_group != other.fk_group) return false; 
		if (fd_access_phone == null) {							
			if (other.fd_access_phone != null)			
				return false;				
		} else if (!fd_access_phone.equals(other.fd_access_phone))	
			return false;							

		if (fd_admin_yn == null) {							
			if (other.fd_admin_yn != null)			
				return false;				
		} else if (!fd_admin_yn.equals(other.fd_admin_yn))	
			return false;							

		if (fd_reg_date == null) {							
			if (other.fd_reg_date != null)			
				return false;				
		} else if (!fd_reg_date.equals(other.fd_reg_date))	
			return false;							

		return true;
	}

}