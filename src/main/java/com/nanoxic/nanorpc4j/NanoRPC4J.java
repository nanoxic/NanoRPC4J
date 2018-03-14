package com.nanoxic.nanorpc4j;

import com.nanoxic.nanorpc4j.exception.InitializationException;

/**
 * 
 * Singleton class to initialize NanoRPC4J
 * 
 * @author Koen De Voegt
 *
 */
public class NanoRPC4J {

	private static String hostname;
	private static int port = 7076;

	private NanoRPC4J() {
	}

	/**
	 * Initialize the NanoRPC4J environment.
	 *
	 * @param hostname
	 *            The hostname of the Rai/Nano node to use
	 */
	public static void init(final String hostname) {
		NanoRPC4J.setHostname(hostname);
	}

	/**
	 * Initialize the NanoRPC4J environment.
	 *
	 * @param hostname
	 *            The hostname of the Rai/Nano node to use
	 * @param port
	 *            The RPC port of the Rai/Nano node to use
	 */
	public static void init(final String hostname, final int port) {
		NanoRPC4J.setHostname(hostname);
		NanoRPC4J.setPort(port);
	}

	/**
	 * Returns the hostname
	 * 
	 * @return hostname
	 */
	public static String getHostname() {
		return hostname;
	}

	/**
	 * Set the hostname.
	 *
	 * @param hostname
	 *            The hostname of the Rai/Nano node to use
	 * @throws InitializationException
	 *             if hostname is null
	 */
	private static void setHostname(final String hostname) {
		if (hostname == null) {
			throw new InitializationException("Hostname can not be null");
		}
		NanoRPC4J.hostname = hostname;
	}

	/**
	 * Return the port
	 * 
	 * @return port
	 */
	public static int getPort() {
		return port;
	}

	/**
	 * Set the port
	 * 
	 * @param port
	 *            The RPC port of the Rai/Nano node to use
	 */
	private static void setPort(final int port) {
		NanoRPC4J.port = port;
	}

}
