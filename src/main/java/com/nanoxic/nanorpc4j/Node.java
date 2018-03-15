package com.nanoxic.nanorpc4j;

import java.math.BigInteger;

import com.nanoxic.nanorpc4j.exception.InitializationException;

/**
 * 
 * Singleton class to initialize NanoRPC4J representing the Nano Node.
 * 
 * @author Koen De Voegt
 *
 */
public class Node {

	private static String hostname;
	private static int port = 7076;
	private static ResponseVersions responseVersions = null;

	// Constructor
	private Node() {
	}

	// Inialisation methodes
	/**
	 * Initialize the connection to the Nano Node. Using default port 1076 on
	 * localhost.
	 */
	public static void init() {
	}

	/**
	 * Initialize the connection to the Nano Node.
	 *
	 * @param hostname
	 *            The hostname of the Rai/Nano node to use.
	 */
	public static void init(final String hostname) {
		if (hostname == null) {
			throw new InitializationException("Hostname can not be null");
		}
		Node.hostname = hostname;
	}

	/**
	 * Initialize the connection to the Nano Node.
	 *
	 * @param hostname
	 *            The hostname of the Rai/Nano node to use.
	 * @param port
	 *            The RPC port of the Rai/Nano node to use.
	 */
	public static void init(final String hostname, final int port) {
		init(hostname);
		Node.port = port;
	}

	// Getters and Setters
	/**
	 * Returns the hostname
	 * 
	 * @return hostname
	 */
	public static String getHostname() {
		return hostname;
	}

	/**
	 * Returns the port
	 * 
	 * @return port
	 */
	public static int getPort() {
		return port;
	}

	// Public Methods
	/**
	 * Returns version information for RPC.
	 * 
	 * @return The RPC version of the node we are connected to.
	 */
	public static int getRPCVersion() {
		if (responseVersions == null)
			getVersions();
		return responseVersions.getRpc_version();
	}

	/**
	 * Returns the Store version.
	 * 
	 * @return The Store version of the node we are connected to.
	 */
	public static int getStoreVersion() {
		if (responseVersions == null)
			getVersions();
		return responseVersions.getStore_version();
	}

	/**
	 * Returns the Node vendor.
	 * 
	 * @return The vender of the node we are connected to.
	 */
	public static String getNodeVendor() {
		if (responseVersions == null)
			getVersions();
		return responseVersions.getNode_vendor();
	}

	/**
	 * Tells the node to look for pending blocks for any account in all available
	 * wallets
	 * 
	 * @return True if command was correctly started
	 */
	public static boolean SearchPending() {
		ResponseSuccess responseSuccess = (ResponseSuccess) HttpClient
				.getResponse(new RequestMessage("search_pending_all"), ResponseSuccess.class);
		return (responseSuccess.getSuccess().equals(""));
	}

	/**
	 * Returns receive minimum for this node.
	 * 
	 * @return The receive minimum for this node.
	 */
	public static BigInteger getReceiveMinimum() {
		ResponseAmount responseAmount = (ResponseAmount) HttpClient
				.getResponse(new RequestMessage("receive_minimum"), ResponseAmount.class);
		return responseAmount.getAmount();
	}

	/**
	 * Stops the Node that is currently connected. Please note that it is not
	 * possible to start a node using this library.
	 * 
	 * @return true if stopped.
	 */
	public static boolean stop() {
		ResponseSuccess responseSuccess = (ResponseSuccess) HttpClient.getResponse(new RequestMessage("stop"),
				ResponseSuccess.class);
		return responseSuccess.getSuccess().equals("");
	}

	// Private Methods
	private static void getVersions() {
		responseVersions = (ResponseVersions) HttpClient.getResponse(new RequestMessage("version"),
				ResponseVersions.class);
	}

}
