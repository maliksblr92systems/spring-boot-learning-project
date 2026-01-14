package com.evergreen.EvergreenServer.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.evergreen.EvergreenServer.dtos.entity.ProductDto;
import com.evergreen.EvergreenServer.models.Product;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ProductMapper {

    Product toEntity(ProductDto productDto);


    List<Product> toEntityList(List<ProductDto> productDtos);

    @Mapping(target = "category.products", ignore = true)
    ProductDto toDto(Product product);

    List<ProductDto> toDtosList(List<Product> products);


}
