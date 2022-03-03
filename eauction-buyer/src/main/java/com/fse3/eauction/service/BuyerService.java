package com.fse3.eauction.service;

import java.util.List;

import com.fse3.eauction.dto.BuyerDTO;
import com.fse3.eauction.exception.BuyerNotCreatedException;
import com.fse3.eauction.exception.BuyerNotDeletedException;
import com.fse3.eauction.exception.BuyerNotFoundException;
import com.fse3.eauction.model.Bid;
import com.fse3.eauction.model.Buyer;

public interface BuyerService {
	public abstract Buyer register(BuyerDTO buyer) throws BuyerNotCreatedException;
	public abstract Buyer getBuyerByEmailId(String buyerEmailId) throws BuyerNotFoundException;
	public abstract List<Buyer> getAllBuyers() throws BuyerNotFoundException;
	public abstract boolean deleteBuyer(String buyerId) throws BuyerNotDeletedException;
	
	default String placeBid(Bid product) {
		return "place-bid -- YET TO BE IMPLEMENTED";
	}
	default String updateBid(Bid bid) {
		return "update-bid -- YET TO BE IMPLEMENTED";
	}
}
