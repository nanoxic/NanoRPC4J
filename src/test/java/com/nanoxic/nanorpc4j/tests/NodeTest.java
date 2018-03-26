package com.nanoxic.nanorpc4j.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nanoxic.nanorpc4j.Node;

public class NodeTest {
	
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
	
	// Methods tests
	@Test
	public void containsTest() {
		assertTrue(Node.contains(walletId));
	} 
	
	@Test
	public void doesntContainTest() {
		assertFalse(Node.contains("XXXXXX"));
	} 
}
