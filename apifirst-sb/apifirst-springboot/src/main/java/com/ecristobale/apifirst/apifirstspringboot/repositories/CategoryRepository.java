package com.ecristobale.apifirst.apifirstspringboot.repositories;

import com.ecristobale.apifirst.apifirstspringboot.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
