package com.nanoxic.nanorpc4j.model.account;

import com.nanoxic.nanorpc4j.messages.ResponseMessage;

class RepresentativeResponse extends ResponseMessage {

	private String representative;

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}
}
