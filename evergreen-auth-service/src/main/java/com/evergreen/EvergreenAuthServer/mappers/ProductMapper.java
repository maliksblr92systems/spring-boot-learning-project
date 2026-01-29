package com.evergreen.EvergreenAuthServer.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.evergreen.EvergreenAuthServer.dtos.entity.ProductDto;
import com.evergreen.EvergreenAuthServer.models.Product;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    Product toEntity(ProductDto productDto);


    List<Product> toEntityList(List<ProductDto> productDtos);

    @Mapping(target = "category.products", ignore = true)
    ProductDto toDto(Product product);

    List<ProductDto> toDtosList(List<Product> products);


}
