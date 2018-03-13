package com.nanoxic.nanorpc4j.model.wallet;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import com.nanoxic.nanorpc4j.http.HttpClient;
import com.nanoxic.nanorpc4j.messages.BalanceResponse;
import com.nanoxic.nanorpc4j.model.account.Account;

public class Wallet {

	private String wallet;

	public Wallet(String wallet) {
		this.wallet = wallet;
	}

	public BigInteger getBalance() {
		BalanceResponse balanceResponse = (BalanceResponse) HttpClient
				.getResponse(new WalletRequest("wallet_balance_total", wallet), BalanceResponse.class);
		return new BigInteger(balanceResponse.getBalance());
	}

	public BigInteger getPending() {
		BalanceResponse balanceResponse = (BalanceResponse) HttpClient
				.getResponse(new WalletRequest("wallet_balance_total", wallet), BalanceResponse.class);
		return new BigInteger(balanceResponse.getPending());
	}

	public boolean contains(String address) {
		ContainsRequest containsRequest = new ContainsRequest("wallet_contains", wallet);
		containsRequest.setAccount(address);
		ContainsResponse containsResponse = (ContainsResponse) HttpClient.getResponse(containsRequest,
				ContainsResponse.class);
		return (containsResponse.getExists() == 1);
	}

	public boolean isLocked() {
		LockedResponse lockedResponse = (LockedResponse) HttpClient
				.getResponse(new WalletRequest("wallet_locked", wallet), LockedResponse.class);
		return (lockedResponse.getLocked() == 1);
	}

	public List<String> getAllAddresses() {
		ListAccountsResponse listAccountsResponse = (ListAccountsResponse) HttpClient
				.getResponse(new WalletRequest("account_list", wallet), ListAccountsResponse.class);
		return listAccountsResponse.getAccounts();
	}

	public List<Account> getAllAccounts() {
		return getAllAddresses().stream().map(address -> new Account(address)).collect(Collectors.toList());
	}

	public String createAddress() {
		CreateAccountResponse createAccountResponse = (CreateAccountResponse) HttpClient
				.getResponse(new WalletRequest("account_create", wallet), CreateAccountResponse.class);
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

	public void getBalances() {
		// "action": "wallet_balances"
	}

	public void getBalances(BigInteger threshold) {
		// "action": "wallet_balances"
	}

	public void SearchPending() {
		// "action": "search_pending"
	}
}