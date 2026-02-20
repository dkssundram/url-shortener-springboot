package com.dks.urlshortener.service;

import java.time.LocalDateTime;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dks.urlshortener.exception.UrlNotFoundException;
import com.dks.urlshortener.model.Url;
import com.dks.urlshortener.repository.UrlRepository;
import com.dks.urlshortener.util.Base62Encoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {
	
	private final UrlRepository urlRepository;
//	private final Map<String, String> urlCache = new ConcurrentHashMap<>();
	
	@CacheEvict(value = "urls", allEntries = true)
	public Url createShortUrl(String originalUrl) {
		Url url = Url.builder()
				.originalUrl(originalUrl)
				.createdAt(LocalDateTime.now())
				.clickCount(0L)
				.build();
		Url savedUrl = urlRepository.save(url);
		String shortCode = Base62Encoder.encode(savedUrl.getId());
		savedUrl.setShortCode(shortCode);
		//urlRepository.save(savedUrl);
		return urlRepository.save(savedUrl);
	}
	
	@Cacheable (value = "urls", key = "#shortCode")
	public String getOriginalUrl(String shortCode) {
		
		System.out.println("DB HIT for: " + shortCode);
//		if(urlCache.containsKey(shortCode)) {
//		System.out.println("CACHE HIT for: " + shortCode);
//			return urlCache.get(shortCode);
//		}
//		System.out.println("CACHE MISS for: " + shortCode);
		
		Url url = urlRepository.findByShortCode(shortCode)
				.orElseThrow(() -> new UrlNotFoundException("Short URL does not exist"));
		url.setClickCount(url.getClickCount() + 1);
		urlRepository.save(url);
//		urlCache.put(shortCode, url.getOriginalUrl());
		return url.getOriginalUrl();
	}

}
