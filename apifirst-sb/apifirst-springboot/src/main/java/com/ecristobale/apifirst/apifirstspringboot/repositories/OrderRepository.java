package com.ecristobale.apifirst.apifirstspringboot.repositories;

import com.ecristobale.apifirst.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {
}
