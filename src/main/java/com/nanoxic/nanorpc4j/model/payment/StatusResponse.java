package com.nanoxic.nanorpc4j.model.payment;

import com.nanoxic.nanorpc4j.messages.ResponseMessage;

public class StatusResponse extends ResponseMessage {

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
