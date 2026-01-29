package com.evergreen.EvergreenAuthServer.dtos.requests.product;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @NotNull
    @Min(1)
    @Max(1000)
    private int stock;

    @NotNull
    @Min(1)
    @Max(100000)
    private double price;


}
