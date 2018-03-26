package com.nanoxic.nanorpc4j.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nanoxic.nanorpc4j.Account;
import com.nanoxic.nanorpc4j.HistoryItem;
import com.nanoxic.nanorpc4j.NANO;
import com.nanoxic.nanorpc4j.Node;
import com.nanoxic.nanorpc4j.Wallet;
import com.nanoxic.nanorpc4j.exceptions.InitializationException;
import com.nanoxic.nanorpc4j.exceptions.PasswordException;
import com.nanoxic.nanorpc4j.exceptions.UnattachedAccountException;

public class AccountTest {

	private static String publicKey;
	private static String address;
	private static String walletId;
	private static String password;

	@BeforeClass
	public static void generalSetup() throws ConfigurationException {
		// Load config
		PropertiesConfiguration config = new PropertiesConfiguration();
		config.load("application.properties");

		String hostname = config.getString("hostname");
		int port = config.getInt("port");

		publicKey = config.getString("publicKey");
		address = config.getString("address");
		walletId = config.getString("walletId");
		password = config.getString("password");

		// initialize the client
		Node.init(hostname, port);
	}

	// Constructor Tests
	@Test
	public void ValidAddressConstructorTest() {
		Account account = new Account(address);
		assertEquals(publicKey, account.getPublicKey());
	}

	@Test(expected = InitializationException.class)
	public void inValidAddressConstructorExceptionTest() {
		new Account("xrb_sqdf");
	}

	@Test
	public void ValidPublicKeyTest() {
		Account account = new Account(publicKey);
		assertEquals(address, account.getAddress());
	}

	@Test(expected = InitializationException.class)
	public void inValidPublicKeyTest() {
		new Account("XXXXXX");
	}

	// Methods tests
	@Test
	public void attachedTest() {
		Wallet wallet = new Wallet(walletId);
		assertTrue(wallet.getAccount().isAttached());
	}

	@Test
	public void historyTest() {
		Account account = new Account(publicKey);
		List<HistoryItem> i = account.getHistory();
		List<HistoryItem> j = account.getHistory(6);

		assertEquals(5, i.size());
		assertEquals(6, j.size());
	}

	// @Test // Actually send NANO
	// public void sendPasswordTest() {
	// Wallet wallet = new Wallet(walletId, password);
	// Account account = wallet.getAccount(address);
	// String block = account.send(new
	// Account("xrb_1roa5678jn1xjp89xus4e957zhpmqkmamp7ptuxwwdqf7xjbbaskn9ubou6o"),
	// new NANO("0.0025"), RandomStringUtils.randomAlphanumeric(16));
	// assertNotNull(block);
	// }

	@Test(expected = PasswordException.class)
	public void sendNoPasswordTest() throws InterruptedException {
		Wallet wallet = new Wallet(walletId);
		Account account = wallet.getAccount(address);
		wallet.enterPassword("WRONG-PASWORD");
		account.send(new Account("xrb_1roa5678jn1xjp89xus4e957zhpmqkmamp7ptuxwwdqf7xjbbaskn9ubou6o"),
				new NANO("0.0025"), RandomStringUtils.randomAlphanumeric(16));
	}

	@Test(expected = UnattachedAccountException.class)
	public void sendUnattachedAccountTest() {
		Account account = new Account(address);
		String block = account.send(new Account("xrb_1roa5678jn1xjp89xus4e957zhpmqkmamp7ptuxwwdqf7xjbbaskn9ubou6o"),
				new NANO("0.0025"), RandomStringUtils.randomAlphanumeric(16));
		assertNotNull(block);
	}

	// @Test
	// public void test() {
	// Account account = new Account(address);
	// System.out.println(account.getLastModified());
	// }
}
