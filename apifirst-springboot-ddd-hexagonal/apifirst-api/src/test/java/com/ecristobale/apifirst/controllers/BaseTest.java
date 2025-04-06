package com.ecristobale.apifirst.controllers;

import com.ecristobale.apifirst.model.Customer;
import com.ecristobale.apifirst.repositories.CustomerRepository;
import com.ecristobale.apifirst.repositories.OrderRepository;
import com.ecristobale.apifirst.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class BaseTest {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    WebApplicationContext wac;

    public MockMvc mockMvc;

    Customer testCustomer;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        testCustomer = customerRepository.findAll().iterator().next();
    }
}
