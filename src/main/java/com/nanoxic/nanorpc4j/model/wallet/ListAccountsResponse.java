package com.nanoxic.nanorpc4j.model.wallet;

import java.util.List;

import com.nanoxic.nanorpc4j.messages.ResponseMessage;

public class ListAccountsResponse extends ResponseMessage {

	private List<String> accounts;

	public List<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}
}