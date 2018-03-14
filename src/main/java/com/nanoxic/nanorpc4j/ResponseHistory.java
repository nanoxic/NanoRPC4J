package com.nanoxic.nanorpc4j;

import java.util.ArrayList;
import java.util.List;

class ResponseHistory extends ResponseMessage{

	private List<HistoryItem> history = new ArrayList<HistoryItem>();

	public List<HistoryItem> getHistory() {
		return history;
	}
	
}
