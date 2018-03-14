package com.nanoxic.nanorpc4j;

public class AccountInfo extends ResponseMessage {

	private String frontier;
	private String open_block;
	private String representative_block;
	private String balance;
	private String modified_timestamp;
	private String block_count;
	private String representative;
	private String weight;
	private String pending;

	public String getFrontier() {
		return frontier;
	}

	public void setFrontier(String frontier) {
		this.frontier = frontier;
	}

	public String getOpen_block() {
		return open_block;
	}

	public void setOpen_block(String open_block) {
		this.open_block = open_block;
	}

	public String getRepresentative_block() {
		return representative_block;
	}

	public void setRepresentative_block(String representative_block) {
		this.representative_block = representative_block;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getModified_timestamp() {
		return modified_timestamp;
	}

	public void setModified_timestamp(String modified_timestamp) {
		this.modified_timestamp = modified_timestamp;
	}

	public String getBlock_count() {
		return block_count;
	}

	public void setBlock_count(String block_count) {
		this.block_count = block_count;
	}

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPending() {
		return pending;
	}

	public void setPending(String pending) {
		this.pending = pending;
	}

}
