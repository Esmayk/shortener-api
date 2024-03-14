package com.shortener.domains.shorten.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
			throw new RuntimeException();
		}
		var alias = this.generateAlias(originalUrl);
		
		var shorten = new Shorten();
		
		shorten.setUrlOriginal(originalUrl);
		shorten.setAlias(alias);
		shorten.setUrlShorten(originalUrl + "/" + alias);
		shorten.setDateCreate(LocalDateTime.now());
		
		repository.save(shorten);
		
		return new ShortenDTO(shorten);
		
	}
	
	public String redirecionaAutomaticoUrl(String originalUrl) {
		
		var shorten = repository.findByUrlOriginal(originalUrl);
		if(shorten == null) {
			throw new RuntimeException();
		}
		return shorten.getUrlShorten();
	}
	
	
	private String generateAlias(String originalUrl) {
		try {
			var digest = MessageDigest.getInstance("SHA-256");
			var hashByte = digest.digest(originalUrl.getBytes());
			var hexString = new StringBuilder();
			for(byte b : hashByte) {
				var hex = Integer.toHexString(0xff & b);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.substring(0,8);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
    }
}
