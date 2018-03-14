package com.nanoxic.nanorpc4j.model.wallet;

import java.util.HashMap;

import com.nanoxic.nanorpc4j.messages.Balance;
import com.nanoxic.nanorpc4j.messages.ResponseMessage;

class BalanceResponse extends ResponseMessage {

	private HashMap<String, Balance> balances;

	public HashMap<String, Balance> getBalances() {
		return balances;
	}

	public void setBalances(HashMap<String, Balance> balances) {
		this.balances = balances;
	}

}
