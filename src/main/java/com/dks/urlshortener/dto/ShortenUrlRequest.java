package com.dks.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ShortenUrlRequest {
	
	@NotBlank(message = "Original URL cannot be blank")
	@Pattern(regexp = "^(http|https)://.*$", message = "Invalid URL format")
	private String originalUrl;
}
