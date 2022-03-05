package com.fse3.eauction.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fse3.eauction.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

}
