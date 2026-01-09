package com.evergreen.EvergreenServer.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.evergreen.EvergreenServer.dtos.requests.category.CreateCategoryRequestDto;
import com.evergreen.EvergreenServer.dtos.requests.category.CreateCategoryResponseDto;
import com.evergreen.EvergreenServer.dtos.requests.category.UpdateCategoryByIdRequestDto;
import com.evergreen.EvergreenServer.models.Category;
import com.evergreen.EvergreenServer.services.CategoryService;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    @PostMapping("")
    public ResponseEntity<CreateCategoryResponseDto> createCategory(@RequestBody @Valid CreateCategoryRequestDto request) {
        CreateCategoryResponseDto response = this.categoryService.createCategory(request);
        return new ResponseEntity<CreateCategoryResponseDto>(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> response = this.categoryService.getAll();
        return new ResponseEntity<List<Category>>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(name = "id") int id) {
        Category response = this.categoryService.getById(id);
        return new ResponseEntity<Category>(response, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Category> updateById(@RequestBody @Valid UpdateCategoryByIdRequestDto request) {
        Category response = this.categoryService.updateById(request);
        return new ResponseEntity<Category>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable(name = "id") int id) {
        int response = this.categoryService.deleteById(id);

        return new ResponseEntity<Integer>(response, HttpStatus.NO_CONTENT);
    }



}
