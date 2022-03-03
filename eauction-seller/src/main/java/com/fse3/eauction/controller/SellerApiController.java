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

import com.fse3.eauction.dto.SellerDTO;
import com.fse3.eauction.exception.SellerNotCreatedException;
import com.fse3.eauction.exception.SellerNotDeletedException;
import com.fse3.eauction.model.Seller;
import com.fse3.eauction.service.SellerService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/e-auction/api/v1/seller", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
public class SellerApiController {

	@Value("${spring.application.name}")
	private String appName;

	private final SellerService sellerService;

	@Autowired
	public SellerApiController(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	@GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> root() {
		log.info("app name - {}", this.appName);
		return new ResponseEntity<>("service up: " + this.appName, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<Seller> registerSeller(@RequestBody SellerDTO sellerDto) throws SellerNotCreatedException {
		log.info("begin seller registration");
		try {
			Seller seller = this.sellerService.register(sellerDto);
			return new ResponseEntity<>(seller, HttpStatus.CREATED);
		} catch (SellerNotCreatedException e) {
			log.error("{} -- {}", e.getClass().getName(), e.getMessage());
			throw new SellerNotCreatedException(e.getMessage());
		}
	}

	@GetMapping("/list")
	public ResponseEntity<List<Seller>> getAllSellers() {
		log.info("get list of registered sellers");
		try {
			List<Seller> sellerList = this.sellerService.getAllSellers();
			log.info("registered seller count: {}", sellerList.size());
			if (sellerList.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else
				return new ResponseEntity<>(sellerList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/list/{sellerEmailId}")
	public ResponseEntity<Seller> getSeller(@PathVariable String sellerEmailId) {
		log.info("get seller info based on seller email id: {}", sellerEmailId);
		try {
			if (null == sellerEmailId || sellerEmailId.isEmpty())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			Seller seller = this.sellerService.getSellerByEmailId(sellerEmailId);
			log.info("registered seller found: {}", seller.getSellerId());
			if (seller.getSellerId().isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else
				return new ResponseEntity<>(seller, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/delete", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> deleteSeller(@RequestParam(required = false) String sellerId) {
		log.info("begin seller delete");
		boolean deleted = Boolean.FALSE;
		try {
			if (null == sellerId || sellerId.isEmpty())
				deleted = this.sellerService.deleteSeller("");
			else
				deleted = this.sellerService.deleteSeller(sellerId);
			if (deleted)
				return new ResponseEntity<>("seller deleted", HttpStatus.OK);
			else
				throw new SellerNotDeletedException("seller not deleted");
		} catch (SellerNotDeletedException e) {
			return new ResponseEntity<>("unable to delete seller", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
