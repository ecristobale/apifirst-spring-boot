package com.ecristobale.apifirst.apifirstspringboot.mappers;

import com.ecristobale.apifirst.apifirstspringboot.domain.*;
import com.ecristobale.apifirst.apifirstspringboot.repositories.CustomerRepository;
import com.ecristobale.apifirst.apifirstspringboot.repositories.ProductRepository;
import com.ecristobale.apifirst.model.OrderCreateDto;
import com.ecristobale.apifirst.model.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

public abstract class OrderMapperDecorator implements OrderMapper {

    @Autowired
    @Qualifier("delegate")
    private OrderMapper orderMapperDelegate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentMethodMapper paymentMethodMapper;

    @Override
    public Order orderCreateDtoToOrder(OrderCreateDto orderCreateDto) {
        Customer orderCustomer = customerRepository.findById(orderCreateDto.getCustomerId()).orElseThrow();

        PaymentMethod selectedPaymentMethod = orderCustomer.getPaymentMethods().stream()
                .filter(pm -> pm.getId().equals(orderCreateDto.getSelectPaymentMethodId()))
                .findFirst()
                .orElseThrow();

        Order.OrderBuilder builder = Order.builder()
                .customer(orderCustomer)
                .selectedPaymentMethod(selectedPaymentMethod)
                .orderStatus(OrderStatusEnum.NEW);

        List<OrderLine> orderLines = new ArrayList<>();

        orderCreateDto.getOrderLines()
                .forEach(orderLineCreate -> {
                    Product product = productRepository.findById(orderLineCreate.getProductId()).orElseThrow();

                    orderLines.add(OrderLine.builder()
                            .product(product)
                            .orderQuantity(orderLineCreate.getOrderQuantity())
                            .build());
                });

        return builder.orderLines(orderLines).build();
    }

    @Override
    public OrderDto orderToOrderDto(Order order) {
        OrderDto orderDto = orderMapperDelegate.orderToOrderDto(order);
        orderDto.getCustomer()
                .selectedPaymentMethod(paymentMethodMapper.paymentMethodToPaymentMethodDto(order.getSelectedPaymentMethod()));
        return orderDto;
    }

    @Override
    public Order orderDtoToOrder(OrderDto orderDto) {
        return orderMapperDelegate.orderDtoToOrder(orderDto);
    }
}
