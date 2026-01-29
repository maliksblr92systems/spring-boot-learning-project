package com.evergreen.EvergreenAuthServer.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.evergreen.EvergreenAuthServer.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryId(Integer categoryId);

    Optional<Product> findByName(String name);

}
