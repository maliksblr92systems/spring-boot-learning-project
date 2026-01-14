package com.evergreen.EvergreenServer.dtos.requests.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private int categoryId;

}
