package com.shortener.domains.shorten.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
	private Optional<List<Shorten>> optionalShorten;
	
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

	@Test
    void whenShortenUrlAnRuntimeException() {
    	when(repository.findByAlias(anyString())).thenThrow(new RuntimeException());
    	try {
			service.findByAlias(ALIAS);
		} catch (Exception e) {
			assertEquals(RuntimeException.class, e.getClass());
		}
    }

	@Test
    void whenredirecionaAutomaticoUrlThenReturnAnIllegalArgumentException() {
    	when(repository.findByAlias(anyString())).thenThrow(new IllegalArgumentException("SHORTENED URL NOT FOUND"));
    	try {
			service.findByAlias(ALIAS);
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
			assertEquals("SHORTENED URL NOT FOUND", e.getMessage());
		}
    }

	@Test
    void whenRedirecionaAutomaticoUrlThenReturnSuccess() {
		when(repository.findByAlias(anyString())).thenReturn(shorten);
    	
    	var response = service.redirecionaAutomaticoUrl(ALIAS);
    	
    	assertNotNull(response);
    	assertEquals(String.class, response.getClass());
    	assertEquals(URL_ORIGINAL, response);
    }
	
	@Test
	void whengetTop10MostAccessedURLsThenReturnSuccess() {
		when(repository.findFirst10ByOrderByAccessCountDesc()).thenReturn(optionalShorten);
		var response = service.getTop10MostAccessedURLs();
		
		assertNotNull(response);
		assertEquals(10, response.size());
        assertEquals(listShorten, response);
        verify(repository, times(1)).findFirst10ByOrderByAccessCountDesc();
		
	}

	private void startShorten() {
		shorten = new Shorten(ID, URL_ORIGINAL, URL_BASE + ALIAS, ALIAS, DATECREATE, ACCESSCOUNT);
		shortenDTO = new ShortenDTO(shorten);
		shortenDTO.setDuration(DURATION);
		listShorten = new ArrayList<>();
		listShorten.addAll(Arrays.asList(shorten,shorten,shorten,shorten,shorten,shorten,shorten,shorten,shorten,shorten));
		optionalShorten = Optional.of(listShorten);
	}
}
