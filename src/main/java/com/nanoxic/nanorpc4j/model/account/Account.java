package com.nanoxic.nanorpc4j.model.account;

import java.math.BigInteger;
import java.util.List;

import com.nanoxic.nanorpc4j.exception.InitializationException;
import com.nanoxic.nanorpc4j.http.HttpClient;
import com.nanoxic.nanorpc4j.messages.Balance;

/**
 * Class representing a single Nano account
 * 
 * @author Koen De Voegt
 *
 */
public class Account {

	private String address;
	private String publicKey;
	private boolean valid;

	// Constructor
	/**
	 * Initialize the account using either an address or a public key
	 * 
	 * @param accountKey
	 *            An address or an account public key
	 */
	public Account(String accountKey) {
		if (accountKey.startsWith("xrb")) {
			if (validate(accountKey)) {
				this.address = accountKey;
				this.publicKey = getpublicKey();
				valid = true;
			} else {
				valid = false;
				throw new InitializationException("Not a valid NANO address");
			}
		} else {
			this.address = getAddress(accountKey);
			this.publicKey = accountKey;
			valid = true;
		}
	}

	// Getters
	/**
	 * Returns the address of the account
	 * 
	 * @return The address of the account
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Returns the public key of the account
	 * 
	 * @return The public key of the account
	 */
	public String getPublicKey() {
		return publicKey;
	}

	public boolean isValid() {
		return valid;
	}

	// Private methodes // TODO move to Wallet of Node???
	private boolean validate(String address) {
		ValidateResponse validateResponse = (ValidateResponse) HttpClient
				.getResponse(new AccountRequest("validate_account_number", address), ValidateResponse.class);
		return (validateResponse.getValid() == 1);
	}

	private String getpublicKey() {
		KeyResponse representativeResponse = (KeyResponse) HttpClient
				.getResponse(new AccountRequest("account_key", address), KeyResponse.class);
		return representativeResponse.getKey();
	}

	private String getAddress(String publicKey) {
		AccountResponse representativeResponse = (AccountResponse) HttpClient
				.getResponse(new KeyRequest("account_get", publicKey), AccountResponse.class);
		return representativeResponse.getAccount();
	}

	// Public methodes
	/**
	 * Returns how many RAW is owned by this account
	 * 
	 * @return The amount of RAW owned by this account
	 */
	public BigInteger getBalance() {
		Balance balanceResponse = (Balance) HttpClient
				.getResponse(new AccountRequest("account_balance", address), Balance.class);
		return new BigInteger(balanceResponse.getBalance());
	}

	/**
	 * Returns how many RAW have not yet been received by this account
	 * 
	 * @return The amount of RAW pending for this account
	 */
	public BigInteger getPending() {
		Balance balanceResponse = (Balance) HttpClient
				.getResponse(new AccountRequest("account_balance", address), Balance.class);
		return new BigInteger(balanceResponse.getPending());
	}

	/**
	 * Get number of blocks for a specific account
	 * 
	 * @return Number of blocks for a specific account
	 */
	public int getBlockCount() {
		BlockCountResponse blockCountResponse = (BlockCountResponse) HttpClient
				.getResponse(new AccountRequest("account_block_count", address), BlockCountResponse.class);
		return Integer.valueOf(blockCountResponse.getBlock_count());
	}

	/**
	 * Returns the voting weight for account
	 * 
	 * @return The voting weight for account
	 */
	public BigInteger getWeight() {
		WeightResponse weightResponse = (WeightResponse) HttpClient
				.getResponse(new AccountRequest("account_weight", address), WeightResponse.class);
		return new BigInteger(weightResponse.getWeight());
	}

	public AccountInfoResponse getInfo() {
		return (AccountInfoResponse) HttpClient.getResponse(new AccountRequest("account_info", address),
				AccountInfoResponse.class);
	}

	public AccountInfoResponse getInfo(boolean representative, boolean weight, boolean pending) {
		AccountInfoRequest accountInfoRequest = new AccountInfoRequest("account_info", address);
		accountInfoRequest.setRepresentative(representative);
		accountInfoRequest.setWeight(weight);
		accountInfoRequest.setPending(pending);
		return (AccountInfoResponse) HttpClient.getResponse(accountInfoRequest, AccountInfoResponse.class);
	}

	/**
	 * Reports the last 5 history items for this account
	 * 
	 * @return The last 5 history items for this account
	 */
	// TODO use int... count
	public List<HistoryItem> getHistory() {
		return getHistory(5);
	}

	public List<HistoryItem> getHistory(int count) {
		HistoryRequest historyRequest = new HistoryRequest("account_history", address);
		historyRequest.setCount(count);
		return ((HistoryResponse) HttpClient.getResponse(historyRequest, HistoryResponse.class)).getHistory();
	}

	/**
	 * Returns the representative for this account
	 * 
	 * @return The representative for this account
	 */
	public String getRepresentative() {
		RepresentativeResponse representativeResponse = (RepresentativeResponse) HttpClient
				.getResponse(new AccountRequest("account_representative", address), RepresentativeResponse.class);
		return representativeResponse.getRepresentative();
	}

}
