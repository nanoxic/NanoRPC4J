package com.nanoxic.nanorpc4j;

import java.math.BigInteger;

/**
 * 
 * Singleton class to convert between raw and the different units used.
 * 
 * @author Koen De Voegt
 *
 */
public class Convert {

	// Constructor
	private Convert() {
	}

	// Public Methods
	/**
	 * Divide a raw amount down by the rai ratio.
	 * 
	 * @param rawAmount
	 *            The raw amount to convert
	 * @return The rai amount
	 */
	@Deprecated
	public static BigInteger raiFromRaw(BigInteger rawAmount) {
		RequestAmount requestAmount = new RequestAmount("rai_from_raw");
		requestAmount.setAmount(rawAmount);
		ResponseAmount responseAmount = (ResponseAmount) HttpClient.getResponse(requestAmount, ResponseAmount.class);
		return responseAmount.getAmount();
	}

	/**
	 * Multiply a rai amount by the rai ratio.
	 * 
	 * @param raiAmount
	 *            The rai amount to convert
	 * @return The raw amount
	 */
	@Deprecated
	public static BigInteger raiToRaw(BigInteger raiAmount) {
		RequestAmount requestAmount = new RequestAmount("rai_to_raw");
		requestAmount.setAmount(raiAmount);
		ResponseAmount responseAmount = (ResponseAmount) HttpClient.getResponse(requestAmount, ResponseAmount.class);
		return responseAmount.getAmount();
	}

	/**
	 * Divide a raw amount down by the krai ratio.
	 * 
	 * @param rawAmount
	 *            The raw amount to convert
	 * @return The krai amount
	 */
	@Deprecated
	public static BigInteger kraiFromRaw(BigInteger rawAmount) {
		RequestAmount requestAmount = new RequestAmount("krai_from_raw");
		requestAmount.setAmount(rawAmount);
		ResponseAmount responseAmount = (ResponseAmount) HttpClient.getResponse(requestAmount, ResponseAmount.class);
		return responseAmount.getAmount();
	}

	/**
	 * Multiply a krai amount by the krai ratio.
	 * 
	 * @param kraiAmount
	 *            The krai amount to convert
	 * @return The raw amount
	 */
	@Deprecated
	public static BigInteger kraiToRaw(BigInteger kraiAmount) {
		RequestAmount requestAmount = new RequestAmount("krai_to_raw");
		requestAmount.setAmount(kraiAmount);
		ResponseAmount responseAmount = (ResponseAmount) HttpClient.getResponse(requestAmount, ResponseAmount.class);
		return responseAmount.getAmount();
	}

	/**
	 * Divide a raw amount down by the Mrai ratio.
	 * 
	 * @param rawAmount
	 *            The raw amount to convert
	 * @return The Mrai amount
	 */
	@Deprecated
	public static BigInteger mraiFromRaw(BigInteger rawAmount) {
		RequestAmount requestAmount = new RequestAmount("mrai_from_raw");
		requestAmount.setAmount(rawAmount);
		ResponseAmount responseAmount = (ResponseAmount) HttpClient.getResponse(requestAmount, ResponseAmount.class);
		return responseAmount.getAmount();
	}

	/**
	 * Multiply a Mrai amount by the Mrai ratio.
	 * 
	 * @param mraiAmount
	 *            The Mrai amount to convert
	 * @return The raw amount
	 */
	@Deprecated
	public static BigInteger mraiToRaw(BigInteger mraiAmount) {
		RequestAmount requestAmount = new RequestAmount("mrai_to_raw");
		requestAmount.setAmount(mraiAmount);
		ResponseAmount responseAmount = (ResponseAmount) HttpClient.getResponse(requestAmount, ResponseAmount.class);
		return responseAmount.getAmount();
	}

}
