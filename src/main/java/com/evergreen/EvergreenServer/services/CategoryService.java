package com.evergreen.EvergreenServer.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.evergreen.EvergreenServer.advices.ApiException;
import com.evergreen.EvergreenServer.dtos.entity.CategoryDto;
import com.evergreen.EvergreenServer.dtos.requests.category.CreateCategoryRequestDto;
import com.evergreen.EvergreenServer.dtos.requests.category.UpdateCategoryByIdRequestDto;
import com.evergreen.EvergreenServer.mappers.CategoryMapper;
import com.evergreen.EvergreenServer.models.Category;
import com.evergreen.EvergreenServer.repositories.CategoryRepository;

@Service
public class CategoryService {


    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryDto createCategory(CreateCategoryRequestDto request) {
        String name = request.getName();
        Category newCategory = new Category();
        newCategory.setName(name);
        Category category = categoryRepository.save(newCategory);
        return categoryMapper.toDto(category);
    }

    public List<CategoryDto> getAll() {
        List<Category> categoriesList = this.categoryRepository.findAll();
        return categoryMapper.toDtoList(categoriesList);

    }

    public CategoryDto getById(int id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> ApiException.notFound("Category not found."));
        return categoryMapper.toDto(category);
    }

    public CategoryDto updateById(UpdateCategoryByIdRequestDto request) {
        int id = request.getId();
        String name = request.getName();
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> ApiException.notFound("Category not found."));
        category.setName(name);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    public int deleteById(int id) {
        this.categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, () -> {
            throw ApiException.notFound("Category not found.");
        });
        return id;
    }

}
