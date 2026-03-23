package com.dks.urlshortener.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dks.urlshortener.exception.UrlNotFoundException;
import com.dks.urlshortener.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlCacheService {

    private final UrlRepository urlRepository;

    @Cacheable(value = "urls", key = "#shortCode")
    public String getOriginalUrlFromCache(String shortCode) {

        System.out.println("DB HIT (CACHE MISS)");

        return urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("Short URL does not exist"))
                .getOriginalUrl();
    }
}