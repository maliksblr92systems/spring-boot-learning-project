package com.evergreen.EvergreenPaymentServer.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.evergreen.EvergreenPaymentServer.models.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    List<ProductModel> findByCategoryId(Integer categoryId);

    Optional<ProductModel> findByName(String name);

}
