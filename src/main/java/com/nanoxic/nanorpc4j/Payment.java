package com.nanoxic.nanorpc4j;

import java.math.BigInteger;

public class Payment {

	private String wallet;

	public Payment(String wallet) {
		this.wallet = wallet;
	}

	public Payment(Wallet wallet) {
		this.wallet = wallet.getWalletId();
	}

	public boolean init() {
		ResponseStatus statusResponse = (ResponseStatus) HttpClient
				.getResponse(new RequestWallet("payment_init", wallet), ResponseStatus.class);
		return statusResponse.getStatus().equals("Ready");
	}

	public String begin() {
		ResponseAccount accountResponse = (ResponseAccount) HttpClient
				.getResponse(new RequestWallet("payment_begin", wallet), ResponseAccount.class);
		return accountResponse.getAccount();
	}

	public boolean wait(String address, BigInteger amount, long timeout) {
		RequestPaymentWait paymentWaitRequest = new RequestPaymentWait("payment_wait", address);
		paymentWaitRequest.setAmount(amount);
		paymentWaitRequest.setTimeout(timeout);
		ResponseStatus statusResponse = (ResponseStatus) HttpClient.getResponse(paymentWaitRequest,
				ResponseStatus.class);
		return statusResponse.getStatus().equals("success");
	}

}
