package com.nanoxic.nanorpc4j.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nanoxic.nanorpc4j.Account;
import com.nanoxic.nanorpc4j.HistoryItem;
import com.nanoxic.nanorpc4j.Node;
import com.nanoxic.nanorpc4j.exceptions.InitializationException;

public class AccountTest {

	private static String publicKey;
	private static String address;

	@BeforeClass
	public static void generalSetup() throws ConfigurationException {
		// Load config
		PropertiesConfiguration config = new PropertiesConfiguration();
		config.load("application.properties");

		String hostname = config.getString("hostname");
		int port = config.getInt("port");

		publicKey = config.getString("publicKey");
		address = config.getString("address");

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

	@Test
	public void historyTest() {
		Account account = new Account(publicKey);
		List<HistoryItem> i = account.getHistory();
		List<HistoryItem> j = account.getHistory(6);

		assertEquals(i.size(), 5);
		assertEquals(j.size(), 6);
	}

}
