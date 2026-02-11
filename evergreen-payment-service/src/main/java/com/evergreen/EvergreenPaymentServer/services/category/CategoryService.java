package com.evergreen.EvergreenPaymentServer.services.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.evergreen.EvergreenPaymentServer.dtos.category.CreateCategoryRequestDto;
import com.evergreen.EvergreenPaymentServer.dtos.category.UpdateCategoryByIdRequestDto;
import com.evergreen.EvergreenPaymentServer.mappers.CategoryMapper;
import com.evergreen.EvergreenPaymentServer.models.CategoryModel;
import com.evergreen.EvergreenPaymentServer.repositories.CategoryRepository;
import com.evergreen.lib.constants.enums.UserActivityStatus;
import com.evergreen.lib.constants.enums.UserActivityType;
import com.evergreen.lib.dtos.UserActivityEventDto;
import com.evergreen.lib.dtos.appuser.AuthUser;
import com.evergreen.lib.dtos.category.CategoryDto;
import com.evergreen.lib.utils.ApiException;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public CategoryDto create(CreateCategoryRequestDto request) {
        String name = request.getName();
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CategoryModel newCategory = new CategoryModel();
        newCategory.setName(name);
        newCategory.setCreatedBy(authUser.id());
        CategoryModel category = categoryRepository.save(newCategory);
        kafkaTemplate.send("user-activity-topic", new UserActivityEventDto(UserActivityType.CATEGORY_CREATION, UserActivityStatus.SUCCESS, authUser));
        return categoryMapper.toDto(category);

    }

    public List<CategoryDto> getAll() {
        List<CategoryModel> categoriesList = this.categoryRepository.findAll();
        return categoryMapper.toDtoList(categoriesList);

    }

    public CategoryDto getOne(int id) {
        CategoryModel category = this.categoryRepository.findById(id).orElseThrow(() -> ApiException.notFound("Category not found."));
        return categoryMapper.toDto(category);
    }

    public CategoryDto update(UpdateCategoryByIdRequestDto request) {
        int id = request.getId();
        String name = request.getName();
        CategoryModel category = this.categoryRepository.findById(id).orElseThrow(() -> ApiException.notFound("Category not found."));
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
