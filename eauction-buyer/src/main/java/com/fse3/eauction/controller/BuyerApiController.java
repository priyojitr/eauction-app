package com.fse3.eauction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fse3.eauction.dto.BidDTO;
import com.fse3.eauction.dto.BuyerDTO;
import com.fse3.eauction.exception.BidNotPlacedException;
import com.fse3.eauction.exception.BuyerNotCreatedException;
import com.fse3.eauction.exception.BuyerNotDeletedException;
import com.fse3.eauction.exception.BuyerNotFoundException;
import com.fse3.eauction.model.Bid;
import com.fse3.eauction.model.Buyer;
import com.fse3.eauction.producer.BidMessageSender;
import com.fse3.eauction.service.BuyerService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/e-auction/api/v1/buyer", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
@SuppressWarnings("all")
public class BuyerApiController {

	@Value("${spring.application.name}")
	private String appName;

	private final BuyerService buyerService;
	private BidMessageSender sender;

	@Autowired
	public BuyerApiController(BuyerService buyerService, BidMessageSender sender) {
		this.buyerService = buyerService;
		this.sender = sender;
	}

	@PostMapping(value = "/place-bid")
	public ResponseEntity<Bid> placeBid(@RequestBody BidDTO bidDto) {
		log.info("placing bid for product");
		try {
			Bid bid = this.buyerService.placeBid(bidDto);
			log.info("new bid placed - {}", bidDto);
			sender.sendMessage(bidDto.toString());
			return new ResponseEntity<>(bid, HttpStatus.CREATED);
		} catch (BuyerNotFoundException | BidNotPlacedException e) {
			log.error("{} -- {}", e.getClass().getName(), e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("{} -- {}", e.getClass().getName(), e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/update-bid/{productId}/{buyerEmailId}/{newBidAmount}")
	public ResponseEntity<Bid> updateBid(@PathVariable String productId, @PathVariable String buyerEmailId,
			@PathVariable float newBidAmount) {
		log.info("update existing bid for a product");
		try {
			Bid bid = this.buyerService.updateBid(productId, buyerEmailId, newBidAmount);
			return new ResponseEntity<>(bid, HttpStatus.ACCEPTED);
		} catch (BuyerNotFoundException | BidNotPlacedException e) {
			log.error("{} -- {}", e.getClass(), e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("{} -- {}", e.getClass(), e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> root() {
		log.info("app name - {}", this.appName);
		return new ResponseEntity<>("service up: " + this.appName, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<Buyer> registerBuyer(@RequestBody BuyerDTO buyerDto) {
		log.info("begin buyer registration");
		try {
			Buyer buyer = this.buyerService.register(buyerDto);
			return new ResponseEntity<>(buyer, HttpStatus.CREATED);
		} catch (BuyerNotCreatedException e) {
			log.error("{} -- {}", e.getClass().getName(), e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("{} -- {}", e.getClass().getName(), e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/list")
	public ResponseEntity<List<Buyer>> getAllBuyers() {
		log.info("get list of registered buyers");
		try {
			List<Buyer> buyerList = this.buyerService.getAllBuyers();
			log.info("registered buyer count: {}", buyerList.size());
			if (buyerList.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else
				return new ResponseEntity<>(buyerList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/list/{buyerEmailId}")
	public ResponseEntity<Buyer> getBuyer(@PathVariable String buyerEmailId) {
		log.info("get buyer info based on buyer email id: {}", buyerEmailId);
		try {
			if (null == buyerEmailId || buyerEmailId.isEmpty())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			Buyer buyer = this.buyerService.getBuyerByEmailId(buyerEmailId);
			if (null == buyer)
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else {
				log.info("registered buyer found: {}", buyer.getBuyerId());
				return new ResponseEntity<>(buyer, HttpStatus.OK);
			}
		} catch (BuyerNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/delete", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> deleteBuyer(@RequestParam(required = false) String buyerId) {
		log.info("begin buyer delete");
		boolean deleted = Boolean.FALSE;
		try {
			if (null == buyerId || buyerId.isEmpty())
				deleted = this.buyerService.deleteBuyer("");
			else
				deleted = this.buyerService.deleteBuyer(buyerId);
			if (deleted)
				return new ResponseEntity<>("buyer deleted", HttpStatus.OK);
			else
				throw new BuyerNotDeletedException("buyer not deleted");
		} catch (BuyerNotDeletedException e) {
			return new ResponseEntity<>("unable to delete buyer", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
