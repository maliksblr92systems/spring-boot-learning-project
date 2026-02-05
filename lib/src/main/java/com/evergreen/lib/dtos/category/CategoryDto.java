package com.evergreen.lib.dtos.category;

import java.time.Instant;
import java.util.List;
import com.evergreen.lib.dtos.product.LightweightProductDto;
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
    private List<LightweightProductDto> products;
    private Instant createdAt;
    private Instant updatedAt;
}
