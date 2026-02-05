package com.evergreen.EvergreenPaymentServer.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.evergreen.EvergreenPaymentServer.models.OrderModel;
import com.evergreen.lib.dtos.order.OrderDto;


@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productThumbnail", source = "product.thumbnail")

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.name")
    @Mapping(target = "userEmail", source = "user.email")
    OrderDto toDto(OrderModel order);

    List<OrderDto> toDtoList(List<OrderModel> orders);

}
