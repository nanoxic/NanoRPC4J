package com.nanoxic.nanorpc4j;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class representing a single Nano wallet
 * 
 * @author Koen De Voegt
 *
 */
public class Wallet {

	private String walletId;

	// Constructor
	/**
	 * Initialize the wallet using a wallet ID
	 * 
	 * @param walletId
	 *            The wallet ID
	 */
	public Wallet(String walletId) {
		this.walletId = walletId;
	}

	// Getters and Setters
	public String getWalletId() {
		return walletId;
	}

	// Methodes
	/**
	 * Returns the sum of all accounts balances in wallet
	 * 
	 * @return The sum of all accounts balances in wallet
	 */
	public BigInteger getBalance() {
		Balance balanceResponse = (Balance) HttpClient.getResponse(new RequestWallet("wallet_balance_total", walletId),
				Balance.class);
		return new BigInteger(balanceResponse.getBalance());
	}

	/**
	 * Returns the sum of all pending amounts in all accounts in wallet
	 * 
	 * @return The sum of all pending amounts in all accounts in wallet
	 */
	public BigInteger getPending() {
		Balance balanceResponse = (Balance) HttpClient.getResponse(new RequestWallet("wallet_balance_total", walletId),
				Balance.class);
		return new BigInteger(balanceResponse.getPending());
	}

	public boolean contains(Account account) {
		return contains(account.getAddress());
	}

	public boolean contains(String address) {
		RequestContains containsRequest = new RequestContains("wallet_contains", walletId);
		containsRequest.setAccount(address);
		ResponseContains containsResponse = (ResponseContains) HttpClient.getResponse(containsRequest,
				ResponseContains.class);
		return (containsResponse.getExists() == 1);
	}

	public boolean changePassword(String password) {
		RequestPassword requestPassword = new RequestPassword("password_change", walletId);
		requestPassword.setPassword(password);
		ResponseChanged responseChanged = (ResponseChanged) HttpClient.getResponse(requestPassword,
				ResponseChanged.class);
		return (responseChanged.getChanged() == 1);
	}

	public boolean enterPassword(String password) {
		RequestPassword requestPassword = new RequestPassword("password_enter", walletId);
		requestPassword.setPassword(password);
		ResponseValid responseValid = (ResponseValid) HttpClient.getResponse(requestPassword, ResponseValid.class);
		return (responseValid.getValid() == 1);
	}

	public boolean hasValidPassword() {
		ResponseValid responseValid = (ResponseValid) HttpClient
				.getResponse(new RequestWallet("password_valid", walletId), ResponseValid.class);
		return (responseValid.getValid() == 1);
	}

	public boolean isLocked() {
		ResponseLocked lockedResponse = (ResponseLocked) HttpClient
				.getResponse(new RequestWallet("wallet_locked", walletId), ResponseLocked.class);
		return (lockedResponse.getLocked() == 1);
	}

	public List<String> getAllAddresses() {
		ResponseListAccounts listAccountsResponse = (ResponseListAccounts) HttpClient
				.getResponse(new RequestWallet("account_list", walletId), ResponseListAccounts.class);
		return listAccountsResponse.getAccounts();
	}

	public List<Account> getAllAccounts() {
		return getAllAddresses().stream().map(address -> new Account(address)).collect(Collectors.toList());
	}

	public String createAddress() {
		ResponseCreateAccount createAccountResponse = (ResponseCreateAccount) HttpClient
				.getResponse(new RequestWallet("account_create", walletId), ResponseCreateAccount.class);
		return createAccountResponse.getAccount();
	}

	public Account createAccount() {
		return new Account(createAddress());
	}

	public List<String> createAddresses(int count) {
		return null;
	}

	public List<Account> createAccounts(int count) {
		return null;
	}

	public List<String> createAddresses(int count, boolean work) {
		return null;
	}

	public List<Account> createAccounts(int count, boolean work) {
		return null;
	}

	public void getBalances(List<String> addresses) {
		// "accounts_balances"
	}

	public void getBalances(String... addresses) {
		// "accounts_balances"
	}

	/**
	 * Returns how many rai is owned and how many have not yet been received by all
	 * accounts in wallet
	 * 
	 * @return vqsdfdqs TODO dsf
	 */
	public HashMap<String, Balance> getBalances() {
		return getBalances(BigInteger.ZERO);
	}

	public HashMap<String, Balance> getBalances(BigInteger threshold) {
		RequestBalance balanceRequest = new RequestBalance("wallet_balances", walletId);
		balanceRequest.setThreshold(threshold);
		ResponseBalance balanceResponse = (ResponseBalance) HttpClient.getResponse(balanceRequest,
				ResponseBalance.class);
		return balanceResponse.getBalances();
	}

	/**
	 * Tells the node to look for pending blocks for any account in wallet
	 * 
	 * @return True if command was correctly started
	 */
	public boolean SearchPending() {
		ResponsePending pendingResponse = (ResponsePending) HttpClient
				.getResponse(new RequestWallet("search_pending", walletId), ResponsePending.class);
		return (pendingResponse.getStarted() == 1);
	}

	/**
	 * Send amount from source Address in wallet to destination Address
	 * 
	 * @param sourceAddress
	 *            The address to send from, must be in current wallet
	 * @param destinationAddress
	 *            The address to send to
	 * @param amount
	 *            The amount to send
	 * @param id
	 *            A unique id for each spend to provide idempotency
	 * @return The send block that was generated
	 */
	public String send(String sourceAddress, String destinationAddress, BigInteger amount, String id) {
		RequestSend requestSend = new RequestSend("send", walletId);
		requestSend.setSource(sourceAddress);
		requestSend.setDestination(destinationAddress);
		requestSend.setAmount(amount);
		requestSend.setId(id);
		ResponseBlock sendResponse = (ResponseBlock) HttpClient.getResponse(requestSend, ResponseBlock.class);
		return sendResponse.getBlock();
	}

	public String send(Account sourceAccount, Account destinationAccount, BigInteger amount, String id) {
		return send(sourceAccount.getAddress(), destinationAccount.getAddress(), amount, id);
	}

	/**
	 * Receive pending block for account in wallet
	 * 
	 * @param address
	 * @param block
	 * @return
	 */
	public String receive(String address, String block) {
		RequestReceive requestReceive = new RequestReceive("receive", walletId);
		requestReceive.setAddress(address);
		requestReceive.setBlock(block);
		ResponseBlock sendResponse = (ResponseBlock) HttpClient.getResponse(requestReceive, ResponseBlock.class);
		return sendResponse.getBlock();
	}

	public String receive(String address, String block, String work) {
		RequestReceive requestReceive = new RequestReceive("receive", walletId);
		requestReceive.setAddress(address);
		requestReceive.setBlock(block);
		requestReceive.setWork(work);
		ResponseBlock sendResponse = (ResponseBlock) HttpClient.getResponse(requestReceive, ResponseBlock.class);
		return sendResponse.getBlock();
	}
	
	public String receive(Account account, String block) {
		return receive(account.getAddress(), block);
	}
}