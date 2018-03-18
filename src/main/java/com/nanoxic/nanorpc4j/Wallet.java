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
	 * Initialize the wallet using a wallet ID.
	 * 
	 * @param walletId
	 *            The wallet ID
	 */
	public Wallet(String walletId) {
		this.walletId = walletId;
	}

	// Getters
	/**
	 * Returns the wallet ID of this Wallet.
	 * 
	 * @return The wallet ID of this Wallet.
	 */
	public String getWalletId() {
		return walletId;
	}

	// Private methods
	private Balance getWalletBalancetotal() {
		return (Balance) HttpClient.getResponse(new RequestWallet("wallet_balance_total", walletId), Balance.class);
	}

	// Public methods
	/**
	 * Returns the sum of all Account balances in wallet in RAW.
	 * 
	 * @return The sum of all Account balances in wallet in RAW.
	 */
	public BigInteger getBalance() {
		return new BigInteger(getWalletBalancetotal().getBalance());
	}

	/**
	 * Returns the sum of all pending amounts in all accounts in wallet in RAW.
	 * 
	 * @return The sum of all pending amounts in all accounts in wallet in RAW.
	 */
	public BigInteger getPending() {
		return new BigInteger(getWalletBalancetotal().getPending());
	}

	/**
	 * Check whether wallet contains account.
	 * 
	 * @param account
	 *            The account to check.
	 * @return True if the account is part of the Wallet.
	 */
	public boolean contains(Account account) {
		return contains(account.getAddress());
	}

	/**
	 * Check whether wallet contains address.
	 * 
	 * @param address
	 *            The address to check.
	 * @return True if the address is part of the Wallet.
	 */
	public boolean contains(String address) {
		RequestContains containsRequest = new RequestContains("wallet_contains", walletId);
		containsRequest.setAccount(address);
		ResponseContains containsResponse = (ResponseContains) HttpClient.getResponse(containsRequest,
				ResponseContains.class);
		return (containsResponse.getExists() == 1);
	}

	/**
	 * Changes the password for wallet to password. enable_control required
	 * 
	 * @param password
	 *            The new password.
	 * @return True if the password was successfully changed.
	 */
	public boolean changePassword(String password) {
		RequestPassword requestPassword = new RequestPassword("password_change", walletId);
		requestPassword.setPassword(password);
		ResponseChanged responseChanged = (ResponseChanged) HttpClient.getResponse(requestPassword,
				ResponseChanged.class);
		return (responseChanged.getChanged() == 1);
	}

	/**
	 * Unlocks the Wallet.
	 * 
	 * @param password
	 *            The password to unlock the Wallet.
	 * @return True is successfully unlocked the Wallet.
	 */
	public boolean enterPassword(String password) {
		RequestPassword requestPassword = new RequestPassword("password_enter", walletId);
		requestPassword.setPassword(password);
		ResponseValid responseValid = (ResponseValid) HttpClient.getResponse(requestPassword, ResponseValid.class);
		return (responseValid.getValid() == 1);
	}

	/**
	 * Checks whether the password entered for Wallet is valid.
	 * 
	 * @return True if the valid password was provided.
	 */
	public boolean hasValidPassword() {
		ResponseValid responseValid = (ResponseValid) HttpClient
				.getResponse(new RequestWallet("password_valid", walletId), ResponseValid.class);
		return (responseValid.getValid() == 1);
	}

	/**
	 * Checks whether Wallet is locked.
	 * 
	 * @return True if the Wallet is locked.
	 */
	public boolean isLocked() {
		ResponseLocked lockedResponse = (ResponseLocked) HttpClient
				.getResponse(new RequestWallet("wallet_locked", walletId), ResponseLocked.class);
		return (lockedResponse.getLocked() == 1);
	}

	/**
	 * Returns a lists of all addresses inside this Wallet.
	 * 
	 * @return A list of addresses.
	 */
	public List<String> getAllAddresses() {
		ResponseListAccounts listAccountsResponse = (ResponseListAccounts) HttpClient
				.getResponse(new RequestWallet("account_list", walletId), ResponseListAccounts.class);
		return listAccountsResponse.getAccounts();
	}

	/**
	 * Returns a lists of all accounts inside this Wallet.
	 * 
	 * @return A list of accounts.
	 */
	public List<Account> getAllAccounts() {
		return getAllAddresses().stream().map(address -> new Account(address)).collect(Collectors.toList());
	}

	/**
	 * Creates a new address, insert next deterministic key in Wallet.
	 * enable_control required
	 * 
	 * @return The new address.
	 */
	public String createAddress() {
		ResponseCreateAccount createAccountResponse = (ResponseCreateAccount) HttpClient
				.getResponse(new RequestWallet("account_create", walletId), ResponseCreateAccount.class);
		return createAccountResponse.getAccount();
	}

	/**
	 * Creates a new account, insert next deterministic key in Wallet.
	 * enable_control required
	 * 
	 * @return The new account.
	 */
	public Account createAccount() {
		return new Account(createAddress());
	}

	// public List<String> createAddresses(int count) {
	// return createAddresses(count, true);
	// }
	//
	// public List<Account> createAccounts(int count) {
	//// return createAddresses(count, true);
	// return null;
	// }
	//
	// public List<String> createAddresses(int count, boolean work) {
	// return null;
	// }
	//
	// public List<Account> createAccounts(int count, boolean work) {
	// return null;
	// }
	//
	// public void getBalances(List<String> addresses) {
	// // "accounts_balances"
	// }
	//
	// public void getBalances(String... addresses) {
	// // "accounts_balances"
	// }

	/**
	 * Returns how many RAW is owned and how many have not yet been received by all
	 * addresses in Wallet.
	 * 
	 * @return A HashMap containing addresses and Balances.
	 */
	public HashMap<String, Balance> getBalances() {
		return getBalances(BigInteger.ZERO);
	}

	/**
	 * 
	 * Returns addresses in Wallet with balances more or equal to threshold.
	 * 
	 * @param threshold
	 *            The threshold for filtering.
	 * @return A HashMap containing addresses and Balances.
	 */
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

	/**
	 * Send amount from source Account in wallet to destination Account
	 * 
	 * @param sourceAccount
	 *            The account to send from, must be in current wallet
	 * @param destinationAccount
	 *            The address to send to
	 * @param amount
	 *            The amount to send
	 * @param id
	 *            A unique id for each spend to provide idempotency
	 * @return The send block that was generated
	 */
	public String send(Account sourceAccount, Account destinationAccount, BigInteger amount, String id) {
		return send(sourceAccount.getAddress(), destinationAccount.getAddress(), amount, id);
	}

	/**
	 * Receive pending block for Address in Wallet.
	 * 
	 * @param address
	 *            The Address in this Wallet the pending belongs to.
	 * @param block
	 *            The pending block
	 * @return The receive block
	 */
	public String receive(String address, String block) {
		RequestReceive requestReceive = new RequestReceive("receive", walletId);
		requestReceive.setAddress(address);
		requestReceive.setBlock(block);
		ResponseBlock sendResponse = (ResponseBlock) HttpClient.getResponse(requestReceive, ResponseBlock.class);
		return sendResponse.getBlock();
	}

	/**
	 * Receive pending block for Address in Wallet.
	 * 
	 * @param address
	 *            The Address in this Wallet the pending belongs to.
	 * @param block
	 *            The pending block
	 * @param work
	 *            A work value from an external source
	 * @return The receive block
	 */
	public String receive(String address, String block, String work) {
		RequestReceive requestReceive = new RequestReceive("receive", walletId);
		requestReceive.setAddress(address);
		requestReceive.setBlock(block);
		requestReceive.setWork(work);
		ResponseBlock sendResponse = (ResponseBlock) HttpClient.getResponse(requestReceive, ResponseBlock.class);
		return sendResponse.getBlock();
	}

	/**
	 * Receive pending block for Account in Wallet.
	 * 
	 * @param account
	 *            The Account in this Wallet the pending belongs to.
	 * @param block
	 *            The pending block
	 * @return The receive block
	 */
	public String receive(Account account, String block) {
		return receive(account.getAddress(), block);
	}

	/**
	 * Receive pending block for Account in Wallet.
	 * 
	 * @param account
	 *            The Account in this Wallet the pending belongs to.
	 * @param block
	 *            The pending block
	 * @param work
	 *            A work value from an external source
	 * @return The receive block
	 */
	public String receive(Account account, String block, String work) {
		return receive(account.getAddress(), block, work);
	}
}