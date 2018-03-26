package com.nanoxic.nanorpc4j;

/**
 * Object representing one item in the Account history.
 * 
 * @author Koen De Voegt
 *
 */
public class HistoryItem {

	private String hash;
	private String type; // TODO enum???
	private String account;
	private String amount;

	/**
	 * The hash for this item.
	 * 
	 * @return The hash for this item.
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * The type of this item.
	 * 
	 * @return The type of this item.
	 */
	public String getType() {
		return type;
	}

	/**
	 * The Account of this item.
	 * 
	 * @return The Account of this item.
	 */
	public Account getAccount() {
		return new Account(account);
	}

	/**
	 * The amount of this item.
	 * 
	 * @return The amount of this item.
	 */
	public NANO getAmount() {
		return NANO.fromRAW(amount);
	}

}
