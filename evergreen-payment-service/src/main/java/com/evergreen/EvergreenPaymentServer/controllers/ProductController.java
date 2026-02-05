package com.evergreen.EvergreenAuthServer.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.evergreen.EvergreenAuthServer.dtos.requests.product.CreateProductRequestDto;
import com.evergreen.EvergreenAuthServer.services.ProductService;
import com.evergreen.lib.dtos.product.ProductDto;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid CreateProductRequestDto requestDto) {
        return new ResponseEntity<>(this.productService.createProduct(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(this.productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(this.productService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable(name = "id") int categoryId) {
        return new ResponseEntity<>(this.productService.getByCategory(categoryId), HttpStatus.OK);
    }

}
