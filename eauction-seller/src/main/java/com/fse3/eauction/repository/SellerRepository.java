package com.fse3.eauction.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fse3.eauction.model.Seller;

@Repository
public interface SellerRepository extends MongoRepository<Seller,String> {
	Seller findByEmail(String email);
}
