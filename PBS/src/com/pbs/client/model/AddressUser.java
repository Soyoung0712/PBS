package com.pbs.client.model;

import java.util.List;

import android.graphics.Bitmap;

public class AddressUser {
	
	private String id;
	private String name;
	private Bitmap icon;
	private List<String> dialList;
	private String selectedDial;
	private String mail;
	private boolean checked = false;	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Bitmap getIcon() {
		return icon;
	}
	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}
	public List<String> getDialList() {
		return dialList;
	}
	public void setDialList(List<String> dialList) {
		this.dialList = dialList;
	}	
	public String getSelectedDial() {
		return selectedDial;
	}
	public void setSelectedDial(String selectedDial) {
		this.selectedDial = selectedDial;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
