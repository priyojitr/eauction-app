package com.fse3.eauction.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse3.eauction.dto.ProductBidDTO;
import com.fse3.eauction.dto.ProductDTO;
import com.fse3.eauction.dto.SellerDTO;
import com.fse3.eauction.exception.ProductNotCreatedException;
import com.fse3.eauction.exception.ProductNotDeletedException;
import com.fse3.eauction.exception.ProductNotFoundException;
import com.fse3.eauction.exception.SellerNotCreatedException;
import com.fse3.eauction.exception.SellerNotDeletedException;
import com.fse3.eauction.exception.SellerNotFoundException;
import com.fse3.eauction.model.Bid;
import com.fse3.eauction.model.Product;
import com.fse3.eauction.model.Seller;
import com.fse3.eauction.repository.BidRepository;
import com.fse3.eauction.repository.ProductRepository;
import com.fse3.eauction.repository.SellerRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SellerServiceImpl implements SellerService {

	private final SellerRepository sellerRepository;
	private final ProductRepository productRepository;
	private final BidRepository bidRepository;
	private static final List<String> PRODUCT_CATEGORY = Arrays.asList("PAINTING", "SCULPTOR", "ORNAMENT");

	@Autowired
	public SellerServiceImpl(SellerRepository sellerRepository, ProductRepository productRepository,
			BidRepository bidRepository) {
		this.sellerRepository = sellerRepository;
		this.productRepository = productRepository;
		this.bidRepository = bidRepository;
	}

	@Override
	public Product addProduct(ProductDTO productDto) throws ProductNotCreatedException, SellerNotFoundException {
		Optional<Seller> seller = Optional.ofNullable(this.sellerRepository.findByEmail(productDto.getSellerEmailId()));
		if (!seller.isPresent()) {
			throw new SellerNotFoundException("seller info not found for adding product");
		}
		if (!PRODUCT_CATEGORY.contains(productDto.getCategory().toUpperCase())) {
			throw new ProductNotCreatedException("invalid product category!");
		} else if ((new Date()).after(productDto.getBidEndDate())) {
			throw new ProductNotCreatedException("invalid bid end date!");
		} else {
			try {
				Product product = Product.builder().productName(productDto.getProductName())
						.shortDescription(productDto.getShortDescription())
						.detailDescription(productDto.getDetailDescription()).category(productDto.getCategory())
						.startingPrice(productDto.getStartingPrice()).bidEndDate(productDto.getBidEndDate())
						.sellerEmailId(productDto.getSellerEmailId()).build();
				return this.productRepository.save(product);
			} catch (Exception e) {
				log.error("{} -- {}", e.getClass().getName(), e.getMessage());
				throw new ProductNotCreatedException("unable to add new product");
			}
		}
	}

	@Override
	public List<ProductBidDTO> showBidList(String productId) throws ProductNotFoundException {
		Optional<Product> product = Optional.ofNullable(this.productRepository.findById(productId)).get();
		if (!product.isPresent())
			throw new ProductNotFoundException("invalid product id provided");
		List<Bid> bidList = this.bidRepository.findByProductIdOrderByBidAmountDesc(product.get().getProductId());
		List<ProductBidDTO> productBids = new ArrayList<>();
		for (Bid bid : bidList) {
			productBids.add(ProductBidDTO.builder().bidAmount(bid.getBidAmount()).product(product.get()).build());
		}
		return productBids;
	}

	@Override
	public void deleteProduct(String productId) throws ProductNotFoundException, ProductNotDeletedException {
		log.info("validate product id to delete product");
		Optional<Product> existingProduct = Optional.ofNullable(this.productRepository.findById(productId)).get();
		if (!existingProduct.isPresent())
			throw new ProductNotFoundException("invalid product id");
		Optional<List<Bid>> existingBidList = Optional.ofNullable(this.bidRepository.findAllByProductId(productId));
		if ((new Date()).after(existingProduct.get().getBidEndDate()))
			throw new ProductNotDeletedException("unable to delete product after bid end date");
		else if (existingBidList.isPresent() && existingBidList.get().size() > 0)
			throw new ProductNotDeletedException("unable to delete product having a bid placed");
		else
			this.productRepository.deleteById(productId);
	}

	@Override
	public List<Product> getAllProducts() {
		return this.productRepository.findAll();
	}

	@Override
	public Seller register(SellerDTO sellerDto) throws SellerNotCreatedException {
		log.info("validating seller info");
		try {
			if (sellerDto.getFirstName().length() <= 5 && sellerDto.getFirstName().length() >= 30)
				throw new SellerNotCreatedException("seller first name validation failure");
			else if (sellerDto.getLastName().length() <= 5 && sellerDto.getLastName().length() >= 25)
				throw new SellerNotCreatedException("seller last name validation failure");
			else if (sellerDto.getPhone().length() != 10)
				throw new SellerNotCreatedException("seller phone number validation failure");
			else if (null == sellerDto.getEmail()
					|| !Pattern.compile("^(.+)@(.+)$").matcher(sellerDto.getEmail()).matches())
				throw new SellerNotCreatedException("seller email validation failure");
			log.info("saving seller details");
			Seller seller = Seller.builder().firstName(sellerDto.getFirstName()).lastName(sellerDto.getLastName())
					.address(sellerDto.getAddress()).city(sellerDto.getCity()).state(sellerDto.getState())
					.pin(sellerDto.getPin()).phone(sellerDto.getPhone()).email(sellerDto.getEmail()).build();
			return this.sellerRepository.save(seller);
		} catch (Exception e) {
			log.error(e.getMessage() + " -- " + e.getClass().getName());
			throw new SellerNotCreatedException("seller registration failed: ");
		}
	}

	@Override
	public List<Seller> getAllSellers() throws SellerNotFoundException {
		return this.sellerRepository.findAll();
	}

	@Override
	public Seller getSellerByEmailId(String sellerEmailId) throws SellerNotFoundException {
		return this.sellerRepository.findByEmail(sellerEmailId);
	}

	@Override
	public boolean deleteSeller(String sellerId) throws SellerNotDeletedException {
		boolean deleted;
		if (sellerId.isEmpty()) {
			log.info("deleting all sellers");
			this.sellerRepository.deleteAll();
			deleted = Boolean.TRUE;
		} else {
			log.info("deleting seller id: {}", sellerId);
			this.sellerRepository.deleteById(sellerId);
			deleted = Boolean.TRUE;
		}
		return deleted;
	}
}
