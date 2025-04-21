package com.ecristobale.apifirst.apifirstspringboot.controllers;

import com.ecristobale.apifirst.model.CustomerDto;
import com.ecristobale.apifirst.apifirstspringboot.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(CustomerController.BASE_PATH)
public class CustomerController {

    public static final String BASE_PATH = "/api/v1/customers";

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> listCustomers() {
        return ResponseEntity.ok(customerService.listCustomers());
    }

    @GetMapping("{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customerId") UUID customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @PostMapping
    public ResponseEntity<Void> saveNewCustomer(@RequestBody CustomerDto customer) {
        CustomerDto savedCustomer = customerService.saveNewCustomer(customer);

        UriComponents uriComponents = UriComponentsBuilder.fromPath(BASE_PATH + "/{customerId}" )
                .buildAndExpand(savedCustomer.getId());

        return ResponseEntity.created(URI.create(uriComponents.getPath())).build();
    }
}
