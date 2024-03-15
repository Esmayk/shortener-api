package com.shortener.domains.shorten.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.shortener.domains.shorten.dto.ShortenDTO;
import com.shortener.domains.shorten.model.Shorten;
import com.shortener.domains.shorten.repository.ShortenRepository;

@SpringBootTest
public class ShortenServiceTest {

	@InjectMocks
	private ShortenService service;

	@Mock
	private ShortenRepository repository;

	private static final Long ID = 1L;
	private static final String URL_BASE = "http://localhost:8080/";
    private static final String URL_ORIGINAL = "http://example.com/original";
    private static final String ALIAS = "9cce4623";
    private static final LocalDateTime DATECREATE = LocalDateTime.now();
    private static final Long ACCESSCOUNT = 5L;
    private static final String DURATION = "20 ms";
    
    private Shorten shorten;
    private ShortenDTO shortenDTO;
    private List<Shorten> listShorten;
    
    @BeforeEach
    void setUp() {
    	MockitoAnnotations.openMocks(this);
    	startShorten();
    }
    
    @Test
    void whenFindByAliasThenReturnAnShortenInstance() {
    	when(repository.findByAlias(anyString())).thenReturn(shorten);
    	
    	var response = service.findByAlias(ALIAS);
    	
    	assertNotNull(response);
    	assertEquals(Shorten.class, response.getClass());
    	assertEquals(ID, response.getId());
    	assertEquals(URL_ORIGINAL, response.getUrlOriginal());
    	assertEquals(URL_BASE + ALIAS, response.getUrlShorten());
    	assertEquals(ALIAS, response.getAlias());
    	assertEquals(ACCESSCOUNT, response.getAccessCount());
    }
    
    @Test
    void whenShortenUrlSuccess() {
    	when(repository.save(any())).thenReturn(shorten);
    	
    	var response = service.shortenUrl(URL_ORIGINAL, ALIAS);
    	
    	assertNotNull(response);
    	assertEquals(ShortenDTO.class, response.getClass());
    	assertEquals(URL_ORIGINAL, response.getUrlOriginal());
    	assertEquals(URL_BASE + ALIAS, response.getUrlShorten());
    	assertEquals(ALIAS, response.getAlias());
    }
    
    private void startShorten() {
    	shorten = new Shorten(ID, URL_ORIGINAL, URL_BASE + ALIAS, ALIAS, DATECREATE, ACCESSCOUNT);
    	shortenDTO = new ShortenDTO(shorten);
    	shortenDTO.setDuration(DURATION);
    	listShorten = new ArrayList<>();
    	listShorten.add(shorten);
    }
}
