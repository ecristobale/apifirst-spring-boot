package com.ecristobale.apifirst.apifirstspringboot.controllers;

import com.ecristobale.apifirst.model.OrderDto;
import com.ecristobale.apifirst.apifirstspringboot.services.OrderService;
import com.ecristobale.apifirst.model.OrderCreateDto;
import com.ecristobale.apifirst.model.OrderPatchDto;
import com.ecristobale.apifirst.model.OrderUpdateDto;
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
    public ResponseEntity<List<OrderDto>> listOrders(){
        return ResponseEntity.ok(orderService.listOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getProductById(@PathVariable("orderId") UUID orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PostMapping
    public ResponseEntity<Void> saveNewCustomer(@RequestBody OrderCreateDto orderCreate) {
        OrderDto savedOrder = orderService.saveNewOrder(orderCreate);

        UriComponents uriComponents = UriComponentsBuilder.fromPath(BASE_PATH + "/{orderId}" )
                .buildAndExpand(savedOrder.getId());

        return ResponseEntity.created(URI.create(uriComponents.getPath())).build();
    }

    @PutMapping("{orderId}")
    public ResponseEntity<OrderDto> updateCustomer(@PathVariable("orderId") UUID orderId,
                                                   @RequestBody OrderUpdateDto orderUpdateDto) {
        OrderDto updatedOrder = orderService.updateOrder(orderId, orderUpdateDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderDto> patchOrder(@PathVariable("orderId") UUID orderId,
                                               @RequestBody OrderPatchDto orderPatchDto){
        OrderDto patchedOrder = orderService.patchOrder(orderId, orderPatchDto);
        return ResponseEntity.ok(patchedOrder);
    }
}
