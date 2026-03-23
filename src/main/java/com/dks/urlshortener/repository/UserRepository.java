package com.dks.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dks.urlshortener.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail (String email);
}
