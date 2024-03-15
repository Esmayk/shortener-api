package com.shortener.domains.shorten.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shortener.domains.shorten.model.Shorten;

@Repository
public interface ShortenRepository extends JpaRepository<Shorten, Long> {

	Shorten findByAlias(String alias);
	
	Optional<List<Shorten>> findFirst10ByOrderByAccessCountDesc();
}
