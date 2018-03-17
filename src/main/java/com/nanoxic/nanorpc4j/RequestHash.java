package com.nanoxic.nanorpc4j;

class RequestHash extends RequestMessage {

	private static final long serialVersionUID = 7582170544853685526L;

	private String hash;

	public RequestHash(String action) {
		super(action);
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
