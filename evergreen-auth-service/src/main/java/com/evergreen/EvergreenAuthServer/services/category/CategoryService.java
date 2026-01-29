package com.evergreen.EvergreenAuthServer.services.category;

import java.util.List;
import org.springframework.stereotype.Service;

import com.evergreen.EvergreenAuthServer.advices.ApiException;
import com.evergreen.EvergreenAuthServer.dtos.entity.CategoryDto;
import com.evergreen.EvergreenAuthServer.dtos.requests.category.CreateCategoryRequestDto;
import com.evergreen.EvergreenAuthServer.dtos.requests.category.UpdateCategoryByIdRequestDto;
import com.evergreen.EvergreenAuthServer.mappers.CategoryMapper;
import com.evergreen.EvergreenAuthServer.models.Category;
import com.evergreen.EvergreenAuthServer.repositories.CategoryRepository;

@Service
public class CategoryService implements ICategoryService {


    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryDto create(CreateCategoryRequestDto request) {
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

    public CategoryDto getOne(int id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> ApiException.notFound("Category not found."));
        return categoryMapper.toDto(category);
    }

    public CategoryDto update(UpdateCategoryByIdRequestDto request) {
        int id = request.getId();
        String name = request.getName();
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> ApiException.notFound("Category not found."));
        category.setName(name);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    public int delete(int id) {
        this.categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, () -> {
            throw ApiException.notFound("Category not found.");
        });
        return id;
    }


}
