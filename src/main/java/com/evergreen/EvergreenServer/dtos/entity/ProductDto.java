package com.evergreen.EvergreenServer.dtos.entity;

import java.time.Instant;
import com.evergreen.EvergreenServer.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Integer id;
    private String name;
    private String description;
    private Category category;
    private Instant createdAt;
    private Instant updatedAt;
}
