package com.ecristobale.apifirst.apifirstspringboot.repositories;

import com.ecristobale.apifirst.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID> {
    // This interface extends CrudRepository to provide CRUD operations for Customer entities.
    // The UUID type is used as the identifier for the Customer entity.
    // Additional query methods can be defined here if needed.
}
