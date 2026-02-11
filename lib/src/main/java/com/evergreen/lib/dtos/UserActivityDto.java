package com.evergreen.lib.dtos;

import java.time.Instant;

import com.evergreen.lib.constants.enums.UserActivityStatus;
import com.evergreen.lib.constants.enums.UserActivityType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserActivityDto {
    private Integer id;

    private int userId;

    private UserActivityType type;

    private UserActivityStatus status;

    private Instant createdAt;

    private Instant updatedAt;

}
