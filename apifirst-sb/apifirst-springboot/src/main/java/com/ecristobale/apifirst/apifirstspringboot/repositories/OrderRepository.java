package com.ecristobale.apifirst.apifirstspringboot.repositories;

import com.ecristobale.apifirst.apifirstspringboot.domain.Customer;
import com.ecristobale.apifirst.apifirstspringboot.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByCustomer(Customer customer);
}
