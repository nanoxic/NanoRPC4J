package com.nanoxic.nanorpc4j.examples;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.nanoxic.nanorpc4j.NanoRPC4J;
import com.nanoxic.nanorpc4j.model.account.Account;
import com.nanoxic.nanorpc4j.model.wallet.Wallet;

public class WalletTest {

	public static void main(String[] args) throws ConfigurationException {
		// Load config
		PropertiesConfiguration config = new PropertiesConfiguration();
		config.load("application.properties");

		String address = config.getString("address");
		String walletId = config.getString("wallet");
		String hostname = config.getString("hostname");
		int port = config.getInt("port");

		// initialize the client
		NanoRPC4J.init(hostname, port);

		// show functions
		Wallet wallet = new Wallet(walletId);
		System.out.println(wallet.getBalance());
		System.out.println(wallet.getPending());
		System.out.println(wallet.contains(address));
		System.out.println(wallet.isLocked());
		// Will generate a new account in the wallet
		// System.out.println(wallet.createAccount().getAccount());
		System.out.println();
		List<String> addresses = wallet.getAllAddresses();
		for (String addressItem : addresses) {
			System.out.println(addressItem);
		}
		System.out.println();
		List<Account> accounts = wallet.getAllAccounts();
		for (Account addressItem : accounts) {
			System.out.println(addressItem.getAccount() + " " + addressItem.getPublicKey());
		}
	}
}
