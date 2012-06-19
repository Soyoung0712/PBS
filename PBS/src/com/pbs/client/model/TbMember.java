package com.pbs.client.model;

import java.sql.Timestamp;

import android.telephony.PhoneNumberUtils;

public class TbMember {

	//-- properties --//
	// 그룹 일련번호
	private long fk_group;
	// 그룹원 전화번호
	private String fd_member_phone;
	// 그룹원 이름
	private String fd_member_name;
	// 등록일
	private Timestamp fd_reg_date;

	//-- Getter --//
	public long getFk_group() {
		return fk_group;
	}
	public String getFd_member_phone() {
		return fd_member_phone;
	}
	public String getFd_member_name() {
		return fd_member_name;
	}
	/*
	public Timestamp getFd_reg_date() {
		return fd_reg_date;
	}
	*/

	//-- Setter --//
	public void setFk_group(long fk_group) {
		this.fk_group = fk_group;
	}
	public void setFd_member_phone(String fd_member_phone) {
		this.fd_member_phone = fd_member_phone;
	}
	public void setFd_member_name(String fd_member_name) {
		this.fd_member_name = fd_member_name;
	}
	public void setFd_reg_date(Timestamp fd_reg_date) {
		this.fd_reg_date = fd_reg_date;
	}

	//-- toString --//
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("=== [" + this.getClass().getSimpleName() +  "] ===\n");
		sb.append("[fk_group       ] : " + fk_group       + "\n");
		sb.append("[fd_member_phone] : " + fd_member_phone+ "\n");
		sb.append("[fd_member_name ] : " + fd_member_name + "\n");
		sb.append("[fd_reg_date    ] : " + fd_reg_date    + "\n");		
		return sb.toString();
	}

	//-- equals --//
	@Override
	public boolean equals(Object obj) {					
		if (this == obj) { return true;  }                 
		if (obj == null) { return false; }                 
		if (getClass() != obj.getClass()) { return false; }

		TbMember other = (TbMember) obj;
		if (fk_group != other.fk_group) return false; 
		if (fd_member_phone == null) {							
			if (other.fd_member_phone != null)			
				return false;				
		} else if (!fd_member_phone.equals(other.fd_member_phone))	
			return false;							

		if (fd_member_name == null) {							
			if (other.fd_member_name != null)			
				return false;				
		} else if (!fd_member_name.equals(other.fd_member_name))	
			return false;							

		if (fd_reg_date == null) {							
			if (other.fd_reg_date != null)			
				return false;				
		} else if (!fd_reg_date.equals(other.fd_reg_date))	
			return false;							

		return true;
	}
	
	//-- USER TODO --//
	private boolean checked = false;
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean check() {
		if( checked ) {
			checked = false;
		}else {
			checked = true;
		}
		return checked;
	}
	
	public String getFd_member_phone_view() {
		if( fd_member_phone != null ) {
			fd_member_phone = PhoneNumberUtils.formatNumber(fd_member_phone); 
		}		
		return fd_member_phone;
	}

}