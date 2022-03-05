package com.fse3.eauction.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse3.eauction.dto.BidDTO;
import com.fse3.eauction.dto.BuyerDTO;
import com.fse3.eauction.exception.BuyerNotCreatedException;
import com.fse3.eauction.exception.BuyerNotDeletedException;
import com.fse3.eauction.exception.BuyerNotFoundException;
import com.fse3.eauction.model.Bid;
import com.fse3.eauction.model.Buyer;
import com.fse3.eauction.model.Product;
import com.fse3.eauction.repository.BidRepository;
import com.fse3.eauction.repository.BuyerRepository;
import com.fse3.eauction.repository.ProductRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BuyerServiceImpl implements BuyerService {

	private final BuyerRepository buyerRepository;
	private final ProductRepository productRepository;
	private final BidRepository bidRepository;

	@Autowired
	public BuyerServiceImpl(BuyerRepository buyerRepository, ProductRepository productRepository,
			BidRepository bidRepository) {
		this.buyerRepository = buyerRepository;
		this.productRepository = productRepository;
		this.bidRepository = bidRepository;
	}

	@Override
	public Bid placeBid(BidDTO bidDto) throws BuyerNotFoundException, BidNotPlacedException {
		Optional<Buyer> existingBuyer = Optional.ofNullable(this.buyerRepository.findByEmail(bidDto.getBuyerEmailId()));
		Optional<Product> existingProduct = Optional.ofNullable(this.productRepository.findById(bidDto.getProductId()))
				.get();
		Optional<Bid> existingBid = Optional.ofNullable(this.bidRepository
				.findByProductIdAndBuyerEmailId(existingProduct.get().getProductId(), existingBuyer.get().getEmail()));
		if (!existingBuyer.isPresent())
			throw new BuyerNotFoundException("buyer info not found for placing bid");
		else if (!existingProduct.isPresent())
			throw new BidNotPlacedException("product not found");
		else if ((new Date()).after(existingProduct.get().getBidEndDate()))
			throw new BidNotPlacedException("past bid end date");
		else if (existingBid.isPresent())
			throw new BidNotPlacedException("cannot place bid for the same the product");
		else if (existingProduct.get().getStartingPrice() >= bidDto.getBidAmount())
			throw new BidNotPlacedException("bid amount cannot be less than starting price");
		Bid bid = Bid.builder().bidAmount(bidDto.getBidAmount()).buyerId(existingBuyer.get().getBuyerId())
				.buyerEmailId(bidDto.getBuyerEmailId()).productId(existingProduct.get().getProductId()).build();
		return this.bidRepository.save(bid);
	}

	@Override
	public Bid updateBid(String productId, String buyerEmailId, float newBidAmount)
			throws BuyerNotFoundException, BidNotPlacedException {
		Optional<Bid> existingBid = Optional
				.ofNullable(this.bidRepository.findByProductIdAndBuyerEmailId(productId, buyerEmailId));
		if (!existingBid.isPresent())
			throw new BidNotPlacedException("bid not found for the product");
		Optional<Product> existingProduct = Optional.ofNullable(this.productRepository.findById(productId)).get();
		if ((new Date()).after(existingProduct.get().getBidEndDate()))
			throw new BidNotPlacedException("cannot update bid past bid end date");
		Bid bid = Bid.builder().bidAmount(newBidAmount).bidId(existingBid.get().getBidId())
				.buyerId(existingBid.get().getBuyerId()).buyerEmailId(existingBid.get().getBuyerEmailId())
				.productId(existingBid.get().getProductId()).build();
		return this.bidRepository.save(bid);
	}

	@Override
	public Buyer register(@NotNull BuyerDTO buyerDto) throws BuyerNotCreatedException {
		log.info("validating buyer info");
		try {
			if (buyerDto.getFirstName().length() <= 5 && buyerDto.getFirstName().length() >= 30)
				throw new BuyerNotCreatedException("buyer first name validation failure");
			else if (buyerDto.getLastName().length() <= 5 && buyerDto.getLastName().length() >= 25)
				throw new BuyerNotCreatedException("buyer last name validation failure");
			else if (buyerDto.getPhone().length() != 10)
				throw new BuyerNotCreatedException("buyer phone number validation failure");
			else if (null == buyerDto.getEmail()
					|| !Pattern.compile("^(.+)@(.+)$").matcher(buyerDto.getEmail()).matches())
				throw new BuyerNotCreatedException("buyer email validation failure");
			log.info("saving buyer details");
			Buyer buyer = Buyer.builder().firstName(buyerDto.getFirstName()).lastName(buyerDto.getLastName())
					.address(buyerDto.getAddress()).city(buyerDto.getCity()).state(buyerDto.getState())
					.pin(buyerDto.getPin()).phone(buyerDto.getPhone()).email(buyerDto.getEmail())
					.bidAmount(buyerDto.getBidAmount()).build();
			return this.buyerRepository.save(buyer);
		} catch (Exception e) {
			log.error(e.getMessage() + " -- " + e.getClass().getName());
			throw new BuyerNotCreatedException("buyer registration failed: ");
		}
	}

	@Override
	public List<Buyer> getAllBuyers() throws BuyerNotFoundException {
		return this.buyerRepository.findAll();
	}

	@Override
	public Buyer getBuyerByEmailId(String buyerEmailId) throws BuyerNotFoundException {
		return this.buyerRepository.findByEmail(buyerEmailId);
	}

	@Override
	public boolean deleteBuyer(String buyerId) throws BuyerNotDeletedException {
		boolean deleted;
		if (buyerId.isEmpty()) {
			log.info("deleting all buyers");
			this.buyerRepository.deleteAll();
			deleted = Boolean.TRUE;
		} else {
			log.info("deleting buyer id: {}", buyerId);
			this.buyerRepository.deleteById(buyerId);
			deleted = Boolean.TRUE;
		}
		return deleted;
	}

}
