package com.shortener.domains.shorten.dto;

import com.shortener.domains.shorten.model.Shorten;

public class ShortenDTO {

	private String urlShorten;
	private String urlOriginal;
	private String alias;
	private Long duration;
	
	public ShortenDTO() {
		super();
	}

	public String getUrlShorten() {
		return urlShorten;
	}

	public void setUrlShorten(String urlShorten) {
		this.urlShorten = urlShorten;
	}

	public String getUrlOriginal() {
		return urlOriginal;
	}

	public void setUrlOriginal(String urlOriginal) {
		this.urlOriginal = urlOriginal;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public ShortenDTO(Shorten shorten) {
		urlShorten = shorten.getUrlShorten();
		urlOriginal = shorten.getUrlOriginal();
		alias = shorten.getAlias();
	}

}
