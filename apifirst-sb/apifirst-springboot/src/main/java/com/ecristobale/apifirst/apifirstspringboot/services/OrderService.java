package com.ecristobale.apifirst.apifirstspringboot.services;

import com.ecristobale.apifirst.model.OrderDto;
import com.ecristobale.apifirst.model.OrderCreateDto;
import com.ecristobale.apifirst.model.OrderPatchDto;
import com.ecristobale.apifirst.model.OrderUpdateDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderDto> listOrders();

    OrderDto getOrderById(UUID orderId);

    OrderDto saveNewOrder(OrderCreateDto orderCreate);

    OrderDto updateOrder(UUID orderId, OrderUpdateDto orderUpdateDto);

    OrderDto patchOrder(UUID orderId, OrderPatchDto orderPatchDto);

    void deleteOrder(UUID orderId);
}
