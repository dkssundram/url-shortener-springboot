package com.dks.urlshortener.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dks.urlshortener.dto.ShortenUrlRequest;
import com.dks.urlshortener.dto.ShortenUrlResponse;
import com.dks.urlshortener.service.UrlService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UrlApiController {
	
	public final UrlService urlService;
	
//	@PostMapping("/shorten")
//	public ResponseEntity<String> createShortUrl(@RequestBody String originalUrl){
//		String shortCode = urlService.createShortUrl(originalUrl);
//		return ResponseEntity.ok("http://localhost:8080/"+ shortCode);
//	}
	
	@PostMapping("/shorten")
	public ShortenUrlResponse createShortUrl(@RequestBody @Valid ShortenUrlRequest request) {
		var result = urlService.createShortUrl(request.getOriginalUrl());
		return ShortenUrlResponse.builder()
				.shortUrl("http://localhost:8080/"+ result.getShortCode())
				.createdAt(result.getCreatedAt())
				.build();
	}
	
}
