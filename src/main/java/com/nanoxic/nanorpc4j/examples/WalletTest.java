package com.nanoxic.nanorpc4j.examples;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.nanoxic.nanorpc4j.NanoRPC4J;
import com.nanoxic.nanorpc4j.messages.Balance;
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
			System.out.println(addressItem.getAddress() + " " + addressItem.getPublicKey());
		}
		System.out.println();
		HashMap<String, Balance> j = wallet.getBalances(new BigInteger("1"));
		Iterator<?> it = j.entrySet().iterator();
		int counter = 6; // generate unique ids for production use
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			String mainWallet = "xrb_39afy3dbjdoeskk1x84imwrp59ubn9ghafyzpt67t7inyujn5xs1t5xptueq";
			if (!pair.getKey().equals(mainWallet)) {
				wallet.send(pair.getKey().toString(), mainWallet, new BigInteger(((Balance) pair.getValue()).getBalance()), Integer.toString(counter));
				counter++;
			}

			it.remove(); // avoids a ConcurrentModificationException
		}

	}
}
