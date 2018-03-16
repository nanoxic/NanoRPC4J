package com.nanoxic.nanorpc4j;

class RequestReceive extends RequestWallet {

	private static final long serialVersionUID = -7508752794268787129L;

	private String address;
	private String block;
	private String work;

	public RequestReceive(String action, String wallet) {
		super(action, wallet);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

}
