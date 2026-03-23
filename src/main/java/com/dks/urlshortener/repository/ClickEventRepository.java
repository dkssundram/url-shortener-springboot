package com.dks.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dks.urlshortener.model.ClickEvent;

public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {
	
	Optional<ClickEvent> findByShortCode(String shortCode);
	long countByShortCodeAndTimestampAfter(String shortCode, java.time.LocalDateTime time);
	
}
