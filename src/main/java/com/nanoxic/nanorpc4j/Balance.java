package com.nanoxic.nanorpc4j;

/**
 * Object that contains a balance and a pending amount of NANO.
 * 
 * @author Koen De Voegt
 *
 */
public class Balance extends ResponseMessage {

	private String balance;
	private String pending;

	/**
	 * Returns the balance amount.
	 * 
	 * @return NANO object of the balance amount.
	 */
	public NANO getBalance() {
		return NANO.fromRAW(balance);
	}

	/**
	 * Returns the pending amount.
	 * 
	 * @return NANO object of the pending amount.
	 */
	public NANO getPending() {
		return NANO.fromRAW(pending);
	}

	/**
	 * Returns the string representation of this Balance, using default notation for
	 * the NANO it contains.
	 * 
	 * @return String representation of this Balance.
	 */
	@Override
	public String toString() {
		return "Balance: " + getBalance() + " Pending: " + getPending();
	}
}
