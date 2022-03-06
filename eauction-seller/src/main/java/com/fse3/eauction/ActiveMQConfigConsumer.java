package com.fse3.eauction;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import com.fse3.eauction.consumer.BidMessageReceiver;

@Configuration
@EnableJms
public class ActiveMQConfigConsumer {

	@Value("${active-mq.broker-url}")
	private String brokerUrl;

	@Bean
	public ConnectionFactory receiverConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL(this.brokerUrl);
		return activeMQConnectionFactory;
	}

	@Bean
	public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(receiverConnectionFactory());
		return factory;
	}

	@Bean
	public BidMessageReceiver receiver() {
		return new BidMessageReceiver();
	}

}
