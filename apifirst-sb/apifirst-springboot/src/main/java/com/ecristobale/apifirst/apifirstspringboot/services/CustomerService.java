package com.ecristobale.apifirst.apifirstspringboot.services;

import com.ecristobale.apifirst.model.CustomerDto;
import com.ecristobale.apifirst.model.CustomerPatchDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDto> listCustomers();

    CustomerDto getCustomerById(UUID customerId);

    CustomerDto saveNewCustomer(CustomerDto customer);

    CustomerDto updateCustomer(UUID customerId, CustomerDto customer);

    CustomerDto patchCustomer(UUID customerId, CustomerPatchDto customer);
}
