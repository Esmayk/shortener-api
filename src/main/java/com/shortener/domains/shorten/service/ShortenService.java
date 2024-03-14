package com.shortener.domains.shorten.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shortener.domains.shorten.dto.ShortenDTO;
import com.shortener.domains.shorten.model.Shorten;
import com.shortener.domains.shorten.repository.ShortenRepository;

@Service
public class ShortenService {

	@Autowired
	private ShortenRepository repository;
	
	public ShortenDTO shortenUrl(String originalUrl, String customAlias) {
		
		if(customAlias != null && repository.findByAlias(customAlias) != null) {
			throw new RuntimeException("{ERR_CODE: 001, Description:CUSTOM ALIAS ALREADY EXISTS}");
		}
		
		Shorten shorten = new Shorten();
		
		shorten.setUrlOriginal(originalUrl);
		shorten.setUrlShorten(this.generateAlias(originalUrl));
		shorten.setAlias(shorten.getUrlShorten());
		shorten.setDateCreate(LocalDateTime.now());
		
		repository.save(shorten);
		
		return new ShortenDTO(shorten);
		
	}
	
	private String generateAlias(String originalUrl) {
        return "alias_" + originalUrl.hashCode();
    }
}
