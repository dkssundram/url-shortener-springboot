package com.dks.urlshortener.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.dks.urlshortener.dto.AnalyticsResponse;
import com.dks.urlshortener.exception.AliasAlreadyExistsException;
import com.dks.urlshortener.exception.UrlNotFoundException;
import com.dks.urlshortener.model.ClickEvent;
import com.dks.urlshortener.model.Url;
import com.dks.urlshortener.model.User;
import com.dks.urlshortener.repository.ClickEventRepository;
import com.dks.urlshortener.repository.UrlRepository;
import com.dks.urlshortener.repository.UserRepository;
import com.dks.urlshortener.util.Base62Encoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {
	
	private final UrlRepository urlRepository;
	private final ClickEventRepository clickEventRepository;
    private final UrlCacheService urlCacheService; // 👈 inject
    private final UserRepository userRepository;

//	private final Map<String, String> urlCache = new ConcurrentHashMap<>();
	
    public Url createShortUrl(String originalUrl, String customAlias, String email) {
    	User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (customAlias != null && !customAlias.isEmpty()) {

            if (urlRepository.findByShortCode(customAlias).isPresent()) {
                throw new AliasAlreadyExistsException("Custom alias already taken");
            }

            Url url = Url.builder()
                    .originalUrl(originalUrl)
                    .user(user)
                    .shortCode(customAlias)
                    .createdAt(LocalDateTime.now())
                    .clickCount(0L)
                    .build();

            return urlRepository.save(url);
        }

//        List<Url> existingUrls = urlRepository.findAllByOriginalUrl(originalUrl);
//
//        if (!existingUrls.isEmpty()) {
//            return existingUrls.get(0);
//        }

        Url url = Url.builder()
                .originalUrl(originalUrl)
                .user(user)
                .createdAt(LocalDateTime.now())
                .clickCount(0L)
                .build();

        Url savedUrl = urlRepository.save(url);

        String shortCode = Base62Encoder.encode(savedUrl.getId());
        savedUrl.setShortCode(shortCode);

        return urlRepository.save(savedUrl);
    }
	
	public String getOriginalUrl(String shortCode) {
		System.out.println("Attempting to retrieve original URL for short code: " + shortCode);
        String originalUrl = urlCacheService.getOriginalUrlFromCache(shortCode);
        
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("Short URL does not exist"));
        
        ClickEvent event = ClickEvent.builder()
                .shortCode(shortCode)
                .timestamp(LocalDateTime.now())
                .build();

        clickEventRepository.save(event);
        
        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);

        return originalUrl;
    }
	
	public AnalyticsResponse getAnalytics(String shortCode) {
		Url url = urlRepository.findByShortCode(shortCode)
				.orElseThrow(()-> new UrlNotFoundException("Short URL does not exist"));
	    long last24hClicks = clickEventRepository
	            .countByShortCodeAndTimestampAfter(
	                    shortCode,
	                    LocalDateTime.now().minusHours(24)
	            );

	    return AnalyticsResponse.builder()
	            .shortCode(shortCode)
	            .originalUrl(url.getOriginalUrl())
	            .totalClicks(url.getClickCount())
	            .last24HoursClicks(last24hClicks)
	            .createdAt(url.getCreatedAt())
	            .build();
	}

}
