package com.nanoxic.nanorpc4j;

class RequestKey extends RequestMessage {

	private static final long serialVersionUID = -4119998281034876345L;

	private String key;

	public RequestKey(String action, String key) {
		super(action);
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
