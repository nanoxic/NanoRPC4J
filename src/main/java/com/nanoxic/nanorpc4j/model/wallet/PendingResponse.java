package com.nanoxic.nanorpc4j.model.wallet;

import com.nanoxic.nanorpc4j.messages.ResponseMessage;

class PendingResponse extends ResponseMessage {

	private int started;

	public int getStarted() {
		return started;
	}

	public void setStarted(int started) {
		this.started = started;
	}

}
