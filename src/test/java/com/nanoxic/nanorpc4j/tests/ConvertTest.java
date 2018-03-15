package com.nanoxic.nanorpc4j.tests;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nanoxic.nanorpc4j.Convert;
import com.nanoxic.nanorpc4j.Node;

public class ConvertTest {

	@BeforeClass
	public static void generalSetup() throws ConfigurationException {
		// Load config
		PropertiesConfiguration config = new PropertiesConfiguration();
		config.load("application.properties");

		String hostname = config.getString("hostname");
		int port = config.getInt("port");

		// initialize the client
		Node.init(hostname, port);
	}

	@Test
	public void raiFromRawTest() {
		BigInteger rawAmount = new BigInteger("1000000000000000000000000");
		BigInteger raiAmountExpected = new BigInteger("1");
		BigInteger raiAmountActual = Convert.raiFromRaw(rawAmount);
		assertEquals(raiAmountExpected, raiAmountActual);
	}

	@Test
	public void raiToRawTest() {
		BigInteger raiAmount = new BigInteger("1");
		BigInteger rawAmountExpected = new BigInteger("1000000000000000000000000");
		BigInteger rawAmountActual = Convert.raiToRaw(raiAmount);
		assertEquals(rawAmountExpected, rawAmountActual);
	}

	@Test
	public void kraiFromRawTest() {
		BigInteger rawAmount = new BigInteger("1000000000000000000000000000");
		BigInteger kraiAmountExpected = new BigInteger("1");
		BigInteger kraiAmountActual = Convert.kraiFromRaw(rawAmount);
		assertEquals(kraiAmountExpected, kraiAmountActual);
	}

	@Test
	public void kraiToRawTest() {
		BigInteger kraiAmount = new BigInteger("1");
		BigInteger rawAmountExpected = new BigInteger("1000000000000000000000000000");
		BigInteger rawAmountActual = Convert.kraiToRaw(kraiAmount);
		assertEquals(rawAmountExpected, rawAmountActual);
	}

	@Test
	public void mraiFromRawTest() {
		BigInteger rawAmount = new BigInteger("1000000000000000000000000000000");
		BigInteger mraiAmountExpected = new BigInteger("1");
		BigInteger mraiAmountActual = Convert.mraiFromRaw(rawAmount);
		assertEquals(mraiAmountExpected, mraiAmountActual);
	}

	@Test
	public void mraiToRawTest() {
		BigInteger mraiAmount = new BigInteger("1");
		BigInteger rawAmountExpected = new BigInteger("1000000000000000000000000000000");
		BigInteger rawAmountActual = Convert.mraiToRaw(mraiAmount);
		assertEquals(rawAmountExpected, rawAmountActual);
	}
}
