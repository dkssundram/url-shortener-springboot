package com.dks.urlshortener.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dks.urlshortener.model.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortCode(String shortCode);
    Optional<Url> findByOriginalUrl(String originalUrl);
    List<Url> findAllByOriginalUrl(String originalUrl);
}
