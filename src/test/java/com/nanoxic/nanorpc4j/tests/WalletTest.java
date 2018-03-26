package com.nanoxic.nanorpc4j.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nanoxic.nanorpc4j.Node;
import com.nanoxic.nanorpc4j.Wallet;
import com.nanoxic.nanorpc4j.exceptions.InitializationException;
import com.nanoxic.nanorpc4j.exceptions.PasswordException;

public class WalletTest {

	private static String walletId;
	private static String address;
	private static String password;

	@BeforeClass
	public static void generalSetup() throws ConfigurationException {
		// Load config
		PropertiesConfiguration config = new PropertiesConfiguration();
		config.load("application.properties");

		String hostname = config.getString("hostname");
		int port = config.getInt("port");

		walletId = config.getString("walletId");
		address = config.getString("address");
		password = config.getString("password");

		// initialize the client
		Node.init(hostname, port);
	}

	// Constructor Tests
	@Test
	public void validWalletIdTest() {
		new Wallet(walletId);
	}

	@Test(expected = InitializationException.class)
	public void invalidWalletIdTest() {
		new Wallet("XXXXXX");
	}

	@Test
	public void passwordIdTest() {
		new Wallet(walletId, password);
	}

	@Test(expected = PasswordException.class)
	public void wrongPasswordTest() {
		new Wallet(walletId, "WRONG-PASWORD");
	}

	// Methods tests
	@Test
	public void changePasswordNoPasswordTest() {
		Wallet wallet = new Wallet(walletId);
		wallet.enterPassword("WRONG-PASWORD");
		assertThrows(PasswordException.class, () -> {
			wallet.changePassword("newpassword");
		});
	}

	@Test
	public void changePasswordTest() {
		Wallet wallet = new Wallet(walletId);
		assertTrue(wallet.changePassword("newpassword"));
		wallet.changePassword(password);
	}

	// @Test
	// public void test() {
	// Wallet wallet = new Wallet(walletId);
	// HashMap<Account, Balance> i = wallet.getBalances();
	// for (Entry<Account, Balance> j : i.entrySet()) {
	// System.out.println(j.getKey().getAddress() + " " + j.getValue());
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
