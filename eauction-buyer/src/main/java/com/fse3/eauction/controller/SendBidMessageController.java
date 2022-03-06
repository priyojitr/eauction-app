package com.fse3.eauction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fse3.eauction.producer.BidMessageSender;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class SendBidMessageController {

	private BidMessageSender sender;
	
	@Autowired
	public SendBidMessageController(BidMessageSender sender) {
		this.sender=sender;
	}
	
	@GetMapping("/sender/{sendMsg}")
	public ResponseEntity<String> sendMsg(@PathVariable String sendMsg) {
		log.info("sending message - {}", sendMsg);
		try {
			sender.sendMessage(sendMsg);
			return new ResponseEntity<>("message sent", HttpStatus.OK);
		} catch (Exception e) {
			log.error("{} -- {}", e.getClass(), e.getMessage());
			return new ResponseEntity<>("message creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
