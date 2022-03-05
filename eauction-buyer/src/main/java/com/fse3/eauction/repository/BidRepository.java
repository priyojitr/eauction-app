package com.fse3.eauction.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fse3.eauction.model.Bid;

@Repository
public interface BidRepository extends MongoRepository<Bid, String> {

	List<Bid> findByProductIdOrderByBidAmountDesc(String productId);
	Bid findByProductIdAndBuyerEmailId(String productId, String buyerEmailId);

}
