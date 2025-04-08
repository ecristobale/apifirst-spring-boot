package com.ecristobale.apifirst.apifirstspringboot.services;

import com.ecristobale.apifirst.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> listCustomers();

    Customer getCustomerById(UUID customerId);
}
