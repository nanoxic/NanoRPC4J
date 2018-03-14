package com.nanoxic.nanorpc4j.model.payment;

import java.math.BigInteger;

import com.nanoxic.nanorpc4j.http.HttpClient;
import com.nanoxic.nanorpc4j.model.account.AccountResponse;
import com.nanoxic.nanorpc4j.model.wallet.Wallet;
import com.nanoxic.nanorpc4j.model.wallet.WalletRequest;

public class Payment {

	private String wallet;

	public Payment(String wallet) {
		this.wallet = wallet;
	}

	public Payment(Wallet wallet) {
		this.wallet = wallet.getWalletId();
	}

	public boolean init() {
		StatusResponse statusResponse = (StatusResponse) HttpClient
				.getResponse(new WalletRequest("payment_init", wallet), StatusResponse.class);
		return statusResponse.getStatus().equals("Ready");
	}

	public String begin() {
		AccountResponse accountResponse = (AccountResponse) HttpClient
				.getResponse(new WalletRequest("payment_begin", wallet), AccountResponse.class);
		return accountResponse.getAccount();
	}

	public boolean wait(String address, BigInteger amount, long timeout) {
		PaymentWaitRequest paymentWaitRequest = new PaymentWaitRequest("payment_wait", address);
		paymentWaitRequest.setAmount(amount);
		paymentWaitRequest.setTimeout(timeout);
		StatusResponse statusResponse = (StatusResponse) HttpClient.getResponse(paymentWaitRequest,
				StatusResponse.class);
		return statusResponse.getStatus().equals("success");
	}

}
