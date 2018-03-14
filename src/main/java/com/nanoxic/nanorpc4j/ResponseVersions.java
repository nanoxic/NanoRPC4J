package com.nanoxic.nanorpc4j;

class ResponseVersions extends ResponseMessage {

	private int rpc_version;
	private int store_version;
	private String node_vendor;

	public int getRpc_version() {
		return rpc_version;
	}

	public void setRpc_version(int rpc_version) {
		this.rpc_version = rpc_version;
	}

	public int getStore_version() {
		return store_version;
	}

	public void setStore_version(int store_version) {
		this.store_version = store_version;
	}

	public String getNode_vendor() {
		return node_vendor;
	}

	public void setNode_vendor(String node_vendor) {
		this.node_vendor = node_vendor;
	}
}
