package com.fse3.eauction.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fse3.eauction.model.Buyer;

@Repository
public interface BuyerRepository extends MongoRepository<Buyer,String> {
	Buyer findByEmail(String email);

}
