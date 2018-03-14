package com.nanoxic.nanorpc4j;

import java.util.List;

class ResponseListAccounts extends ResponseMessage {

	private List<String> accounts;

	public List<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}
}