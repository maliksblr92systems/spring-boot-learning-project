package com.evergreen.EvergreenServer.security.dtos;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class ProtectedAppUserDto {

    private Integer id;
    private String email;
    private String username;
    private String phoneNumber;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;



}
