package com.fse3.eauction;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import com.fse3.eauction.producer.BidMessageSender;

@Configuration
public class ActiveMQConfigProducer {

	@Value("${active-mq.broker-url}")
	private String brokerUrl;

	@Bean
	public ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL(this.brokerUrl);
		return activeMQConnectionFactory;
	}
	
	@Bean
	public CachingConnectionFactory cachingConnectionFactory() {
		return new CachingConnectionFactory(connectionFactory());
	}

	@Bean
	public JmsTemplate jmsTemplate() {
		return new JmsTemplate(cachingConnectionFactory());
	}
	
	@Bean
	public BidMessageSender sender() {
		return new BidMessageSender();
	}

}
