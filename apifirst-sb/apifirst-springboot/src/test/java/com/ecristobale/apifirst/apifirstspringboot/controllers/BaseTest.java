package com.ecristobale.apifirst.apifirstspringboot.controllers;

import com.ecristobale.apifirst.apifirstspringboot.domain.Customer;
import com.ecristobale.apifirst.apifirstspringboot.domain.Order;
import com.ecristobale.apifirst.apifirstspringboot.domain.Product;
import com.ecristobale.apifirst.apifirstspringboot.mappers.CustomerMapper;
import com.ecristobale.apifirst.apifirstspringboot.mappers.ProductMapper;
import com.ecristobale.apifirst.apifirstspringboot.repositories.CustomerRepository;
import com.ecristobale.apifirst.apifirstspringboot.repositories.OrderRepository;
import com.ecristobale.apifirst.apifirstspringboot.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
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

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    ObjectMapper objectMapper;

    Customer testCustomer;
    Product testProduct;
    Order testOrder;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        testCustomer = customerRepository.findAll().iterator().next();
        testProduct = productRepository.findAll().iterator().next();
        testOrder = orderRepository.findAll().iterator().next();
    }
}
