package com.shortener.domains.shorten.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shortener.domains.shorten.model.Shorten;

@Repository
public interface ShortenRepository extends JpaRepository<Shorten, Long> {

	Shorten findByAlias(String alias);
	Shorten findByUrlOriginal(String urlOriginal);
}
