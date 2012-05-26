package com.pbs.client.model;

import java.util.ArrayList;
import java.util.List;

public class VoBase{

	//-- properties --//
	private int s_range;
	private int e_range;
	private List<String> orderList;	
	
	public VoBase () {
		
		orderList = new ArrayList<String>();
		
	}	
	
	//-- Getter/Setter --//
	public int getS_range() {
		return s_range;
	}
	public void setS_range(int s_range) {
		this.s_range = s_range;
	}
	public int getE_range() {
		return e_range;
	}
	public void setE_range(int e_range) {
		this.e_range = e_range;
	}
	public List<String> getOrderList() {
		return orderList;
	}
	
	public void addOrderAsc(String order) {
		
		String orderStr = " " + order + " ASC";
		this.orderList.add(orderStr);
		
	}
	
	public void addOrderDesc(String order) {
		
		String orderStr = " " + order + " DESC"; 
		this.orderList.add(orderStr);
		
	}
	
}