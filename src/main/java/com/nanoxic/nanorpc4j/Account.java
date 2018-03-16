package com.nanoxic.nanorpc4j;

import java.math.BigInteger;
import java.util.List;

import com.nanoxic.nanorpc4j.exception.InitializationException;

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
			if (Node.isValidAddress(accountKey)) {
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
	private String getpublicKey() {
		ResponseKey representativeResponse = (ResponseKey) HttpClient
				.getResponse(new RequestAccount("account_key", address), ResponseKey.class);
		return representativeResponse.getKey();
	}

	private String getAddress(String publicKey) {
		ResponseAccount representativeResponse = (ResponseAccount) HttpClient
				.getResponse(new RequestKey("account_get", publicKey), ResponseAccount.class);
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
				.getResponse(new RequestAccount("account_balance", address), Balance.class);
		return new BigInteger(balanceResponse.getBalance());
	}

	/**
	 * Returns how many RAW have not yet been received by this account
	 * 
	 * @return The amount of RAW pending for this account
	 */
	public BigInteger getPending() {
		Balance balanceResponse = (Balance) HttpClient
				.getResponse(new RequestAccount("account_balance", address), Balance.class);
		return new BigInteger(balanceResponse.getPending());
	}

	/**
	 * Get number of blocks for a specific account
	 * 
	 * @return Number of blocks for a specific account
	 */
	public int getBlockCount() {
		ResponseBlockCount blockCountResponse = (ResponseBlockCount) HttpClient
				.getResponse(new RequestAccount("account_block_count", address), ResponseBlockCount.class);
		return Integer.valueOf(blockCountResponse.getBlock_count());
	}

	/**
	 * Returns the voting weight for account
	 * 
	 * @return The voting weight for account
	 */
	public BigInteger getWeight() {
		ResponseWeight weightResponse = (ResponseWeight) HttpClient
				.getResponse(new RequestAccount("account_weight", address), ResponseWeight.class);
		return new BigInteger(weightResponse.getWeight());
	}

	public AccountInfo getInfo() {
		return (AccountInfo) HttpClient.getResponse(new RequestAccount("account_info", address),
				AccountInfo.class);
	}

	public AccountInfo getInfo(boolean representative, boolean weight, boolean pending) {
		RequestAccountInfo accountInfoRequest = new RequestAccountInfo("account_info", address);
		accountInfoRequest.setRepresentative(representative);
		accountInfoRequest.setWeight(weight);
		accountInfoRequest.setPending(pending);
		return (AccountInfo) HttpClient.getResponse(accountInfoRequest, AccountInfo.class);
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
		RequestHistory historyRequest = new RequestHistory("account_history", address);
		historyRequest.setCount(count);
		return ((ResponseHistory) HttpClient.getResponse(historyRequest, ResponseHistory.class)).getHistory();
	}

	/**
	 * Returns the representative for this account
	 * 
	 * @return The representative for this account
	 */
	public String getRepresentative() {
		ResponseRepresentative representativeResponse = (ResponseRepresentative) HttpClient
				.getResponse(new RequestAccount("account_representative", address), ResponseRepresentative.class);
		return representativeResponse.getRepresentative();
	}

}
