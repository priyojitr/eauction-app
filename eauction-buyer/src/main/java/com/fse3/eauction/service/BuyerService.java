package com.fse3.eauction.service;

import java.util.List;

import com.fse3.eauction.dto.BidDTO;
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

	public abstract Bid placeBid(BidDTO bid) throws BuyerNotFoundException, BidNotPlacedException;

	public abstract Bid updateBid(String productId, String buyerEmailId, float newBidAmount)
			throws BuyerNotFoundException, BidNotPlacedException;

}
