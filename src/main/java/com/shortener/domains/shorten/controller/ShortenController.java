package com.shortener.domains.shorten.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shortener.domains.shorten.dto.ShortenDTO;
import com.shortener.domains.shorten.service.ShortenService;

@RestController
public class ShortenController {
	
	@Autowired
	private ShortenService service;

	@PostMapping("shorten")
	public ResponseEntity<?> shortenUrl(@RequestParam String url, @RequestParam(required = false) String customAlias) {
		long startTime = System.currentTimeMillis();
		ShortenDTO dto = service.shortenUrl(url, customAlias);
		long duration = System.currentTimeMillis() - startTime;
		dto.setDuration(duration);
		return ResponseEntity.ok(dto);
	}

}
