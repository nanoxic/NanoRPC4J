package com.nanoxic.nanorpc4j.listeners;

import com.nanoxic.nanorpc4j.Callback;

public abstract class AccountListener extends AbstractCallbackListener {

	private String account;

	// TODO use account object
	public AccountListener(String account) {
		this.setAccount(account);
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public abstract void onNewAccountCallback(Callback callback);

	@Override
	protected final void newCallback(Callback callback) {
		if (account.equals(callback.getAccount()))
			onNewAccountCallback(callback);
	}

}
