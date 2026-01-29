package com.evergreen.EvergreenAuthServer.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.evergreen.EvergreenAuthServer.dtos.entity.OrderDto;
import com.evergreen.EvergreenAuthServer.models.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productThumbnail", source = "product.thumbnail")

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.name")
    @Mapping(target = "userEmail", source = "user.email")
    OrderDto toDto(Order order);

    List<OrderDto> toDtoList(List<Order> orders);

}
