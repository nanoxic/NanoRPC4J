package com.nanoxic.nanorpc4j.model.wallet;

import com.nanoxic.nanorpc4j.messages.RequestMessage;

class WalletRequest extends RequestMessage {

	private static final long serialVersionUID = -965143531935325162L;

	private String wallet;

	public WalletRequest(String action, String wallet) {
		setAction(action);
		this.wallet = wallet;
	}

	public String getWallet() {
		return wallet;
	}

	public void setWallet(String account) {
		this.wallet = account;
	}
}
