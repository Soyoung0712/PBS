package com.pbs.client.model;

public class CommonResult {
	
	static public String SUCCESS = "SUCCESS";
	static public String ERROR   = "ERROR";
	
	// 결과
	private String result;
	
	// 에러 메시지
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
