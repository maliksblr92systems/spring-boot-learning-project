package com.evergreen.EvergreenAuthServer.services.category;

import java.util.List;
import com.evergreen.EvergreenAuthServer.dtos.requests.category.CreateCategoryRequestDto;
import com.evergreen.EvergreenAuthServer.dtos.requests.category.UpdateCategoryByIdRequestDto;
import com.evergreen.lib.dtos.category.CategoryDto;

/**
 * High-level modules should not directly depend on low-level modules; both should depend on abstractions. Abstractions should not depend on concrete implementations; instead, concrete implementations
 * should depend on abstractions.
 *
 * In this example, {@code CategoryController} depends on the {@code ICategoryService} interface rather than being directly coupled to {@code CategoryService}. This ensures that the high-level module
 * does not import the low-level module, resulting in loose coupling.
 *
 * Similarly, {@code CategoryService} depends on the abstraction ({@code ICategoryService}), ensuring that concrete details depend on abstractions, in accordance with the Dependency Inversion
 * Principle.
 */

public interface ICategoryService {

    public CategoryDto create(CreateCategoryRequestDto request);

    public List<CategoryDto> getAll();

    public CategoryDto getOne(int id);

    public CategoryDto update(UpdateCategoryByIdRequestDto request);

    public int delete(int id);

}
