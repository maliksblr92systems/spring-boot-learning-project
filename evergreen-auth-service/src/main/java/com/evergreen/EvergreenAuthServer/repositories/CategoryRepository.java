package com.evergreen.EvergreenAuthServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evergreen.EvergreenAuthServer.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {


}
