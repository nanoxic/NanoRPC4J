package com.nanoxic.nanorpc4j.model.wallet;

import com.nanoxic.nanorpc4j.messages.ResponseMessage;

public class ContainsResponse extends ResponseMessage {

	private int exists;

	public int getExists() {
		return exists;
	}

	public void setExists(int exists) {
		this.exists = exists;
	}

}
