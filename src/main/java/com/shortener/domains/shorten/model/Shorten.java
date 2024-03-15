package com.shortener.domains.shorten.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Shorten implements Serializable {

	private static final long serialVersionUID = -2735699778933613067L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String urlOriginal;
	@Column(unique = true)
	private String urlShorten;
	private String alias;
	private LocalDateTime dateCreate;
	private Long accessCount;

	public Shorten() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrlOriginal() {
		return urlOriginal;
	}

	public void setUrlOriginal(String urlOriginal) {
		this.urlOriginal = urlOriginal;
	}

	public String getUrlShorten() {
		return urlShorten;
	}

	public void setUrlShorten(String urlShorten) {
		this.urlShorten = urlShorten;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public LocalDateTime getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(LocalDateTime dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Long getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Long accessCount) {
		this.accessCount = accessCount;
	}
	
}
