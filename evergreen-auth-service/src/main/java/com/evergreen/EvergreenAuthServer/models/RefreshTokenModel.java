package com.evergreen.EvergreenAuthServer.models;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "refresh_tokens")
@Entity(name = "refresh_tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RefreshTokenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Integer userId;

    @Column(name = "hash", nullable = false, updatable = false, unique = true)
    private String hash;

    @Column(name = "expiry", nullable = false, updatable = false)
    private Instant expiry;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @Column(name = "device_info", nullable = true)
    private String deviceInfo;

}
