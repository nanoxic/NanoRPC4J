package com.nanoxic.nanorpc4j;

class ResponseLocked extends ResponseMessage {

	private int locked;

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}
}
