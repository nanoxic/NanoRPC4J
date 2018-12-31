package com.nanoxic.nanorpc4j;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Block {
	// TODO tests
	private Type type;
	private String account; // TODO should be Account object
	private String previous;
	private String representative;
	private String balance; // TODO NANO object
	private String link;
	private String link_as_account; // TODO Account
	private String signature;
	private String work;

	// For SEND block
	private String destination;
	// for RECEIVE block
	private String source;

	// OPEN CHANGE???

	/*
	 * { "type": "send", "previous":
	 * "5CEAC997A057E2D134E15FF621358546F7F458ACC91D2B26C7E403DC998E2CA0",
	 * "destination":
	 * "xrb_19anuj7kdo6skbcaasu8y1y6q1q8sdzi15hf75dyo7ez3mnyaw575zy9u57f",
	 * "balance": "00000000014999E47469DA7FFFFFE35A", "work": "7f2476c8359dd4c6",
	 * "signature":
	 * "22579A6A79486E25B6409A8F29DF3876D65B98833F27F072DBEB3E8EDE04280CDE634FACA321A47E1B880689139CCB89503E80243E38BF47814DF4045EF22B07"
	 * }
	 */

	/*
	 * "type": "state", "account":
	 * "xrb_1mitch8thnpmc13hwydwxcb8u1iy9q1zz7fx3fyzeg9ohzm8uwrzeboix6hm",
	 * "previous":
	 * "DBE81E0BFDF39EC39E887E9DCF6FC00020EC44EACE147AB8ACFC746CAD1AB3F2",
	 * "representative":
	 * "xrb_1mitch8thnpmc13hwydwxcb8u1iy9q1zz7fx3fyzeg9ohzm8uwrzeboix6hm",
	 * "balance": "96249999999999999881452191744", "link":
	 * "571FEFD85B0A896AE4C2363E76FEA46406D00EEF7ACFB9813E3FE650E3AD7DEF",
	 * "link_as_account":
	 * "xrb_1orzxze7p4nbfdke6fjyguzcas18t19gyyphq81mwhz8c5jttzhhcfxudu7b",
	 * "signature":
	 * "3BAB358A0DE5755AB6F6F6226A1197E65450AB198C0FA2B820050457EBEA39E04A29129BD9437CDEECEEBB0EF0254A22489C506C0A2D959F5720897FCD72FB04",
	 * "work": "ff4282ba08f517cf"
	 */

	private ObjectMapper mapper = new ObjectMapper();

	public enum Type {
		OPEN, SEND, RECEIVE, CHANGE, STATE
	}

	public Block() {
	}

	public Block(String block) {
		try {
			Block test = mapper.readValue(block, Block.class);
			this.type = test.getType();
			this.work = test.getWork();
			this.signature = test.getSignature();
			this.account = test.getAccount();
			// this.source = test.getSource();
			this.representative = test.getRepresentative();
			this.previous = test.getPrevious();
			this.balance = test.getBalance();
			this.destination = test.getDestination();
		} catch (IOException e) {
			System.err.println("THIS ERROR");
			e.printStackTrace();
		}
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setType(String type) {
		this.type = Type.valueOf(type.toUpperCase());
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String toString() {
		return type.toString() + ": " + account + " " + balance;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink_as_account() {
		return link_as_account;
	}

	public void setLink_as_account(String link_as_account) {
		this.link_as_account = link_as_account;
	}

}
