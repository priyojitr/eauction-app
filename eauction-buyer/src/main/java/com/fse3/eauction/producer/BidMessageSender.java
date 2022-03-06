package com.fse3.eauction.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BidMessageSender {

	@Value("${active-mq.topic}")
	private String topic;

	@Autowired
	private JmsTemplate jmsTemplate;

	public void sendMessage(String message) {
		log.info("new bid details - {}", message);
		this.jmsTemplate.convertAndSend(this.topic, message);
	}

}
