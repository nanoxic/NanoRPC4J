package com.nanoxic.nanorpc4j;

class ResponsePending extends ResponseMessage {

	private int started;

	public int getStarted() {
		return started;
	}

	public void setStarted(int started) {
		this.started = started;
	}

}
