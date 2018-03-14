package com.nanoxic.nanorpc4j.model.account;

import java.util.ArrayList;
import java.util.List;

import com.nanoxic.nanorpc4j.messages.ResponseMessage;

class HistoryResponse extends ResponseMessage{

	private List<HistoryItem> history = new ArrayList<HistoryItem>();

	public List<HistoryItem> getHistory() {
		return history;
	}
	
}
