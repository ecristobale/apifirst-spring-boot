package com.ecristobale.apifirst.apifirstspringboot.mappers;

import com.ecristobale.apifirst.apifirstspringboot.domain.*;
import com.ecristobale.apifirst.apifirstspringboot.repositories.CustomerRepository;
import com.ecristobale.apifirst.apifirstspringboot.repositories.ProductRepository;
import com.ecristobale.apifirst.model.OrderCreateDto;
import com.ecristobale.apifirst.model.OrderDto;
import com.ecristobale.apifirst.model.OrderPatchDto;
import com.ecristobale.apifirst.model.OrderUpdateDto;
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

        Order order = builder.orderLines(orderLines).build();
        orderLines.forEach(orderLine -> orderLine.setOrder(order));
        return order;
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

    @Override
    public void updateOrder(OrderUpdateDto orderDto, Order order) {
        orderMapperDelegate.updateOrder(orderDto, order);

        Customer orderCustomer = customerRepository.findById(orderDto.getCustomerId()).orElseThrow();

        order.setCustomer(orderCustomer);

        PaymentMethod selectedPaymentMethod = order.getCustomer().getPaymentMethods().stream()
                .filter(pm -> pm.getId().equals(orderDto.getSelectPaymentMethodId()))
                .findFirst()
                .orElseThrow();

        order.setSelectedPaymentMethod(selectedPaymentMethod);

        if (orderDto.getOrderLines() != null && !orderDto.getOrderLines().isEmpty()) {
            orderDto.getOrderLines().forEach(orderLineDto -> {
                OrderLine existingOrderLine = order.getOrderLines().stream()
                        .filter(ol -> ol.getId().equals(orderLineDto.getId()))
                        .findFirst().orElseThrow();

                Product product = productRepository.findById(orderLineDto.getProductId()).orElseThrow();

                existingOrderLine.setProduct(product);
                existingOrderLine.setOrderQuantity(orderLineDto.getOrderQuantity());
            });
        }
    }

    @Override
    public OrderUpdateDto orderToOrderUpdateDto(Order order) {
        OrderUpdateDto orderUpdateDto = orderMapperDelegate.orderToOrderUpdateDto(order);

        orderUpdateDto.setCustomerId(order.getCustomer().getId());
        orderUpdateDto.setSelectPaymentMethodId(order.getSelectedPaymentMethod().getId());

        orderUpdateDto.getOrderLines().forEach(orderLineDto -> {
            OrderLine orderLine = order.getOrderLines().stream()
                    .filter(ol -> ol.getId().equals(orderLineDto.getId()))
                    .findFirst()
                    .orElseThrow();
            orderLineDto.setProductId(orderLine.getProduct().getId());
        });

        return orderUpdateDto;
    }

    @Override
    public void patchOrder(OrderPatchDto orderPatchDto, Order target) {
        orderMapperDelegate.patchOrder(orderPatchDto, target);

        if (orderPatchDto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(orderPatchDto.getCustomerId()).orElseThrow();
            target.setCustomer(customer);
        }

        if (orderPatchDto.getSelectPaymentMethodId() != null) {
            PaymentMethod selectedPaymentMethod = target.getCustomer().getPaymentMethods().stream()
                    .filter(pm -> pm.getId().equals(orderPatchDto.getSelectPaymentMethodId()))
                    .findFirst()
                    .orElseThrow();
            target.setSelectedPaymentMethod(selectedPaymentMethod);
        }

        if (orderPatchDto.getOrderLines() != null && !orderPatchDto.getOrderLines().isEmpty()) {
            orderPatchDto.getOrderLines().forEach(orderLinePatchDto -> {
                OrderLine existingOrderLine = target.getOrderLines().stream()
                        .filter(ol -> ol.getId().equals(orderLinePatchDto.getId()))
                        .findFirst()
                        .orElseThrow();

                if (orderLinePatchDto.getProductId() != null) {
                    Product product = productRepository.findById(orderLinePatchDto.getProductId()).orElseThrow();
                    existingOrderLine.setProduct(product);
                }

                if (orderLinePatchDto.getOrderQuantity() != null) {
                    existingOrderLine.setOrderQuantity(orderLinePatchDto.getOrderQuantity());
                }
            });
        }
    }
}
