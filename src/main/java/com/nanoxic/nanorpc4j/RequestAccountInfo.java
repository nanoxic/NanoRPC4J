package com.nanoxic.nanorpc4j;

class RequestAccountInfo extends RequestAccount {

	private static final long serialVersionUID = 7723924438945394069L;

	private boolean representative;
	private boolean weight;
	private boolean pending;

	public RequestAccountInfo(String action, String account) {
		super(action, account);
	}

	public boolean isRepresentative() {
		return representative;
	}

	public void setRepresentative(boolean representative) {
		this.representative = representative;
	}

	public boolean isWeight() {
		return weight;
	}

	public void setWeight(boolean weight) {
		this.weight = weight;
	}

	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

}
