package com.ecristobale.apifirst.bootstrap;

import com.ecristobale.apifirst.model.*;
import com.ecristobale.apifirst.repositories.CustomerRepository;
import com.ecristobale.apifirst.repositories.OrderRepository;
import com.ecristobale.apifirst.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        Address address1 = Address.builder()
                .addressLine1("1234 W Some Street")
                .city("Some City")
                .state("FL")
                .zip("33701")
                .build();

        Customer savedCustomer1 = Customer.builder()
                .name(Name.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .build())
                .billToAddress(address1)
                .shipToAddress(address1)
                .email("edu_ce_1988@hotmail.com")
                .phone("800-555-1212")
                .paymentMethods(List.of(PaymentMethod.builder()
                        .displayName("My Card")
                        .cardNumber(12341234)
                        .expiryMonth(12)
                        .expiryYear(26)
                        .cvv(123)
                        .build()))
                .build();

        Address address2 = Address.builder()
                .addressLine1("1234 W Some Street")
                .city("Some City")
                .state("FL")
                .zip("33701")
                .build();

        Customer savedCustomer2 = Customer.builder()
                .name(Name.builder()
                        .firstName("Ecristobale")
                        .lastName("Ecristobalee")
                        .build())
                .billToAddress(address2)
                .shipToAddress(address2)
                .email("edu_ce_19888@hotmail.com")
                .phone("800-555-1212")
                .paymentMethods(List.of(PaymentMethod.builder()
                        .displayName("My Other Card")
                        .cardNumber(1234888)
                        .expiryMonth(12)
                        .expiryYear(26)
                        .cvv(456)
                        .build()))
                .build();

        customerRepository.save(savedCustomer1);
        customerRepository.save(savedCustomer2);
        Product product1 = Product.builder()
                .description("Product 1")
                .categories(List.of(Category.builder()
                        .category("Category 1")
                        .description("Category 1 Description")
                        .build()))
                .cost("12.99")
                .price("14.99")
                .dimensions(Dimensions.builder()
                        .height(1)
                        .length(2)
                        .width(3)
                        .build())
                .images(List.of(Image.builder()
                        .url("http://example.com/image1")
                        .altText("Image 1")
                        .build()))
                .build();

        Product product2 = Product.builder()
                .description("Product 2")
                .categories(List.of(Category.builder()
                        .category("Category 2")
                        .description("Category 2 Description")
                        .build()))
                .cost("12.99")
                .price("14.99")
                .dimensions(Dimensions.builder()
                        .height(1)
                        .length(2)
                        .width(3)
                        .build())
                .images(List.of(Image.builder()
                        .url("http://example.com/image2")
                        .altText("Image 2")
                        .build()))
                .build();

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);

        Order order1 = Order.builder()
                .customer(OrderCustomer.builder()
                        .id(savedCustomer1.getId())
                        .name(savedCustomer1.getName())
                        .billToAddress(savedCustomer1.getBillToAddress())
                        .shipToAddress(savedCustomer1.getShipToAddress())
                        .phone(savedCustomer1.getPhone())
                        .paymentMethod(savedCustomer1.getPaymentMethods().get(0))
                        .build())
                .orderStatus(Order.OrderStatusEnum.NEW)
                .shipmentInfo("shipment info")
                .orderLines(List.of(OrderLine.builder()
                                .product(OrderProduct.builder()
                                        .id(savedProduct1.getId())
                                        .description(product1.getDescription())
                                        .price(product1.getPrice())
                                        .build())
                                .orderQuantity(1)
                                .shipQuantity(1)
                                .build(),
                        OrderLine.builder()
                                .product(OrderProduct.builder()
                                        .id(savedProduct2.getId())
                                        .description(product2.getDescription())
                                        .price(product1.getPrice())
                                        .build())
                                .orderQuantity(1)
                                .shipQuantity(1)
                                .build()))
                .build();

        Order order2 = Order.builder()
                .customer(OrderCustomer.builder()
                        .id(savedCustomer2.getId())
                        .billToAddress(savedCustomer2.getBillToAddress())
                        .shipToAddress(savedCustomer2.getShipToAddress())
                        .phone(savedCustomer2.getPhone())
                        .paymentMethod(savedCustomer2.getPaymentMethods().get(0))
                        .build())
                .orderStatus(Order.OrderStatusEnum.NEW)
                .shipmentInfo("shipment info #2")
                .orderLines(List.of(OrderLine.builder()
                                .product(OrderProduct.builder()
                                        .id(savedProduct1.getId())
                                        .description(product1.getDescription())
                                        .price(product1.getPrice())
                                        .build())
                                .orderQuantity(1)
                                .shipQuantity(1)
                                .build(),
                        OrderLine.builder()
                                .product(OrderProduct.builder()
                                        .id(savedProduct2.getId())
                                        .description(product2.getDescription())
                                        .price(product1.getPrice())
                                        .build())
                                .orderQuantity(1)
                                .shipQuantity(1)
                                .build()))
                .build();

        orderRepository.save(order1);
        orderRepository.save(order2);
    }
}
