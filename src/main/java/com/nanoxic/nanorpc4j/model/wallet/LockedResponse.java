package com.nanoxic.nanorpc4j.model.wallet;

import com.nanoxic.nanorpc4j.messages.ResponseMessage;

public class LockedResponse extends ResponseMessage {

	private int locked;

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}
}
