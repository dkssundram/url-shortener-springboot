package com.dks.urlshortener.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnalyticsResponse {
	private String shortCode;
	private String originalUrl;
	private Long totalClicks;
	private LocalDateTime createdAt;
	private Long last24HoursClicks;
}
