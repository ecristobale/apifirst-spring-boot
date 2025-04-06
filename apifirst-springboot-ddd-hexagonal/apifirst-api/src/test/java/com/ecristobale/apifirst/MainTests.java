package com.ecristobale.apifirst;

import com.ecristobale.apifirst.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MainTests {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void contextLoads() {
        // This test method is used to check if the Spring application context loads successfully.
        // It does not perform any specific assertions or checks.
        // If the application context fails to load, this test will fail.
    }

    @Test
    void testDataLoad() {
        assertThat(customerRepository.count()).isGreaterThan(0L);
    }
}
