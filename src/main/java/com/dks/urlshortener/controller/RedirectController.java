package com.dks.urlshortener.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dks.urlshortener.service.UrlService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RedirectController {
	
	public final UrlService urlService;
	
	@GetMapping("/{shortCode}")
	public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode){
		
		String originalUrl = urlService.getOriginalUrl(shortCode);
		return ResponseEntity
	            .status(302)
	            .header("Location", originalUrl)
	            .build();
	}

}
