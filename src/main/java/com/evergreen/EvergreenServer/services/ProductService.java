package com.evergreen.EvergreenServer.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.evergreen.EvergreenServer.advices.ApiException;
import com.evergreen.EvergreenServer.dtos.entity.ProductDto;
import com.evergreen.EvergreenServer.dtos.requests.product.CreateProductRequestDto;
import com.evergreen.EvergreenServer.mappers.ProductMapper;
import com.evergreen.EvergreenServer.models.Category;
import com.evergreen.EvergreenServer.models.Product;
import com.evergreen.EvergreenServer.repositories.CategoryRepository;
import com.evergreen.EvergreenServer.repositories.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;


    public ProductService(ProductRepository productRepository, ProductMapper productMapper, CategoryRepository categoryRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = this.productRepository.findAll();
        return productMapper.toDtosList(products);
    }

    public List<ProductDto> getByCategory(int categoryId) {
        List<Product> products = this.productRepository.findByCategoryId(categoryId);
        return productMapper.toDtosList(products);
    }

    public ProductDto getProductById(int id) {
        ProductDto productDto = this.productRepository.findById(id).map(productMapper::toDto).orElseThrow(() -> {
            throw ApiException.notFound("Product not found.");
        });
        return productDto;
    }

    public ProductDto createProduct(CreateProductRequestDto requestDto) {
        String name = requestDto.getName();
        Optional<Product> productWithNameExists = this.productRepository.findByName(name);
        if (productWithNameExists.isPresent()) {
            throw ApiException.badRequest("Product with name " + name + " already exists");
        }

        String description = requestDto.getDescription();
        int categoryId = requestDto.getCategoryId();
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw ApiException.notFound("Category not found.");
        });
        Product newProduct = new Product();
        newProduct.setCategory(category);
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct = this.productRepository.save(newProduct);

        return productMapper.toDto(newProduct);
    }


}
