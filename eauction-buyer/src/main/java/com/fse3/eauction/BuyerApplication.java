package com.fse3.eauction;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class BuyerApplication {

	@Value("${spring.application.name}")
	private String appName;

	public static void main(String[] args) {
		SpringApplication.run(BuyerApplication.class, args);
	}

	@PostConstruct
	private void appStartup() {
		log.info("{} - app started", this.appName);
	}

	@PreDestroy
	private void appShutdown() {
		log.info("{} - shutting down", this.appName);
	}

}
