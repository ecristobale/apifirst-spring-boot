package com.ecristobale.apifirst.services;

import com.ecristobale.apifirst.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> listCustomers();

    Customer getCustomerById(UUID customerId);
}
