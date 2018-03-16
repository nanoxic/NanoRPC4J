package com.nanoxic.nanorpc4j;

import java.math.BigInteger;

class ResponseAvailable extends ResponseMessage {

	private BigInteger available;

	public BigInteger getAvailable() {
		return available;
	}

	public void setAvailable(BigInteger available) {
		this.available = available;
	}
}
