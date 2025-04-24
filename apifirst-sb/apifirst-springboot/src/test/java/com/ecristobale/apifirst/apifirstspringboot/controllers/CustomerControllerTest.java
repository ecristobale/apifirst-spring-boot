package com.ecristobale.apifirst.apifirstspringboot.controllers;

import com.ecristobale.apifirst.apifirstspringboot.domain.Customer;
import com.ecristobale.apifirst.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;


import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class CustomerControllerTest extends BaseTest {

    @DisplayName("Test List Customers")
    @Test
    void TestListCustomers() throws Exception {
        mockMvc.perform(get(CustomerController.BASE_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @DisplayName("Test Get Customer by ID")
    @Test
    void TestGetCustomerById() throws Exception {
        mockMvc.perform(get(CustomerController.BASE_PATH + "/{customerId}", testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testCustomer.getId().toString()));
    }

    @DisplayName("Test Create Customer")
    @Test
    void testCreateCustomer() throws Exception {
        CustomerDto newCustomer = CustomerDto.builder()
                .name(NameDto.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .build())
                .shipToAddress(AddressDto.builder()
                        .addressLine1("123 Main St")
                        .city("Denver")
                        .state("CO")
                        .zip("80216")
                        .build())
                .billToAddress(AddressDto.builder()
                        .addressLine1("123 Main St")
                        .city("Denver")
                        .state("CO")
                        .zip("80216")
                        .build())
                .phone("555-555-5555")
                .email("john@example.com")
                .build();

        mockMvc.perform(post(CustomerController.BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCustomer))) //convert to JSON
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    //test update customer
    @Transactional
    @DisplayName("Test Update Customer")
    @Test
    void testUpdateCustomer() throws Exception {
        Customer customer = customerRepository.findAll().iterator().next();

        customer.getName().setFirstName("Updated");
        customer.getName().setLastName("Updated2");
        customer.getPaymentMethods().get(0).setDisplayName("NEW NAME");

        mockMvc.perform(put(CustomerController.BASE_PATH + "/{customerId}", testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMapper.customerToCustomerDto(customer))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name.firstName", equalTo("Updated")))
                .andExpect(jsonPath("$.name.lastName", equalTo("Updated2")))
                .andExpect(jsonPath("$.paymentMethods[0].displayName", equalTo("NEW NAME")));
    }
}
