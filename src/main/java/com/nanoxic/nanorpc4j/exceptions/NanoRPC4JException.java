package com.nanoxic.nanorpc4j.exceptions;

public abstract class NanoRPC4JException extends RuntimeException {

	private static final long serialVersionUID = 7401304686968120790L;

	public NanoRPC4JException(final String message) {
		this(message, null);
	}

	public NanoRPC4JException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
