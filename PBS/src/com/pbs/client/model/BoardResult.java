package com.pbs.client.model;

public class BoardResult {
	
	static public String SUCCESS = "SUCCESS";
	static public String ERROR   = "ERROR";
	
	// insert 성공 개수
	private int insertCnt;
	// update 성공 개수
	private int updateCnt;
	// delete 성공 개수
	private int deleteCnt;	
	// 결과 (SUCCESS, ERROR)
	private String result;
	// 결과 메시지
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
				setMessage("정상적으로 저장 되었습니다.");
			}else if( type == 2 ) {
				this.message = "";
				setMessage("정상적으로 수정 되었습니다.");
			}else if( type == 3 ) {
				this.message = "";
				setMessage("정상적으로 삭제 되었습니다.");
			}			
		}else {
			setResult("ERROR");
			setMessage("잠시 후 다시 시도해 주시기 바랍니다.");
		}
	}	
	
}
