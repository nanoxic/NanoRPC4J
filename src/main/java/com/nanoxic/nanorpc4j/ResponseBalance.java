package com.nanoxic.nanorpc4j;

import java.util.HashMap;

class ResponseBalance extends ResponseMessage {

	private HashMap<String, Balance> balances;

	public HashMap<String, Balance> getBalances() {
		return balances;
	}

	public void setBalances(HashMap<String, Balance> balances) {
		this.balances = balances;
	}

}
