package com.evergreen.EvergreenPaymentServer.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.evergreen.EvergreenPaymentServer.models.ProductModel;
import com.evergreen.lib.dtos.product.ProductDto;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    ProductModel toEntity(ProductDto productDto);

    List<ProductModel> toEntityList(List<ProductDto> productDtos);

    @Mapping(target = "category.products", ignore = true)
    ProductDto toDto(ProductModel product);

    List<ProductDto> toDtosList(List<ProductModel> products);


}
