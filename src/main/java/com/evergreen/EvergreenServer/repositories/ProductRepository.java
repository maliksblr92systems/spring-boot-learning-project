package com.evergreen.EvergreenServer.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.evergreen.EvergreenServer.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    public List<Product> findByCategory(int categoryId);

}
