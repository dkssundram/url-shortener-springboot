package com.dks.urlshortener.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dks.urlshortener.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UrlNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUrlNotFound(UrlNotFoundException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorCode("URL_NOT_FOUND")
				.message(ex.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorCode("INTERNAL_SERVER_ERROR")
				.message("An unexpected error occurred")
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(AliasAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAliasException(AliasAlreadyExistsException ex) {

        ErrorResponse error = ErrorResponse.builder()
                .errorCode("ALIAS_ALREADY_EXISTS")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
