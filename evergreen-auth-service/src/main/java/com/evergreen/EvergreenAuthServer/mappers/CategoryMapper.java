package com.evergreen.EvergreenAuthServer.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.evergreen.EvergreenAuthServer.dtos.entity.CategoryDto;
import com.evergreen.EvergreenAuthServer.models.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // @Mapping(target = "products", ignore = true)
    Category toEntity(CategoryDto categoryDto);

    List<Category> toEntityList(List<CategoryDto> categoriesDtos);

    CategoryDto toDto(Category category);

    List<CategoryDto> toDtoList(List<Category> categories);

}
