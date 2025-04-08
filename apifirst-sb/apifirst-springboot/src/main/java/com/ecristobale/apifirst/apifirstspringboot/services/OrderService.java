package com.ecristobale.apifirst.apifirstspringboot.services;

import com.ecristobale.apifirst.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<Order> listOrders();

    Order getOrderById(UUID orderId);
}
