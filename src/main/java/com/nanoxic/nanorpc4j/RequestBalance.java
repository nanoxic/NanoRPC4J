package com.nanoxic.nanorpc4j;

import java.math.BigInteger;

class RequestBalance extends RequestWallet {

	private static final long serialVersionUID = 889580977598665426L;

	private BigInteger threshold;

	public RequestBalance(String action, String wallet) {
		super(action, wallet);
	}

	public BigInteger getThreshold() {
		return threshold;
	}

	public void setThreshold(BigInteger threshold) {
		this.threshold = threshold;
	}

}
