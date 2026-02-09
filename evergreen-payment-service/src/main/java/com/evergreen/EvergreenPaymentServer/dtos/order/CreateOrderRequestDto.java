package com.evergreen.EvergreenPaymentServer.dtos.order;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderRequestDto {

    @NotNull
    // @Min(1)
    // @Max(10)
    private Integer quantity;

    @NotNull
    private Integer productId;

}
