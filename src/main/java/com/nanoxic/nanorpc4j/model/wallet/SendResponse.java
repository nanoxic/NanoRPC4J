package com.nanoxic.nanorpc4j.model.wallet;

import com.nanoxic.nanorpc4j.messages.ResponseMessage;

class SendResponse extends ResponseMessage {

	private String block;

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

}
