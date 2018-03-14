package com.nanoxic.nanorpc4j.model.account;

import com.nanoxic.nanorpc4j.messages.RequestMessage;

class KeyRequest extends RequestMessage {

	private static final long serialVersionUID = -4119998281034876345L;

	private String key;

	public KeyRequest(String action, String key) {
		setAction(action);
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
