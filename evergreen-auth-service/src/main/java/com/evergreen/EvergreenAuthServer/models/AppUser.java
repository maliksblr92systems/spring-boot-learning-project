package com.evergreen.EvergreenAuthServer.models;


import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@Entity(name = "users")
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class AppUser {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "email", nullable = true, unique = true)
    private String email;

    @Column(name = "phone", nullable = true, unique = true)
    private String phoneNumber;

    @Column(name = "username", nullable = true, unique = true)
    private String username;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "is_active", nullable = true)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, updatable = true)
    @LastModifiedDate
    private Instant updatedAt;


    @Override
    public String toString() {
        return "AppUser id=" + id + " name=" + name + " email=" + email + " username=" + username;
    }



}
