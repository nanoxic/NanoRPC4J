package com.nanoxic.nanorpc4j;

class RequestWallet extends RequestMessage {

	private static final long serialVersionUID = -965143531935325162L;

	private String wallet;

	public RequestWallet(String action, String wallet) {
		super(action);
		this.wallet = wallet;
	}

	public String getWallet() {
		return wallet;
	}

	public void setWallet(String account) {
		this.wallet = account;
	}
}
