package com.nanoxic.nanorpc4j.model.payment;

import java.math.BigInteger;

import com.nanoxic.nanorpc4j.model.account.AccountRequest;

public class PaymentWaitRequest extends AccountRequest {

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

	public PaymentWaitRequest(String action, String account) {
		super(action, account);
	}

}
