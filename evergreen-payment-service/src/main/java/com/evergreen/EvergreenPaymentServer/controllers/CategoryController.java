package com.evergreen.EvergreenPaymentServer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
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

import com.evergreen.EvergreenPaymentServer.dtos.category.CreateCategoryRequestDto;
import com.evergreen.EvergreenPaymentServer.dtos.category.UpdateCategoryByIdRequestDto;
import com.evergreen.EvergreenPaymentServer.services.category.CategoryService;
import com.evergreen.EvergreenPaymentServer.services.category.ICategoryService;
import com.evergreen.lib.dtos.category.CategoryDto;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/v1/payment/category")
public class CategoryController {

    @Qualifier("categoryService")
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CreateCategoryRequestDto request) {
        CategoryDto response = this.categoryService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<>(this.categoryService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "id") int id) {
        CategoryDto response = this.categoryService.getOne(id);
        return new ResponseEntity<CategoryDto>(response, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<CategoryDto> updateById(@RequestBody @Valid UpdateCategoryByIdRequestDto request) {
        CategoryDto response = this.categoryService.update(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable(name = "id") int id) {
        int response = this.categoryService.delete(id);

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }



}
