package com.shortener.domains.shorten.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shortener.domains.shorten.dto.ShortenDTO;
import com.shortener.domains.shorten.model.Shorten;
import com.shortener.domains.shorten.repository.ShortenRepository;

@Service
public class ShortenService {
	
	private static String URL_BASE = "http://localhost:8080/";
	
	@Autowired
	private ShortenRepository repository;
	
	public ShortenDTO shortenUrl(String originalUrl, String customAlias) {
		
		if(customAlias != null && findByAlias(customAlias) != null) {
			throw new RuntimeException();
		}
		var alias = this.generateAlias(originalUrl);
		
		var shorten = new Shorten();
		
		shorten.setUrlOriginal(originalUrl);
		shorten.setAlias(alias);
		shorten.setUrlShorten(URL_BASE + alias);
		shorten.setAccessCount(1L);
		shorten.setDateCreate(LocalDateTime.now());
		
		repository.save(shorten);
		
		return new ShortenDTO(shorten);
		
	}
	
	public String redirecionaAutomaticoUrl(String customAlias) {
		
		var shorten = findByAlias(customAlias);
		if(shorten == null) {
			throw new IllegalArgumentException("SHORTENED URL NOT FOUND");
		}
		return shorten.getUrlOriginal();
	}
	
	public List<Shorten> getTop10MostAccessedURLs() {
		return repository.findFirst10ByOrderByAccessCountDesc().get();
	}
	
	public Shorten findByAlias(String customAlias) {
		return repository.findByAlias(customAlias);
	}
	
	public String generateAlias(String originalUrl) {
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
