package com.ecristobale.apifirst.apifirstspringboot.services;

import com.ecristobale.apifirst.apifirstspringboot.domain.Customer;
import com.ecristobale.apifirst.apifirstspringboot.mappers.CustomerMapper;
import com.ecristobale.apifirst.apifirstspringboot.repositories.CustomerRepository;
import com.ecristobale.apifirst.model.CustomerDto;
import com.ecristobale.apifirst.model.CustomerPatchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDto> listCustomers() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }

    @Override
    public CustomerDto getCustomerById(UUID customerId) {
        return customerMapper.customerToCustomerDto(
                customerRepository.findById(customerId).orElseThrow());
    }

    @Transactional
    @Override
    public CustomerDto saveNewCustomer(CustomerDto customer) {
        Customer savedCustomer = customerRepository.save(customerMapper.customerDtoToCustomer(customer));
        customerRepository.flush();
        return customerMapper.customerToCustomerDto(savedCustomer);
    }

    @Transactional
    @Override
    public CustomerDto updateCustomer(UUID customerId, CustomerDto customer) {
        Customer existingCustomer = customerRepository.findById(customerId).orElseThrow();
        customerMapper.updateCustomer(customer, existingCustomer);
        return customerMapper.customerToCustomerDto(customerRepository.save(existingCustomer));
    }

    @Transactional
    @Override
    public CustomerDto patchCustomer(UUID customerId, CustomerPatchDto customer) {
        Customer existingCustomer = customerRepository.findById(customerId).orElseThrow();

        customerMapper.patchCustomer(customer, existingCustomer);

        return customerMapper.customerToCustomerDto(customerRepository.saveAndFlush(existingCustomer));
    }

    @Transactional
    @Override
    public void deleteCustomer(UUID customerId) {
        customerRepository.findById(customerId).ifPresentOrElse(customerRepository::delete, () -> {
            throw new NotFoundException("Customer Not Found");
        });
    }
}
