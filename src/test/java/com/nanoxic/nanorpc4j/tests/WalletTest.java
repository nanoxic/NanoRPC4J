package com.nanoxic.nanorpc4j.tests;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nanoxic.nanorpc4j.Node;
import com.nanoxic.nanorpc4j.Wallet;

public class WalletTest {

	private static String walletId;

	@BeforeClass
	public static void generalSetup() throws ConfigurationException {
		// Load config
		PropertiesConfiguration config = new PropertiesConfiguration();
		config.load("application.properties");

		String hostname = config.getString("hostname");
		int port = config.getInt("port");

		walletId = config.getString("walletId");

		// initialize the client
		Node.init(hostname, port);
	}

	@Test
	public void validWalletIdTest() {
		System.out.println(walletId);
		// Wallet wallet =
		new Wallet(walletId);
		// List<String> i = wallet.getAllAddresses();
		// for (String address : i) {
		// System.out.println(address);
		// }
	}

	// @Test
	// public void invalidWalletIdTest() {
	// System.out.println(walletId);
	// Wallet wallet = new Wallet("XXXXXX");
	// List<String> i = wallet.getAllAddresses();
	// System.out.println(i);
	// System.out.println(wallet.isLocked());
	// System.out.println(wallet.getBalance());
	// for (String address : i) {
	// System.out.println(address);
	// }
	// }

	// @Test
	// public void Test() {
	// Account account = new
	// Account("xrb_39afy3dbjdoeskk1x84imwrp59ubn9ghafyzpt67t7inyujn5xs1t5xptueq");
	// Wallet wallet = new Wallet(walletId);
	//
	// account.getBalance();
	// assertEquals(new BigInteger("5191855022866341132517769216"),
	// account.getBalance());
	// assertEquals(new BigInteger("5191855022866341132517769216"),
	// wallet.getBalance());
	// }
}
