package com.dks.urlshortener.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls",
       indexes = {
           @Index(name = "idx_short_code", columnList = "shortCode", unique = true)
       })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String originalUrl;

    @Column(unique = true)
    private String shortCode;

    private LocalDateTime createdAt;

    private LocalDateTime expiryDate;

    private Long clickCount;
}
