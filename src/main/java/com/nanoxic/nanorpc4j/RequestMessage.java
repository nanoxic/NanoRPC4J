package com.nanoxic.nanorpc4j;

import java.io.Serializable;

class RequestMessage implements Serializable {

	private static final long serialVersionUID = -8052630731036869055L;

	private String action;

	public RequestMessage(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
