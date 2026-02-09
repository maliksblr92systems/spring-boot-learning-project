package com.evergreen.EvergreenPaymentServer.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.evergreen.EvergreenPaymentServer.models.CategoryModel;
import com.evergreen.lib.dtos.category.CategoryDto;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "products", ignore = true)
    CategoryModel toEntity(CategoryDto categoryDto);

    List<CategoryModel> toEntityList(List<CategoryDto> categoriesDtos);

    @Mapping(target = "products", ignore = true)
    CategoryDto toDto(CategoryModel category);

    List<CategoryDto> toDtoList(List<CategoryModel> categories);

}
