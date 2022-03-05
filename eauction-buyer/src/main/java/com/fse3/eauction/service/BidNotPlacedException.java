package com.fse3.eauction.service;

public class BidNotPlacedException extends Exception {

	private static final long serialVersionUID = 1L;

	public BidNotPlacedException(String message) {
		super(message);
	}

}
