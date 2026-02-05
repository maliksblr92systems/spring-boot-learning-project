package com.evergreen.lib.dtos.appuser;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {

    private int id;

    private String name;

    private String email;

    private String phoneNumber;

    private String username;

    private String password;

    private Boolean isActive;

    private Instant createdAt;

    private Instant updatedAt;

}
