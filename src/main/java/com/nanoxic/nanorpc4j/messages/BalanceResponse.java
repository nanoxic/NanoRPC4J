package com.nanoxic.nanorpc4j.messages;

public class BalanceResponse extends ResponseMessage {

	private String balance;
	private String pending;

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getPending() {
		return pending;
	}

	public void setPending(String pending) {
		this.pending = pending;
	}

}
