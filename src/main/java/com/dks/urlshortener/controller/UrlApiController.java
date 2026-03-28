package com.dks.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dks.urlshortener.dto.AnalyticsResponse;
import com.dks.urlshortener.dto.ShortenUrlRequest;
import com.dks.urlshortener.dto.ShortenUrlResponse;
import com.dks.urlshortener.service.UrlService;
import com.dks.urlshortener.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UrlApiController {
	
	public final UrlService urlService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Value("${app.base-url}")
	private String baseUrl;
	
	@PostMapping("/shorten")
	public ShortenUrlResponse createShortUrl(@RequestBody @Valid ShortenUrlRequest request, 
			HttpServletRequest httpRequest) {
		
		String authHeader = httpRequest.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
		    throw new RuntimeException("Missing or invalid Authorization header");
		}

		String token = authHeader.substring(7);
		String email = jwtUtil.extractEmail(token);
		
		var result = urlService.createShortUrl(request.getOriginalUrl(), 
												request.getCustomAlias(), email);
		return ShortenUrlResponse.builder()
				.shortUrl(baseUrl + "/" + result.getShortCode())
				.createdAt(result.getCreatedAt())
				.build();
	}
	
	@GetMapping("/analytics/{shortCode}")
	public AnalyticsResponse getAnalytics(@PathVariable String shortCode) {
		return urlService.getAnalytics(shortCode);
	}
	
}
