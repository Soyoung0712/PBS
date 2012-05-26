package com.pbs.client.model;

public class BoardResult {
	
	static public String SUCCESS = "SUCCESS";
	static public String ERROR   = "ERROR";
	
	// insert ���� ����
	private int insertCnt;
	// update ���� ����
	private int updateCnt;
	// delete ���� ����
	private int deleteCnt;	
	// ��� (SUCCESS, ERROR)
	private String result;
	// ��� �޽���
	private String message;
	
	public int getInsertCnt() {
		return insertCnt;
	}

	public void setInsertCnt(int insertCnt) {
		this.insertCnt = insertCnt;
		setResultAndMessage(this.insertCnt, 1);
	}

	public int getUpdateCnt() {
		return updateCnt;
	}

	public void setUpdateCnt(int updateCnt) {
		this.updateCnt = updateCnt;
		setResultAndMessage(this.updateCnt, 2);
	}

	public int getDeleteCnt() {
		return deleteCnt;
	}

	public void setDeleteCnt(int deleteCnt) {
		this.deleteCnt = deleteCnt;
		setResultAndMessage(this.deleteCnt, 3);
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setErrorMessage(String message) {
		this.setResult(BoardResult.ERROR);
		this.message = message;
	}

	/**
	 * 
	 * @param cnt
	 * @param type : 1[insert], 2[update], 3[delete]
	 */
	private void setResultAndMessage(int cnt, int type) {
		if( cnt > 0 ) {
			setResult(BoardResult.SUCCESS);
			if( type == 1 ) {
				setMessage("���������� ���� �Ǿ����ϴ�.");
			}else if( type == 2 ) {
				this.message = "";
				setMessage("���������� ���� �Ǿ����ϴ�.");
			}else if( type == 3 ) {
				this.message = "";
				setMessage("���������� ���� �Ǿ����ϴ�.");
			}			
		}else {
			setResult("ERROR");
			setMessage("��� �� �ٽ� �õ��� �ֽñ� �ٶ��ϴ�.");
		}
	}	
	
}
