package com.ecristobale.apifirst.apifirstspringboot.services;

import com.ecristobale.apifirst.apifirstspringboot.domain.*;
import com.ecristobale.apifirst.apifirstspringboot.mappers.OrderMapper;
import com.ecristobale.apifirst.apifirstspringboot.repositories.OrderRepository;
import com.ecristobale.apifirst.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Override
    public List<OrderDto> listOrders() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map(orderMapper::orderToOrderDto)
                .toList();
    }

    @Override
    public OrderDto getOrderById(UUID orderId) {
        return orderMapper.orderToOrderDto(orderRepository.findById(orderId).orElseThrow());
    }

    @Override
    public OrderDto saveNewOrder(OrderCreateDto orderCreate) {


        Order savedOrder = orderRepository.saveAndFlush(orderMapper.orderCreateDtoToOrder(orderCreate));

        return orderMapper.orderToOrderDto(savedOrder);
    }

    @Transactional
    @Override
    public OrderDto updateOrder(UUID orderId, OrderUpdateDto orderUpdateDto) {
        Order existingOrder = orderRepository.findById(orderId).orElseThrow();

        orderMapper.updateOrder(orderUpdateDto, existingOrder);

        Order savedOrder = orderRepository.saveAndFlush(existingOrder);

        return orderMapper.orderToOrderDto(savedOrder);
    }

    @Override
    public OrderDto patchOrder(UUID orderId, OrderPatchDto orderPatchDto) {
        Order existingOrder = orderRepository.findById(orderId).orElseThrow();
        orderMapper.patchOrder(orderPatchDto, existingOrder);
        return orderMapper.orderToOrderDto(orderRepository.saveAndFlush(existingOrder));
    }

    @Override
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }
}
