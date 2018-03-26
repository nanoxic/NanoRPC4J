package com.nanoxic.nanorpc4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.nanoxic.nanorpc4j.exceptions.InitializationException;
import com.nanoxic.nanorpc4j.exceptions.PasswordException;

/**
 * Class representing a single Nano wallet
 * 
 * @author Koen De Voegt
 *
 */
public class Wallet {

	private String walletId;
	private String password;

	// Constructor
	/**
	 * Initialize the wallet using a wallet ID.
	 * 
	 * @param walletId
	 *            The wallet ID
	 */
	public Wallet(String walletId) {
		if (Node.contains(walletId))
			this.walletId = walletId;
		else
			throw new InitializationException("Not a valid walletId for this Node");
	}

	/**
	 * Initialize the wallet using a wallet ID and password.
	 * 
	 * @param walletId
	 *            The wallet ID
	 * @param password
	 *            The password of this Wallet
	 */
	public Wallet(String walletId, String password) {
		this(walletId);
		if (!this.enterPassword(password))
			throw new PasswordException("Incorrect password provided");
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

	// Public methods
	/**
	 * Returns the sum of all Account balances in wallet in RAW.
	 * 
	 * @return The sum of all Account balances in wallet in RAW.
	 */
	public NANO getBalance() {
		return getWalletBalancetotal().getBalance();
	}

	/**
	 * Returns the sum of all pending amounts in all accounts in wallet in RAW.
	 * 
	 * @return The sum of all pending amounts in all accounts in wallet in RAW.
	 */
	public NANO getPending() {
		return getWalletBalancetotal().getPending();
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
		unLockIfNecessary();
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
	 * Returns the first Account in this Wallet.
	 * 
	 * @return The first Account in this Wallet.
	 */
	public Account getAccount() {
		return getAccount(0);
	}

	/**
	 * Returns the Account at given index in this Wallet.
	 * 
	 * @param index
	 *            The index of the Account to retrieve.
	 * @return The Account at given index in this Wallet.
	 */
	public Account getAccount(int index) {
		return getAllAccounts().get(index);
	}
	// TODO error handling + testing

	/**
	 * Returns the Account with given address if it is part of this Wallet
	 * 
	 * @param address
	 *            The address of the Account to create.
	 * @return The Account with given address.
	 */
	public Account getAccount(String address) {
		if (contains(address))
			return new Account(address, this);
		else
			return null;
	}

	/**
	 * Returns a lists of all accounts inside this Wallet.
	 * 
	 * @return A list of accounts.
	 */
	public List<Account> getAllAccounts() {
		return getAllAddresses().stream().map(address -> new Account(address, this)).collect(Collectors.toList());
	}

	/**
	 * Creates a new account, insert next deterministic key in Wallet.
	 * enable_control required
	 * 
	 * @return The new account.
	 */
	public Account createAccount() {
		return new Account(createAddress(), this);
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
	 * Returns how many NANO is owned and how many have not yet been received by all
	 * addresses in Wallet.
	 * 
	 * @return A HashMap containing Accounts and Balances.
	 */
	public HashMap<Account, Balance> getBalances() {
		return getBalances(NANO.ZERO);
	}

	/**
	 * 
	 * Returns Accounts in Wallet with balances more or equal to threshold.
	 * 
	 * @param threshold
	 *            The threshold for filtering.
	 * @return A HashMap containing Accounts and Balances.
	 */
	public HashMap<Account, Balance> getBalances(NANO threshold) {
		RequestBalance balanceRequest = new RequestBalance("wallet_balances", walletId);
		balanceRequest.setThreshold(threshold.getRAW());
		ResponseBalance balanceResponse = (ResponseBalance) HttpClient.getResponse(balanceRequest,
				ResponseBalance.class);

		Map<Account, Balance> y = balanceResponse.getBalances().entrySet().stream()
				.collect(Collectors.toMap(e -> (Account) new Account(e.getKey()), e -> (Balance) e.getValue()));

		return (HashMap<Account, Balance>) y;
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

	// Private methods
	private Balance getWalletBalancetotal() {
		return (Balance) HttpClient.getResponse(new RequestWallet("wallet_balance_total", walletId), Balance.class);
	}

	private List<String> getAllAddresses() {
		ResponseListAccounts listAccountsResponse = (ResponseListAccounts) HttpClient
				.getResponse(new RequestWallet("account_list", walletId), ResponseListAccounts.class);
		return listAccountsResponse.getAccounts();
	}

	private String createAddress() {
		ResponseCreateAccount createAccountResponse = (ResponseCreateAccount) HttpClient
				.getResponse(new RequestWallet("account_create", walletId), ResponseCreateAccount.class);
		return createAccountResponse.getAccount();
	}

	protected boolean unLockIfNecessary() {
		if (!isLocked() && (password == null)) {
			return true;
		} else
			throw new PasswordException("No password provided");
	}
}