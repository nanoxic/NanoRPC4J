package com.nanoxic.nanorpc4j.model.wallet;

import java.math.BigInteger;

class SendRequest extends WalletRequest {

	private static final long serialVersionUID = 8843463699358569854L;

	private String source;
	private String destination;
	private BigInteger amount;
	private String id;

	public SendRequest(String action, String wallet) {
		super(action, wallet);
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
