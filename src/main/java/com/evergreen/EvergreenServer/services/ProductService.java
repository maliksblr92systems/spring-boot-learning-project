package com.evergreen.EvergreenServer.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.evergreen.EvergreenServer.advices.ApiException;
import com.evergreen.EvergreenServer.dtos.entity.ProductDto;
import com.evergreen.EvergreenServer.mappers.ProductMapper;
import com.evergreen.EvergreenServer.models.Product;
import com.evergreen.EvergreenServer.repositories.ProductRepository;

@Service
public class ProductService {

    public final ProductRepository productRepository;
    public final ProductMapper productMapper;


    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = this.productRepository.findAll();
        return productMapper.toDtosList(products);
    }

    public List<ProductDto> getByCategory(int categoryId) {
        List<Product> products = this.productRepository.findByCategory(categoryId);
        return productMapper.toDtosList(products);
    }

    public ProductDto getProductById(int id) {
        ProductDto productDto = this.productRepository.findById(id).map(productMapper::toDto).orElseThrow(() -> {
            throw ApiException.notFound("Product not found.");
        });
        return productDto;
    }

    public ProductDto createProduct(CreateProductRequestDto){
        this.productRepository.save();
    }


}
