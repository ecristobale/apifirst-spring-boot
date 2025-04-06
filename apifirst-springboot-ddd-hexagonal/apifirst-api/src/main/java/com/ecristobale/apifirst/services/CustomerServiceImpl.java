package com.ecristobale.apifirst.services;

import com.ecristobale.apifirst.model.Customer;
import com.ecristobale.apifirst.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> listCustomers() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .toList();
    }
}
