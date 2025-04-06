package com.ecristobale.apifirst.controllers;

import com.ecristobale.apifirst.model.Customer;
import com.ecristobale.apifirst.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.ecristobale.apifirst.controllers.CustomerController.BASE_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH)
public class CustomerController {

    public static final String BASE_PATH = "/api/v1/customers";

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> listCustomers() {
        return ResponseEntity.ok(customerService.listCustomers());
    }

    @GetMapping("{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") UUID customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }
}
