package com.ecristobale.apifirst.apifirstspringboot.repositories;

import com.ecristobale.apifirst.apifirstspringboot.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByCategoryCode(String categoryCode);
}
