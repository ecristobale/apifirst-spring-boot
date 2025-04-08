package com.ecristobale.apifirst.apifirstspringboot.repositories;

import com.ecristobale.apifirst.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
}
