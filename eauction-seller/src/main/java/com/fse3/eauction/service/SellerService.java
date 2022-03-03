package com.fse3.eauction.service;

import java.util.List;

import com.fse3.eauction.dto.ProductDTO;
import com.fse3.eauction.dto.SellerDTO;
import com.fse3.eauction.exception.ProductNotCreatedException;
import com.fse3.eauction.exception.SellerNotCreatedException;
import com.fse3.eauction.exception.SellerNotDeletedException;
import com.fse3.eauction.exception.SellerNotFoundException;
import com.fse3.eauction.model.Product;
import com.fse3.eauction.model.Seller;

public interface SellerService {
	public abstract Seller register(SellerDTO sellerDto) throws SellerNotCreatedException;
	public abstract Seller getSellerByEmailId(String sellerEmailId) throws SellerNotFoundException;
	public abstract List<Seller> getAllSellers() throws SellerNotFoundException;
	public abstract boolean deleteSeller(String sellerId) throws SellerNotDeletedException;
	public abstract Product addProduct(ProductDTO product) throws ProductNotCreatedException, SellerNotFoundException;
	
	default String showBidList(String productId) {
		return "show-bids -- YET TO BE IMPLEMENTED";
	}
	
	default String deleteProduct(String productId) {
		return "place-bid -- YET TO BE IMPLEMENTED";
	}

}
