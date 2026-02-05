package com.evergreen.lib.dtos.order;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Integer id;
    // user
    private int userId;
    private String userEmail;
    private String username;

    // product
    private int productId;
    private String productName;
    private String productThumbnail;


    private int quantity;

    private double amount;

    private Instant createdAt;

    private Instant updatedAt;


}
