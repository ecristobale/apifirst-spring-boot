package com.ecristobale.apifirst.apifirstspringboot.controllers;

import com.ecristobale.apifirst.apifirstspringboot.domain.Customer;
import com.ecristobale.apifirst.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class CustomerControllerTest extends BaseTest {

    @DisplayName("Customer: List")
    @Test
    void TestListCustomers() throws Exception {
        mockMvc.perform(get(CustomerController.BASE_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @DisplayName("Customer: Get by ID")
    @Test
    void TestGetCustomerById() throws Exception {
        mockMvc.perform(get(CustomerController.BASE_PATH + "/{customerId}", testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testCustomer.getId().toString()));
    }

    @DisplayName("Customer: Get by Id Not Found")
    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        mockMvc.perform(get(CustomerController.BASE_PATH + "/{customerId}", UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Customer: Create")
    @Test
    void testCreateCustomer() throws Exception {
        CustomerDto newCustomer = buildTestCustomerDto();

        mockMvc.perform(post(CustomerController.BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCustomer))) //convert to JSON
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    //test update customer
    @Transactional
    @DisplayName("Customer: Update")
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

    @Transactional
    @DisplayName("Customer: Patch")
    @Test
    void testPatchCustomer() throws Exception {
        Customer customer = customerRepository.findAll().iterator().next();

        CustomerPatchDto customerPatch = CustomerPatchDto.builder()
                .name(CustomerNamePatchDto.builder()
                        .firstName("Patch Updated")
                        .lastName("Patch Updated2")
                        .build())
                .paymentMethods(Collections.singletonList(CustomerPaymentMethodPatchDto.builder()
                        .id(customer.getPaymentMethods().get(0).getId())
                        .displayName("Patch NEW NAME")
                        .build()))
                .build();

        mockMvc.perform(patch(CustomerController.BASE_PATH + "/{customerId}", testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerPatch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name.firstName", equalTo("Patch Updated")))
                .andExpect(jsonPath("$.name.lastName", equalTo("Patch Updated2")))
                .andExpect(jsonPath("$.paymentMethods[0].displayName", equalTo("Patch NEW NAME")));
    }

    @DisplayName("Customer: Delete")
    @Test
    void testDeleteCustomer() throws Exception {
        CustomerDto customer = buildTestCustomerDto();
        Customer savedCustomer = customerRepository.save(customerMapper.customerDtoToCustomer(customer));

        mockMvc.perform(delete(CustomerController.BASE_PATH + "/{customerId}", savedCustomer.getId()))
                .andExpect(status().isNoContent());

        assert customerRepository.findById(savedCustomer.getId()).isEmpty();
    }

    @Test
    @DisplayName("Customer: Delete Not Found")
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_PATH + "/{customerId}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    private CustomerDto buildTestCustomerDto() {
        return CustomerDto.builder()
                .name(NameDto.builder()
                        .lastName("Doe")
                        .firstName("Ecristobale")
                        .build())
                .phone("555-555-5555")
                .email("ecristobale@ecristobale.com")
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
                .build();
    }
}
