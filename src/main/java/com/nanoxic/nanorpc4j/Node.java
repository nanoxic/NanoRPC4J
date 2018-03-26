package com.nanoxic.nanorpc4j;

import java.math.BigInteger;

import com.nanoxic.nanorpc4j.exceptions.InitializationException;
import com.nanoxic.nanorpc4j.exceptions.ResponseException;

/**
 * 
 * Singleton class to initialize NanoRPC4J representing the Nano node.
 * 
 * @author Koen De Voegt
 *
 */
public class Node {

	private static String hostname = "localhost";
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
	 * Check whether this Node contains Wallet.
	 * 
	 * @param wallet
	 *            The wallet to check.
	 * @return True if the wallet is part of the Node.
	 */
	public static boolean contains(Wallet wallet) {
		return contains(wallet.getWalletId());
	}

	/**
	 * Check whether this Node contains Wallet by ID.
	 * 
	 * @param walletId
	 *            The walletId to check.
	 * @return True if the wallet is part of this Node.
	 */
	public static boolean contains(String walletId) {
		try {
			HttpClient.getResponse(new RequestWallet("wallet_locked", walletId), ResponseLocked.class);
		} catch (ResponseException e) {
			return false;
		}
		return true;
	}

	/**
	 * Check whether given string is a valid Nano address
	 * 
	 * @param address
	 *            a string containing an address to verify
	 * @return True if the string provided contains a valid Nano address
	 */
	public static boolean isValidAddress(String address) {
		ResponseValidate validateResponse = (ResponseValidate) HttpClient
				.getResponse(new RequestAccount("validate_account_number", address), ResponseValidate.class);
		return (validateResponse.getValid() == 1);
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
		ResponseAmount responseAmount = (ResponseAmount) HttpClient.getResponse(new RequestMessage("receive_minimum"),
				ResponseAmount.class);
		return responseAmount.getAmount();
	}

	/**
	 * Returns how many rai are in the public supply.
	 * 
	 * @return Amount of rai in the public supply.
	 */
	public static BigInteger getAvailableSupply() {
		ResponseAvailable responseAvailable = (ResponseAvailable) HttpClient
				.getResponse(new RequestMessage("available_supply"), ResponseAvailable.class);
		return responseAvailable.getAvailable();
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

	/**
	 * 
	 * Creates a new random wallet id. enable_control required.
	 * 
	 * @return The new Wallet.
	 */
	public Wallet createWallet() {
		ResponseWallet responseWallet = (ResponseWallet) HttpClient.getResponse(new RequestMessage("wallet_create"),
				ResponseWallet.class);
		return new Wallet(responseWallet.getWallet());

	} // TODO test

	/**
	 * Destroys wallet and all contained accounts. enable_control required.
	 * 
	 * @param wallet The Wallet to destroy.
	 */
	public void destroyWallet(Wallet wallet) {
		HttpClient.getResponse(new RequestWallet("wallet_destroy", wallet.getWalletId()), ResponseWallet.class);

	} // TODO test

	/**
	 * Generates work for block. enable_control required.
	 * 
	 * @param block
	 *            The pending block
	 * @return The receive block
	 */
	public static String generateWork(String block) {
		RequestHash requestHash = new RequestHash("work_generate");
		requestHash.setHash(block);
		ResponseWork responseWork = (ResponseWork) HttpClient.getResponse(requestHash, ResponseWork.class);
		return responseWork.getWork();
	}

	// Private Methods
	private static void getVersions() {
		responseVersions = (ResponseVersions) HttpClient.getResponse(new RequestMessage("version"),
				ResponseVersions.class);
	}

}
