package com.evergreen.EvergreenAuthServer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.evergreen.EvergreenAuthServer.dtos.requests.product.CreateProductRequestDto;
import com.evergreen.EvergreenAuthServer.models.Category;
import com.evergreen.EvergreenAuthServer.models.Product;
import com.evergreen.EvergreenAuthServer.repositories.CategoryRepository;
import com.evergreen.EvergreenAuthServer.repositories.ProductRepository;
import com.evergreen.EvergreenPaymentServer.mappers.ProductMapper;
import com.evergreen.lib.dtos.product.ProductDto;
import com.evergreen.lib.utils.ApiException;

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
        newProduct.setPrice(requestDto.getPrice());
        newProduct.setStock(requestDto.getStock());
        newProduct = this.productRepository.save(newProduct);

        return productMapper.toDto(newProduct);
    }

}
