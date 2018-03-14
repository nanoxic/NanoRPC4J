package com.nanoxic.nanorpc4j;

class RequestAccount extends RequestMessage {

	private static final long serialVersionUID = -965143531935325162L;

	private String account;

	public RequestAccount(String action, String account) {
		super(action);
		this.account = account;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
