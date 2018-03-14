package com.nanoxic.nanorpc4j.model.account;

import com.nanoxic.nanorpc4j.messages.ResponseMessage;

public class AccountResponse extends ResponseMessage {

	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
