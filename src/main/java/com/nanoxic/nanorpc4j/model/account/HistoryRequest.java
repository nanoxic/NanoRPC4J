package com.nanoxic.nanorpc4j.model.account;

public class HistoryRequest extends AccountRequest {

	private static final long serialVersionUID = 7868753707387947478L;

	private int count;
	
	public HistoryRequest(String action, String account) {
		super(action, account);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
