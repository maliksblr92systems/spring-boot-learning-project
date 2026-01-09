package com.evergreen.EvergreenServer.dtos.requests.category;

import com.evergreen.EvergreenServer.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryResponseDto {

    private Category category;
}
