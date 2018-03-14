package com.nanoxic.nanorpc4j.model.account;

import com.nanoxic.nanorpc4j.messages.ResponseMessage;

class BlockCountResponse extends ResponseMessage {

	private String block_count;

	public String getBlock_count() {
		return block_count;
	}

	public void setBlock_count(String block_count) {
		this.block_count = block_count;
	}

}