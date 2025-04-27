package com.ecristobale.apifirst.apifirstspringboot.controllers;

import com.ecristobale.apifirst.apifirstspringboot.domain.Order;
import com.ecristobale.apifirst.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class OrderControllerTest extends BaseTest {

    @DisplayName("Test List Products")
    @Test
    void listOrders() throws Exception {
        mockMvc.perform(get(OrderController.BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @DisplayName("Test Get Order by ID")
    @Test
    void testGetOrderById() throws Exception {
        mockMvc.perform(get(OrderController.BASE_PATH + "/{orderId}", testOrder.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testOrder.getId().toString()));
    }

    @DisplayName("Test Create Order")
    @Test
    @Transactional
    void testCreateOrder() throws Exception {
        OrderCreateDto orderCreate = buildTestOrderDto();

        System.out.println(objectMapper.writeValueAsString(orderCreate));

        mockMvc.perform(post(OrderController.BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderCreate)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @DisplayName("Test Update Order")
    @Test
    @Transactional
    void testUpdateOrder() throws Exception {

        Order order = orderRepository.findAll().get(0);

        order.getOrderLines().get(0).setOrderQuantity(222);

        OrderUpdateDto orderUpdate = orderMapper.orderToOrderUpdateDto(order);

        mockMvc.perform(put(OrderController.BASE_PATH + "/{orderId}", testOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderUpdate))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(testOrder.getId().toString())))
                .andExpect(jsonPath("$.orderLines.length()", greaterThan(0)))
                .andExpect(jsonPath("$.orderLines[0].orderQuantity", equalTo(222)));
    }

    @DisplayName("Test Patch Order")
    @Test
    @Transactional
    void testPatchOrder() throws Exception {

        Order order = orderRepository.findAll().get(0);

        OrderPatchDto orderPatch = OrderPatchDto.builder()
                .orderLines(Collections.singletonList(OrderLinePatchDto.builder()
                        .id(order.getOrderLines().get(0).getId())
                        .orderQuantity(333)
                        .build()))
                .build();

        mockMvc.perform(patch(OrderController.BASE_PATH + "/{orderId}", testOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderPatch))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(testOrder.getId().toString())))
                .andExpect(jsonPath("$.orderLines[0].orderQuantity", equalTo(333)));
    }

    @DisplayName("Test Delete Order")
    @Test
    @Transactional
    void testDeleteOrder() throws Exception {
        OrderCreateDto order = buildTestOrderDto();
        Order savedOrder = orderRepository.save(orderMapper.orderCreateDtoToOrder(order));

        mockMvc.perform(delete(OrderController.BASE_PATH + "/{orderId}", savedOrder.getId()))
                .andExpect(status().isNoContent());

        assert orderRepository.findById(savedOrder.getId()).isEmpty();
    }

    @Test
    @DisplayName("Test Delete Order Not Found")
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(OrderController.BASE_PATH + "/{orderId}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    private OrderCreateDto buildTestOrderDto() {
        return OrderCreateDto.builder()
                .customerId(testCustomer.getId())
                .selectPaymentMethodId(testCustomer.getPaymentMethods().get(0).getId())
                .orderLines(Arrays.asList(OrderLineCreateDto.builder()
                        .productId(testProduct.getId())
                        .orderQuantity(2)
                        .build()))
                .build();
    }
}
