package com.nanoxic.nanorpc4j.model.account;

import com.nanoxic.nanorpc4j.messages.ResponseMessage;

class WeightResponse extends ResponseMessage {

	private String weight;

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

}