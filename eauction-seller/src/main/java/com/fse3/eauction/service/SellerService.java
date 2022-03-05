package com.fse3.eauction.service;

import java.util.List;

import com.fse3.eauction.dto.ProductBidDTO;
import com.fse3.eauction.dto.ProductDTO;
import com.fse3.eauction.dto.SellerDTO;
import com.fse3.eauction.exception.ProductNotCreatedException;
import com.fse3.eauction.exception.ProductNotDeletedException;
import com.fse3.eauction.exception.ProductNotFoundException;
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

	public abstract List<Product> getAllProducts();

	public abstract List<ProductBidDTO> showBidList(String productId) throws ProductNotFoundException;

	public abstract void deleteProduct(String productId) throws ProductNotFoundException, ProductNotDeletedException;

}
