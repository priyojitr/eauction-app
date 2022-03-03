package com.fse3.eauction.service;

import java.util.List;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse3.eauction.dto.BuyerDTO;
import com.fse3.eauction.exception.BuyerNotCreatedException;
import com.fse3.eauction.exception.BuyerNotDeletedException;
import com.fse3.eauction.exception.BuyerNotFoundException;
import com.fse3.eauction.model.Buyer;
import com.fse3.eauction.repository.BuyerRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BuyerServiceImpl implements BuyerService {

	private final BuyerRepository buyerRepository;

	@Autowired
	public BuyerServiceImpl(BuyerRepository buyerRepository) {
		this.buyerRepository = buyerRepository;
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
			else if (!Pattern.compile("^(.+)@(.+)$").matcher(buyerDto.getEmail()).matches())
				throw new BuyerNotCreatedException("buyer email validation failure: " + buyerDto.getEmail());
			log.info("saving buyer details");
			Buyer buyer = Buyer.builder()
					.firstName(buyerDto.getFirstName())
					.lastName(buyerDto.getLastName())
					.address(buyerDto.getAddress())
					.city(buyerDto.getCity())
					.state(buyerDto.getState())
					.pin(buyerDto.getPin())
					.phone(buyerDto.getPhone())
					.email(buyerDto.getEmail())
					.bidAmount(buyerDto.getBidAmount())
					.build();
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
