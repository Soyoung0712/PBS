package com.pbs.client.model;

import java.sql.Timestamp;

public class TbConfig {

	//-- properties --//
	// 환경설정 일련번호
	private long pk_config;
	// 어플 버전
	private String fd_app_ver;
	// 광고노출여부
	private String fd_advertising_use_yn;
	// 등록일
	private Timestamp fd_reg_date;

	//-- Getter --//
	public long getPk_config() {
		return pk_config;
	}
	public String getFd_app_ver() {
		return fd_app_ver;
	}
	public String getFd_advertising_use_yn() {
		return fd_advertising_use_yn;
	}
	public Timestamp getFd_reg_date() {
		return fd_reg_date;
	}

	//-- Setter --//
	public void setPk_config(long pk_config) {
		this.pk_config = pk_config;
	}
	public void setFd_app_ver(String fd_app_ver) {
		this.fd_app_ver = fd_app_ver;
	}
	public void setFd_advertising_use_yn(String fd_advertising_use_yn) {
		this.fd_advertising_use_yn = fd_advertising_use_yn;
	}
	public void setFd_reg_date(Timestamp fd_reg_date) {
		this.fd_reg_date = fd_reg_date;
	}

	//-- toString --//
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("=== [" + this.getClass().getSimpleName() +  "] ===\n");
		sb.append("[pk_config            ] : " + pk_config            + "\n");
		sb.append("[fd_app_ver           ] : " + fd_app_ver           + "\n");
		sb.append("[fd_advertising_use_yn] : " + fd_advertising_use_yn+ "\n");
		sb.append("[fd_reg_date          ] : " + fd_reg_date          + "\n");
		return sb.toString();
	}

	//-- equals --//
	@Override
	public boolean equals(Object obj) {					
		if (this == obj) { return true;  }                 
		if (obj == null) { return false; }                 
		if (getClass() != obj.getClass()) { return false; }

		TbConfig other = (TbConfig) obj;
		if (pk_config != other.pk_config) return false; 
		if (fd_app_ver == null) {							
			if (other.fd_app_ver != null)			
				return false;				
		} else if (!fd_app_ver.equals(other.fd_app_ver))	
			return false;							

		if (fd_advertising_use_yn == null) {							
			if (other.fd_advertising_use_yn != null)			
				return false;				
		} else if (!fd_advertising_use_yn.equals(other.fd_advertising_use_yn))	
			return false;							

		if (fd_reg_date == null) {							
			if (other.fd_reg_date != null)			
				return false;				
		} else if (!fd_reg_date.equals(other.fd_reg_date))	
			return false;							

		return true;
	}

}