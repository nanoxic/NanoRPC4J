package com.nanoxic.nanorpc4j;

public class Callback {

	private String account;
	private String hash;
	private Block block;
	private String amount;
	private boolean is_send = false;

	// TODO use account object
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public boolean isIs_send() {
		return is_send;
	}

	public void setIs_send(boolean is_send) {
		this.is_send = is_send;
	}

	public String toString() {
		return account + " " + amount; // block.getType() + "\t " +
		// return block;
	}

}
