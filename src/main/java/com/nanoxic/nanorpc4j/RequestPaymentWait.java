package com.nanoxic.nanorpc4j;

import java.math.BigInteger;

class RequestPaymentWait extends RequestAccount {

	private static final long serialVersionUID = -936105561869422171L;

	private BigInteger amount;
	private long timeout;

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public RequestPaymentWait(String action, String account) {
		super(action, account);
	}

}
