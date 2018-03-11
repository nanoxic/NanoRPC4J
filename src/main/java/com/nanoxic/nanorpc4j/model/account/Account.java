package com.nanoxic.nanorpc4j.model.account;

import java.math.BigInteger;

import com.nanoxic.nanorpc4j.exception.InitializationException;
import com.nanoxic.nanorpc4j.http.HttpClient;
import com.nanoxic.nanorpc4j.messages.BalanceResponse;

public class Account {

	private String account;
	private String publicKey;
	private boolean valid;

	// Constructor
	public Account(String accountKey) {
		if (accountKey.startsWith("xrb")) {
			if (validate(accountKey)) {
				this.account = accountKey;
				this.publicKey = getKey();
			} else
				throw new InitializationException("Not a valid NANO address");
		} else {
			this.account = getAccount(accountKey);
			this.publicKey = accountKey;
		}
	}

	// Getters
	public String getAccount() {
		return account;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public boolean isValid() {
		return valid;
	}

	// Private methodes
	private boolean validate(String account) {
		ValidateResponse validateResponse = (ValidateResponse) HttpClient
				.getResponse(new AccountRequest("validate_account_number", account), ValidateResponse.class);
		return (validateResponse.getValid() == 1);
	}

	private String getKey() {
		KeyResponse representativeResponse = (KeyResponse) HttpClient
				.getResponse(new AccountRequest("account_key", account), KeyResponse.class);
		return representativeResponse.getKey();
	}

	private String getAccount(String publicKey) {
		AccountResponse representativeResponse = (AccountResponse) HttpClient
				.getResponse(new KeyRequest("account_get", publicKey), AccountResponse.class);
		return representativeResponse.getAccount();
	}

	// Public methodes
	public BigInteger getBalance() {
		BalanceResponse balanceResponse = (BalanceResponse) HttpClient
				.getResponse(new AccountRequest("account_balance", account), BalanceResponse.class);
		return new BigInteger(balanceResponse.getBalance());
	}

	public BigInteger getPending() {
		BalanceResponse balanceResponse = (BalanceResponse) HttpClient
				.getResponse(new AccountRequest("account_balance", account), BalanceResponse.class);
		return new BigInteger(balanceResponse.getPending());
	}

	public int getBlockCount() {
		BlockCountResponse blockCountResponse = (BlockCountResponse) HttpClient
				.getResponse(new AccountRequest("account_block_count", account), BlockCountResponse.class);
		return Integer.valueOf(blockCountResponse.getBlock_count());
	}

	public void getWeight() {
		// TODO implement
	}
	
	public AccountInfoResponse getInfo() {
		return (AccountInfoResponse) HttpClient.getResponse(new AccountRequest("account_info", account),
				AccountInfoResponse.class);
	}

	public AccountInfoResponse getInfo(boolean representative, boolean weight, boolean pending) {
		AccountInfoRequest accountInfoRequest = new AccountInfoRequest("account_info", account);
		accountInfoRequest.setRepresentative(representative);
		accountInfoRequest.setWeight(weight);
		accountInfoRequest.setPending(pending);
		return (AccountInfoResponse) HttpClient.getResponse(accountInfoRequest, AccountInfoResponse.class);
	}

	public History getHistory() {
		HistoryRequest historyRequest = new HistoryRequest("account_history", account);
		historyRequest.setCount(5);
		return (History) HttpClient.getResponse(historyRequest, History.class);
	}

	public History getHistory(int count) {
		HistoryRequest historyRequest = new HistoryRequest("account_history", account);
		historyRequest.setCount(count);
		return (History) HttpClient.getResponse(historyRequest, History.class);
	}

	public String getRepresentative() {
		RepresentativeResponse representativeResponse = (RepresentativeResponse) HttpClient
				.getResponse(new AccountRequest("account_representative", account), RepresentativeResponse.class);
		return representativeResponse.getRepresentative();
	}

}
