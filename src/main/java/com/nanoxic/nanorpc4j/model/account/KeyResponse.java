package com.nanoxic.nanorpc4j.model.account;

import com.nanoxic.nanorpc4j.messages.ResponseMessage;

class KeyResponse extends ResponseMessage {

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
