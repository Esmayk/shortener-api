package com.shortener.domains.shorten.controller;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shortener.domains.shorten.service.ShortenService;
import com.shortener.exception.ErrorResponse;

@RestController
public class ShortenController {
	
	@Autowired
	private ShortenService service;

	@PostMapping("shorten")
	public ResponseEntity<?> shortenUrl(@RequestParam String url, @RequestParam(required = false) String customAlias) {
		var start = Instant.now();
		try {
			var dto = service.shortenUrl(url, customAlias);
			var end = Instant.now();
			var duration = Duration.between(start, end);
			dto.setDuration(duration.toMillis() + " ms");
			return ResponseEntity.ok(dto);
			
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse(customAlias, "001", "CUSTOM ALIAS ALREADY EXISTS"));
		}
		
		
		
	}

}
