package com.nanoxic.nanorpc4j.model.wallet;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.nanoxic.nanorpc4j.http.HttpClient;
import com.nanoxic.nanorpc4j.messages.Balance;
import com.nanoxic.nanorpc4j.model.account.Account;

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
		Balance balanceResponse = (Balance) HttpClient.getResponse(new WalletRequest("wallet_balance_total", walletId),
				Balance.class);
		return new BigInteger(balanceResponse.getBalance());
	}

	/**
	 * Returns the sum of all pending amounts in all accounts in wallet
	 * 
	 * @return The sum of all pending amounts in all accounts in wallet
	 */
	public BigInteger getPending() {
		Balance balanceResponse = (Balance) HttpClient.getResponse(new WalletRequest("wallet_balance_total", walletId),
				Balance.class);
		return new BigInteger(balanceResponse.getPending());
	}

	public boolean contains(String address) {
		ContainsRequest containsRequest = new ContainsRequest("wallet_contains", walletId);
		containsRequest.setAccount(address);
		ContainsResponse containsResponse = (ContainsResponse) HttpClient.getResponse(containsRequest,
				ContainsResponse.class);
		return (containsResponse.getExists() == 1);
	}

	public boolean isLocked() {
		LockedResponse lockedResponse = (LockedResponse) HttpClient
				.getResponse(new WalletRequest("wallet_locked", walletId), LockedResponse.class);
		return (lockedResponse.getLocked() == 1);
	}

	public List<String> getAllAddresses() {
		ListAccountsResponse listAccountsResponse = (ListAccountsResponse) HttpClient
				.getResponse(new WalletRequest("account_list", walletId), ListAccountsResponse.class);
		return listAccountsResponse.getAccounts();
	}

	public List<Account> getAllAccounts() {
		return getAllAddresses().stream().map(address -> new Account(address)).collect(Collectors.toList());
	}

	public String createAddress() {
		CreateAccountResponse createAccountResponse = (CreateAccountResponse) HttpClient
				.getResponse(new WalletRequest("account_create", walletId), CreateAccountResponse.class);
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
		BalanceRequest balanceRequest = new BalanceRequest("wallet_balances", walletId);
		balanceRequest.setThreshold(threshold);
		BalanceResponse balanceResponse = (BalanceResponse) HttpClient.getResponse(balanceRequest,
				BalanceResponse.class);
		return balanceResponse.getBalances();
	}

	/**
	 * Tells the node to look for pending blocks for any account in wallet
	 * 
	 * @return True if command was correctly started
	 */
	public boolean SearchPending() {
		PendingResponse pendingResponse = (PendingResponse) HttpClient
				.getResponse(new WalletRequest("search_pending", walletId), PendingResponse.class);
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
		SendRequest sendRequest = new SendRequest("send", walletId);
		sendRequest.setSource(sourceAddress);
		sendRequest.setDestination(destinationAddress);
		sendRequest.setAmount(amount);
		sendRequest.setId(id);
		SendResponse sendResponse = (SendResponse) HttpClient.getResponse(sendRequest, SendResponse.class);
		return sendResponse.getBlock();
	}

	public String send(Account sourceAccount, Account destinationAccount, BigInteger amount, String id) {
		return send(sourceAccount.getAddress(), destinationAccount.getAddress(), amount, id);
	}
}