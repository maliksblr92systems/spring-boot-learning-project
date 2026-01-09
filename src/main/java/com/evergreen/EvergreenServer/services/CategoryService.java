package com.evergreen.EvergreenServer.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.evergreen.EvergreenServer.advices.ApiException;
import com.evergreen.EvergreenServer.dtos.requests.category.CreateCategoryRequestDto;
import com.evergreen.EvergreenServer.dtos.requests.category.CreateCategoryResponseDto;
import com.evergreen.EvergreenServer.dtos.requests.category.UpdateCategoryByIdRequestDto;
import com.evergreen.EvergreenServer.models.Category;
import com.evergreen.EvergreenServer.repositories.CategoryRepository;

@Service
public class CategoryService {


    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CreateCategoryResponseDto createCategory(CreateCategoryRequestDto request) {
        String name = request.getName();
        Category newCategory = new Category();
        newCategory.setName(name);
        Category category = categoryRepository.save(newCategory);
        CreateCategoryResponseDto response = new CreateCategoryResponseDto(category);
        return response;
    }

    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    public Category getById(int id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> ApiException.notFound("Category not found."));
        return category;
    }

    public Category updateById(UpdateCategoryByIdRequestDto request) {
        int id = request.getId();
        String name = request.getName();
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> ApiException.notFound("Category not found."));
        category.setName(name);
        category = categoryRepository.save(category);
        return category;
    }

    public int deleteById(int id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> ApiException.notFound("Category not found."));
        categoryRepository.delete(category);
        return id;
    }

}
