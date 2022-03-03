package com.fse3.eauction.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/e-auction/api/v1/buyer", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
public class BuyerApiController {

	@Value("${spring.application.name}")
	private String appName;

	@GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> root() {
		log.info("app name - {}", this.appName);
		return new ResponseEntity<>("service up: " + this.appName, HttpStatus.OK);
	}
}
