package com.platform.support;

public class PlatformException extends RuntimeException {

	private static final long serialVersionUID = -4203102277214632563L;

	public PlatformException() {
		super();
	}

	public PlatformException(String message) {
		super(message);
	}

	public PlatformException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlatformException(Throwable cause) {
		super(cause);
	}

}
