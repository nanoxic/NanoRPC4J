package com.nanoxic.nanorpc4j.model.wallet;

import java.math.BigInteger;

class BalanceRequest extends WalletRequest {

	private static final long serialVersionUID = 889580977598665426L;

	private BigInteger threshold;

	public BalanceRequest(String action, String wallet) {
		super(action, wallet);
	}

	public BigInteger getThreshold() {
		return threshold;
	}

	public void setThreshold(BigInteger threshold) {
		this.threshold = threshold;
	}

}
