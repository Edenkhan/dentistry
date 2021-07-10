package com.youruan.dentistry.core.base.exception;

public class OptimismLockingException extends RuntimeException {

	private static final long serialVersionUID = -3688134219601876056L;

	public OptimismLockingException(String message) {
		super(message);
	}

}
