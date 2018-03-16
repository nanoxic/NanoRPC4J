package com.nanoxic.nanorpc4j;

import java.math.BigInteger;

class RequestAmount extends RequestMessage {

	private static final long serialVersionUID = -1291828500989167697L;

	private BigInteger amount;

	public RequestAmount(String action) {
		super(action);
	}

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

}
