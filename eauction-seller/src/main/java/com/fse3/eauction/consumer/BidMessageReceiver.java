package com.fse3.eauction.consumer;

import org.springframework.jms.annotation.JmsListener;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BidMessageReceiver {
	
	@JmsListener(destination = "${active-mq.topic}")
	public void receive(String message) {
		log.info("new bid received: {}", message);
	}
}
