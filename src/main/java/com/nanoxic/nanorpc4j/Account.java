package com.nanoxic.nanorpc4j;

import java.math.BigInteger;
import java.util.List;

import com.nanoxic.nanorpc4j.exceptions.InitializationException;

/**
 * Class representing a single Nano account
 * 
 * @author Koen De Voegt
 *
 */
public class Account {

	private String address;
	private String publicKey;

	// Constructor
	/**
	 * Construct the Account using either an address or a public key
	 * 
	 * @param accountKey
	 *            An address or an account public key
	 */
	public Account(String accountKey) {
		if (accountKey.startsWith("xrb_")) {
			if (Node.isValidAddress(accountKey)) {
				this.address = accountKey;
				this.publicKey = getpublicKey();
			} else {
				throw new InitializationException("Not a valid NANO address");
			}
		} else {
			this.address = getAddress(accountKey);
			if (this.address == null)
				throw new InitializationException("Not a valid NANO public key");
			this.publicKey = accountKey;
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

	// Private methodes
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
		Balance balanceResponse = (Balance) HttpClient.getResponse(new RequestAccount("account_balance", address),
				Balance.class);
		return new BigInteger(balanceResponse.getBalance());
	}

	/**
	 * Returns how many RAW have not yet been received by this account
	 * 
	 * @return The amount of RAW pending for this account
	 */
	public BigInteger getPending() {
		Balance balanceResponse = (Balance) HttpClient.getResponse(new RequestAccount("account_balance", address),
				Balance.class);
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

	/**
	 * Returns frontier, open block, change representative block, balance, last
	 * modified timestamp from local database &amp; block count for account.
	 * 
	 * @return An AccountInfo object containing the requested information
	 */
	public AccountInfo getInfo() {
		return (AccountInfo) HttpClient.getResponse(new RequestAccount("account_info", address), AccountInfo.class);
	}

	/**
	 * Returns frontier, open block, change representative block, balance, last
	 * modified timestamp from local database &amp; block count for account. Optionally
	 * returns representative, voting weight, pending balance for account
	 * 
	 * @param representative Include the representative in the AccountInfo object 
	 * @param weight Include the weight in the AccountInfo object
	 * @param pending Include the pending in the AccountInfo object
	 * @return An AccountInfo object containing the requested information
	 */
	public AccountInfo getInfo(boolean representative, boolean weight, boolean pending) {
		RequestAccountInfo accountInfoRequest = new RequestAccountInfo("account_info", address);
		accountInfoRequest.setRepresentative(representative);
		accountInfoRequest.setWeight(weight);
		accountInfoRequest.setPending(pending);
		return (AccountInfo) HttpClient.getResponse(accountInfoRequest, AccountInfo.class);
	}

	/**
	 * Returns the last count history items for this account, defaults to 5 items
	 * 
	 * @param count
	 *            Either one or no Integer, the amount of history items wanted
	 * @return The last 5 history items for this account
	 */
	public List<HistoryItem> getHistory(int... count) {
		int numberOfItems = 5;
		if (count.length > 0)
			numberOfItems = count[0];
		RequestHistory historyRequest = new RequestHistory("account_history", address);
		historyRequest.setCount(numberOfItems);
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
