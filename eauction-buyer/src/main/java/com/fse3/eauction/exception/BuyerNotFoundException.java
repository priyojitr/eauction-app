package com.fse3.eauction.exception;

public class BuyerNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BuyerNotFoundException(String message) {
		super(message);
	}

}
