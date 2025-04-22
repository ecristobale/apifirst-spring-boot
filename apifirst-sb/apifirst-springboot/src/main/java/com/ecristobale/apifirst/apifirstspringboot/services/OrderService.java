package com.ecristobale.apifirst.apifirstspringboot.services;

import com.ecristobale.apifirst.model.OrderDto;
import com.ecristobale.apifirst.model.OrderCreateDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderDto> listOrders();

    OrderDto getOrderById(UUID orderId);

    OrderDto saveNewOrder(OrderCreateDto orderCreate);
}
