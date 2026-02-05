package com.evergreen.lib.dtos.product;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private int id;
    private String name;
    private String description;
    private String thumbnail;
    private CategoryDto category;
    private int stock;
    private double price;
    private Instant createdAt;
    private Instant updatedAt;
}
