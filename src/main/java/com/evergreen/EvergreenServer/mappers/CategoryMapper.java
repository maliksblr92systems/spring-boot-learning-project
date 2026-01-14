package com.evergreen.EvergreenServer.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.evergreen.EvergreenServer.dtos.entity.CategoryDto;
import com.evergreen.EvergreenServer.models.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryDto categoryDto);

    List<Category> toEntityList(List<CategoryDto> categoriesDtos);

    @Mapping(target = "products", ignore = true)
    CategoryDto toDto(Category category);

    List<CategoryDto> toDtoList(List<Category> categories);


}
