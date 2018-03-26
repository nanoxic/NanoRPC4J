package com.nanoxic.nanorpc4j;

import java.math.BigInteger;
import java.util.List;

import com.nanoxic.nanorpc4j.exceptions.InitializationException;
import com.nanoxic.nanorpc4j.exceptions.ResponseException;
import com.nanoxic.nanorpc4j.exceptions.UnattachedAccountException;

/**
 * Class representing a single Nano account
 * 
 * @author Koen De Voegt
 *
 */
public class Account {

	private String address;
	private String publicKey;
	private Wallet wallet;

	// Constructor
	/**
	 * Construct a detached Account using either an address or a public key. A
	 * detached Account is an Account that is not part of any Wallet, it can be used
	 * as a receiver of funds. Use Wallet.getAccount() to get an attached Account.
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
			try {
				this.address = getAddress(accountKey);
				this.publicKey = accountKey;
			} catch (ResponseException e) {
				throw new InitializationException("Not a valid NANO public key");
			}
		}
	}

	Account(String accountKey, Wallet wallet) {
		this(accountKey);
		this.wallet = wallet;
	}

	// Getters
	/**
	 * Returns the address of the Account
	 * 
	 * @return The address of the Account
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Returns the public key of the Account
	 * 
	 * @return The public key of the Account
	 */
	public String getPublicKey() {
		return publicKey;
	}

	/**
	 * Returns the Wallet this Account belongs to
	 * 
	 * @return The Wallet this account belongs to
	 */
	public Wallet getWallet() {
		return wallet;
	}

	// Public methods
	/**
	 * Returns whether or not this Account is part of a Wallet
	 * 
	 * @return True when this account is part of a Wallet
	 */
	public boolean isAttached() {
		return (wallet != null);
	}

	/**
	 * Returns how many RAW is owned by this account
	 * 
	 * @return The amount of RAW owned by this account
	 */
	public NANO getBalance() {
		Balance balanceResponse = (Balance) HttpClient.getResponse(new RequestAccount("account_balance", address),
				Balance.class);
		return balanceResponse.getBalance();
	}

	/**
	 * Returns how many RAW have not yet been received by this account
	 * 
	 * @return The amount of RAW pending for this account
	 */
	public NANO getPending() {
		Balance balanceResponse = (Balance) HttpClient.getResponse(new RequestAccount("account_balance", address),
				Balance.class);
		return balanceResponse.getPending();
	}

	/**
	 * Get number of blocks for this account
	 * 
	 * @return Number of blocks for this account
	 */
	public int getBlockCount() {
		ResponseBlockCount blockCountResponse = (ResponseBlockCount) HttpClient
				.getResponse(new RequestAccount("account_block_count", address), ResponseBlockCount.class);
		return Integer.valueOf(blockCountResponse.getBlock_count());
	}

	/**
	 * Returns the voting weight for this account
	 * 
	 * @return The voting weight for this account
	 */
	public BigInteger getWeight() {
		ResponseWeight weightResponse = (ResponseWeight) HttpClient
				.getResponse(new RequestAccount("account_weight", address), ResponseWeight.class);
		return new BigInteger(weightResponse.getWeight());
	}

	/**
	 * Returns the frontier for this account
	 * 
	 * @return The frontier for account
	 */
	public String getFrontier() {
		return getInfo().getFrontier();
	}

	/**
	 * Returns the open block for this account
	 * 
	 * @return The open block for account
	 */
	public String getOpenBlock() {
		return getInfo().getOpen_block();
	}

	/**
	 * Returns the representative block for this account
	 * 
	 * @return The representative block for account
	 */
	public String getReprsentativeBlock() {
		return getInfo().getRepresentative_block();
	}

	/**
	 * Returns the last modified time for this account
	 * 
	 * @return The last modified time for account
	 */
	public String getLastModified() { // TODO String????
		return getInfo().getModified_timestamp();
	}

	/**
	 * Returns frontier, open block, change representative block, balance, last
	 * modified timestamp from local database &amp; block count for account.
	 * Optionally returns representative, voting weight, pending balance for account
	 * 
	 * @param representative
	 *            Include the representative in the AccountInfo object
	 * @param weight
	 *            Include the weight in the AccountInfo object
	 * @param pending
	 *            Include the pending in the AccountInfo object
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

	/**
	 * Send amount from this Account to destination Account. Sending Account must be
	 * attached to a Wallet.
	 * 
	 * @param destinationAccount
	 *            The Account to send to
	 * @param amount
	 *            The amount to send
	 * @param id
	 *            A unique id for each spend to provide idempotency
	 * @return The send block that was generated
	 */
	public String send(Account destinationAccount, NANO amount, String id) {
		if (isAttached()) {
			RequestSend requestSend = new RequestSend("send", wallet.getWalletId());
			requestSend.setSource(address);
			requestSend.setDestination(destinationAccount.getAddress());
			requestSend.setAmount(amount.getRAW());
			requestSend.setId(id);
			wallet.unLockIfNecessary();
			ResponseBlock sendResponse = (ResponseBlock) HttpClient.getResponse(requestSend, ResponseBlock.class);
			return sendResponse.getBlock();
		} else
			throw new UnattachedAccountException("Current Account is not part of a Wallet.");
	}

	/**
	 * Receive pending block.
	 * 
	 * @param block
	 *            The pending block
	 * @return The receive block
	 */
	public String receive(String block) {
		return receive(block, null);
	}

	/**
	 * Receive pending block using work from external source.
	 * 
	 * @param block
	 *            The pending block
	 * @param work
	 *            A work value from an external source
	 * @return The receive block
	 */
	public String receive(String block, String work) {
		RequestReceive requestReceive = new RequestReceive("receive", wallet.getWalletId());
		requestReceive.setAddress(address);
		requestReceive.setBlock(block);
		if (work != null)
			requestReceive.setWork(work);
		ResponseBlock sendResponse = (ResponseBlock) HttpClient.getResponse(requestReceive, ResponseBlock.class);
		return sendResponse.getBlock();
	} // TODO test receive functions

	// Private methods
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

	private AccountInfo getInfo() {
		return (AccountInfo) HttpClient.getResponse(new RequestAccount("account_info", address), AccountInfo.class);
	}
}
