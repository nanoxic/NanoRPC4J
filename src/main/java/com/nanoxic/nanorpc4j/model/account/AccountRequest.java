package com.nanoxic.nanorpc4j.model.account;

import com.nanoxic.nanorpc4j.messages.RequestMessage;

class AccountRequest extends RequestMessage {

	private static final long serialVersionUID = -965143531935325162L;

	private String account;

	public AccountRequest(String action, String account) {
		setAction(action);
		this.account = account;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
