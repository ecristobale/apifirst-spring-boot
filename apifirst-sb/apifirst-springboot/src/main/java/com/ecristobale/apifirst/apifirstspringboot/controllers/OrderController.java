package com.ecristobale.apifirst.apifirstspringboot.controllers;

import com.ecristobale.apifirst.model.Customer;
import com.ecristobale.apifirst.model.Order;
import com.ecristobale.apifirst.apifirstspringboot.services.OrderService;
import com.ecristobale.apifirst.model.OrderCreate;
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
@RequestMapping(OrderController.BASE_PATH)
public class OrderController {

    public static final String BASE_PATH = "/api/v1/orders";

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> listOrders(){
        return ResponseEntity.ok(orderService.listOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getProductById(@PathVariable("orderId") UUID orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PostMapping
    public ResponseEntity<Void> saveNewCustomer(@RequestBody OrderCreate orderCreate) {
        Order savedOrder = orderService.saveNewCustomer(orderCreate);

        UriComponents uriComponents = UriComponentsBuilder.fromPath(BASE_PATH + "/{orderId}" )
                .buildAndExpand(savedOrder.getId());

        return ResponseEntity.created(URI.create(uriComponents.getPath())).build();
    }
}
