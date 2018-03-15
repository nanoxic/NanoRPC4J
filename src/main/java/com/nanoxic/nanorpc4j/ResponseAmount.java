package com.nanoxic.nanorpc4j;

import java.math.BigInteger;

class ResponseAmount extends ResponseMessage {

	private BigInteger amount;

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}
}
