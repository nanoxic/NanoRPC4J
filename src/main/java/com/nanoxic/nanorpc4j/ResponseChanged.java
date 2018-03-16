package com.nanoxic.nanorpc4j;

class ResponseChanged extends ResponseMessage {

	private int changed;

	public int getChanged() {
		return changed;
	}

	public void setChanged(int changed) {
		this.changed = changed;
	}
}
