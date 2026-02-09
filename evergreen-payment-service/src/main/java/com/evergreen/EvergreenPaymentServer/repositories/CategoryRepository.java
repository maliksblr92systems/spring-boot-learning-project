package com.evergreen.EvergreenPaymentServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evergreen.EvergreenPaymentServer.models.CategoryModel;


@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Integer> {


}
