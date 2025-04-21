package com.ecristobale.apifirst.apifirstspringboot.services;

import com.ecristobale.apifirst.apifirstspringboot.repositories.CustomerRepository;
import com.ecristobale.apifirst.apifirstspringboot.repositories.OrderRepository;
import com.ecristobale.apifirst.apifirstspringboot.repositories.ProductRepository;
import com.ecristobale.apifirst.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    public List<OrderDto> listOrders() {
//        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
//                .toList();
        return null;
    }

    @Override
    public OrderDto getOrderById(UUID orderId) {
//        return orderRepository.findById(orderId).orElseThrow();
        return null;
    }

    @Override
    public OrderDto saveNewCustomer(OrderCreateDto orderCreate) {
//        CustomerDto orderCustomer = customerRepository.findById(orderCreate.getCustomerId()).orElseThrow();

//        OrderDto.OrderDtoBuilder builder = OrderDto.builder()
//                .customer(OrderCustomerDto.builder()
//                        .id(orderCustomer.getId())
//                        .name(orderCustomer.getName())
//                        .billToAddress(orderCustomer.getBillToAddress())
//                        .shipToAddress(orderCustomer.getShipToAddress())
//                        .phone(orderCustomer.getPhone())
//                        .selectedPaymentMethod(orderCustomer.getPaymentMethods().stream()
//                                .filter(paymentMethod -> paymentMethod.getId()
//                                        .equals(orderCreate.getSelectPaymentMethodId()))
//                                .findFirst().orElseThrow())
//                        .build())
//                .orderStatus(OrderDto.OrderStatusEnum.NEW);

//        List<OrderLineDto> orderLines = new ArrayList<>();

//        orderCreate.getOrderLines()
//                .forEach(orderLineCreate -> {
//                    ProductDto product = productRepository.findById(orderLineCreate.getProductId()).orElseThrow();
//
//                    orderLines.add(OrderLineDto.builder()
//                            .product(OrderProductDto.builder()
//                                    .id(product.getId())
//                                    .description(product.getDescription())
//                                    .price(product.getPrice())
//                                    .build())
//                            .orderQuantity(orderLineCreate.getOrderQuantity())
//                            .build());
//                });

//        return orderRepository.save(builder.orderLines(orderLines).build());
        return null;
    }
}
