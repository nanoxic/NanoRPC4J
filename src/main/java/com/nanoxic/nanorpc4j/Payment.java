package com.nanoxic.nanorpc4j;

import java.math.BigInteger;

/**
 * Class making the Nodes payment infrastructure available
 * 
 * @author Koen De Voegt
 *
 */
public class Payment {

	private String walletId;

	/**
	 * Construct a Payment object using a Wallet ID.
	 * 
	 * @param walletId
	 *            The Wallet ID
	 */
	public Payment(String walletId) {
		this.walletId = walletId;
	}

	/**
	 * Construct a Payment object using a Wallet.
	 * 
	 * @param wallet
	 *            The Wallet
	 */
	public Payment(Wallet wallet) {
		this.walletId = wallet.getWalletId();
	}

	/**
	 * Marks all accounts in wallet as available for being used as a payment
	 * session.
	 * 
	 * @return True if successful
	 */
	public boolean init() {
		ResponseStatus statusResponse = (ResponseStatus) HttpClient
				.getResponse(new RequestWallet("payment_init", walletId), ResponseStatus.class);
		return statusResponse.getStatus().equals("Ready");
	}

	/**
	 * Begin a new payment session. Searches wallet for an account that's marked as
	 * available and has a 0 balance. If one is found, the account number is
	 * returned and is marked as unavailable. If no account is found, a new account
	 * is created, placed in the wallet, and returned.
	 * 
	 * @return The account number
	 */
	public String begin() {
		ResponseAccount accountResponse = (ResponseAccount) HttpClient
				.getResponse(new RequestWallet("payment_begin", walletId), ResponseAccount.class);
		return accountResponse.getAccount();
	}

	/**
	 * Wait for payment of 'amount' to arrive in 'account' or until 'timeout'
	 * milliseconds have elapsed.
	 * 
	 * @param address
	 *            The address to receive a payment on
	 * @param amount
	 *            The expected amount
	 * @param timeout
	 *            The time to wait in milliseconds to wait for the payment to arrive
	 * @return True if successful
	 */
	public boolean wait(String address, BigInteger amount, long timeout) {
		RequestPaymentWait paymentWaitRequest = new RequestPaymentWait("payment_wait", address);
		paymentWaitRequest.setAmount(amount);
		paymentWaitRequest.setTimeout(timeout);
		ResponseStatus statusResponse = (ResponseStatus) HttpClient.getResponse(paymentWaitRequest,
				ResponseStatus.class);
		return statusResponse.getStatus().equals("success");
	}

}
