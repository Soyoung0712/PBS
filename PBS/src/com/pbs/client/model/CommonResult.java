package com.pbs.client.model;

public class CommonResult {
	
	static public String SUCCESS = "SUCCESS";
	static public String ERROR   = "ERROR";
	
	// ���
	private String result;
	
	// ���� �޽���
	private String errorMessage;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}	
	
}
