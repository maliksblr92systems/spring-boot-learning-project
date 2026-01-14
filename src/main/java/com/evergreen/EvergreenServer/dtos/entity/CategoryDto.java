package com.evergreen.EvergreenServer.dtos.entity;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDto {
    private int id;
    private String name;
    private String thumbnail;
    private List<ProductDto> products;
    private Instant createdAt;
    private Instant updatedAt;
}
