package com.shortener.domains.shorten.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.shortener.domains.shorten.model.Shorten;
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
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse(customAlias, "001", "CUSTOM ALIAS ALREADY EXISTS"));
		}
	}
	
	@GetMapping("{customAlias}")
	public RedirectView redirectToOriginalURL(@PathVariable String customAlias) {
		var urlRedirecionamento = service.redirecionaAutomaticoUrl(customAlias);
		return new RedirectView(urlRedirecionamento);

	}
	
	 @GetMapping("shorten/top-10")
	    public ResponseEntity<List<Shorten>> getTop10MostAccessedURLs() {
	        var top10URLs = service.getTop10MostAccessedURLs();
	        return ResponseEntity.ok(top10URLs);
	    }

}
