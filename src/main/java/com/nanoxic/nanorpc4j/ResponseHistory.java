package com.nanoxic.nanorpc4j;

import java.util.ArrayList;
import java.util.List;

class ResponseHistory extends ResponseMessage {

	private String account;
	private List<HistoryItem> history = new ArrayList<HistoryItem>();
	private String previous;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public List<HistoryItem> getHistory() {
		return history;
	}

	public void setHistory(List<HistoryItem> history) {
		this.history = history;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

}
