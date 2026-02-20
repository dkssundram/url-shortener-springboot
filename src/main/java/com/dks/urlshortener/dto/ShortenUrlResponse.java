package com.dks.urlshortener.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortenUrlResponse {
	private String shortUrl;
	private LocalDateTime createdAt;
}
