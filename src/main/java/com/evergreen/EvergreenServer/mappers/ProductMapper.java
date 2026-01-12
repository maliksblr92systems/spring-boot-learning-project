package com.evergreen.EvergreenServer.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import com.evergreen.EvergreenServer.dtos.entity.ProductDto;
import com.evergreen.EvergreenServer.models.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductDto productDto);

    List<Product> toEntityList(List<ProductDto> productDtos);

    ProductDto toDto(Product product);

    List<ProductDto> toDtosList(List<Product> products);


}
